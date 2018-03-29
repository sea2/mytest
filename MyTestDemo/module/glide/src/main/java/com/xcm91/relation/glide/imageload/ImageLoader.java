package com.xcm91.relation.glide.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;
import com.xcm91.relation.glide.FileDirectoryUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lhy on 2018/3/27.
 */

public class ImageLoader {

    private static final String TAG = "ImageLoader";


    private static final int DISK_CACHE_INDEX = 0;

    public static final int MESSAGE_POST_RESULT = 1001;

    //CPU核心数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    //核心线程数
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

    //最大线程数
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;

    //线程存活时间
    private static final long KEEP_ALIVE_TIME = 10;

    //磁盘缓存目录名称
    private static final String DISK_CACAHE_NAME = "bitmap";

    //磁盘缓存大小限制，默认为50MB，可修改
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;

    //IO流大小
    private static final int IO_BUFFER_SIZE = 8 * 1024;

    //标记内存缓存是否建立
    private boolean mIsDiskCacheCreated = false;


    //内存缓存
    private LruCache<String, Bitmap> mMemoryCache;

    //磁盘缓存
    private DiskLruCache mDiskLruCache;

    //图片缩放类
    private ImageResizer mImageResizer = new ImageResizer();

    //线程工厂
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {

        //AtomicInteger，线程安全的加减操作接口
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ImageLoader #" + mCount.getAndIncrement());
        }
    };

    //线程池
    public static final Executor THREAD_POOR_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), sThreadFactory);


    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.imageView;
            imageView.setImageBitmap(result.bitmap);
        }
    };


    /**
     * 私有构造函数，不能通过new来产生实例
     * 主要完成了mLruCache和mDiskLruCache的初始化
     */
    private ImageLoader(Context context) {
        Context mContext = context.getApplicationContext();

        //应用程序可用的最大内存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        //取最大内存的1/8作为内存缓存的大小
        int cacheSize = maxMemory / 8;

        /**
         * 初始化内存缓存
         * 返回缓存对象的大小,单位要和cacheSize一样
         */
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

        File diskCacheFile = getDiskCacheFile(mContext, DISK_CACAHE_NAME);
        if (!diskCacheFile.exists()) {
            diskCacheFile.mkdirs();
        }

        //确保该路径下的可用空间大于磁盘缓存需要的最大空间
        if (getUsableSpace(diskCacheFile) > DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheFile, 1, 1, DISK_CACHE_SIZE);
                mIsDiskCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 提供ImageLoader实例
     */
    public static ImageLoader getIntance(Context context) {
        return new ImageLoader(context);
    }


    public void bindBitmap(final String url, final ImageView imageView) {
        bindBitmap(url, imageView, 0, 0);
    }

    /**
     * 异步下载
     * 根据url将图片显示在ImageView上，依次顺序是内存缓存，磁盘缓存，网络下载
     *
     * @param url       图片url
     * @param imageView 绑定的ImageView
     * @param reqWidth  期望的宽度
     * @param reqHeight 期望的高度
     */
    public void bindBitmap(final String url, final ImageView imageView, final int reqWidth, final int reqHeight) {

        Bitmap bitmap = loadBitmapFromMemCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(url, reqWidth, reqHeight);
                if (bitmap != null) {
                    LoaderResult result = new LoaderResult(imageView, url, bitmap);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
                }
            }
        };

        THREAD_POOR_EXECUTOR.execute(loadBitmapTask);
    }


    /**
     * 同步加载
     * * @param url       图片url
     *
     * @param reqWidth  期望的宽度
     * @param reqHeight 期望的高度
     */
    public Bitmap loadBitmap(String url, int reqWidth, int reqHeight) {

        Bitmap bitmap = loadBitmapFromMemCache(url);
        if (bitmap != null) {
            Log.d(TAG, "from MemoryCache; url:" + url);
            return bitmap;
        }

        bitmap = loadBitmapFromDiskCache(url, reqWidth, reqHeight);
        if (bitmap != null) {
            Log.d(TAG, "from DiskCache; url:" + url);
            return bitmap;
        }
        bitmap = loadBitmapFromNet(url, reqWidth, reqHeight);
        Log.d(TAG, "from Net; url:" + url);

        if (bitmap == null && !mIsDiskCacheCreated) {
            Log.e("TAG", "encounter error, DiskLruCache is not created.");
            bitmap = downloadBitmapFromUrl(url);
        }
        return bitmap;
    }


    /**
     * 从内存缓存中加载图片
     */
    private Bitmap loadBitmapFromMemCache(String url) {
        final String key = hashKeyFormUrl(url);
        return getBitmapFromMemoryCache(key);
    }

    /**
     * 从磁盘缓存中加载图片,加载成功后添加到内存缓存
     */
    private Bitmap loadBitmapFromDiskCache(String url, int reqWidth, int reqHeight) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.w(TAG, "load bitmap from UI Thread, it's not recommended!");
        }
        if (mDiskLruCache == null) {
            return null;
        }

        Bitmap bitmap = null;
        String key = hashKeyFormUrl(url);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                FileInputStream inputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
                FileDescriptor descriptor = inputStream.getFD();
                bitmap = mImageResizer.resizeBitmapFromFileDescriptor(descriptor, reqWidth, reqHeight);
                if (bitmap != null) {
                    addBitmapToMemoryCache(key, bitmap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    /**
     * 从网络加载图片流，缓存到磁盘，然后从磁盘缓存中获取图片
     */
    private Bitmap loadBitmapFromNet(String url, int reqWidth, int reqHeight) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can not visit network from UI Thread.");
        }
        if (mDiskLruCache == null) {
            return null;
        }

        String key = hashKeyFormUrl(url);
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
                if (downloadUrlToStream(url, outputStream)) {
                    editor.commit();//下载完成，添加缓存到磁盘
                } else {
                    editor.abort();//若失败，放弃添加缓存
                }
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return loadBitmapFromDiskCache(url, reqWidth, reqHeight);
    }

    /**
     * 直接从网络下载图片
     */
    private Bitmap downloadBitmapFromUrl(String urlPath) {
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }


    private boolean downloadUrlToStream(String imageUrl, OutputStream outputStream) {
        HttpURLConnection connection = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();
            in = connection.getInputStream();
            int len;
            byte[] bs = new byte[1024];
            while ((len = in.read(bs)) != -1) {
                outputStream.write(bs, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 向内存缓存中添加Bitmap
     */

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 根据key获取内存缓存中的Bitmap
     */
    private Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 获取磁盘缓存路径
     * 放在此路径下当程序卸载时同时会清楚缓存数据
     * 当然你也可以自定义其他缓存路径
     */
    private File getDiskCacheFile(Context context, String uniqeName) {
       /* String cachePath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cachePath = mContext.getExternalCacheDir().getPath();
        } else {
            cachePath = mContext.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqeName);*/
        return FileDirectoryUtil.getOwnCacheDirectory(context, uniqeName);
    }


    /**
     * 获取可用空间
     */
    private long getUsableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        } else {
            final StatFs statFs = new StatFs(path.getPath());
            return (long) statFs.getBlockSize() * (long) statFs.getAvailableBlocks();
        }
    }

    /**
     * 以url作为tag防止加载时候错乱，为防止url有特殊字符，一律转化为MD5
     *
     * @param url 图片url
     * @return MD5字符串
     */
    private String hashKeyFormUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static class LoaderResult {
        public ImageView imageView;
        public String url;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String url, Bitmap bitmap) {
            this.imageView = imageView;
            this.url = url;
            this.bitmap = bitmap;
        }
    }
}
