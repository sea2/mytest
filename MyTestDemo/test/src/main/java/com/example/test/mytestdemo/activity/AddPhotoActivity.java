package com.example.test.mytestdemo.activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.utils.FileProvider7;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPhotoActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_TAKE_PHOTO = 0x110;
    private static final int REQ_PERMISSION_CODE_SDCARD = 0X111;
    private static final int REQ_PERMISSION_CODE_TAKE_PHOTO = 0X112;
    private static final int PHOTO_CROP_RESULT = 0x1113;
    //相册
    private static final int PHOTO_PICKED_WITH_DATA = 0x1114;
    //拍照的图片路径
    private String mCurrentPhotoPath;
    //裁剪后路径
    private String cropPath;
    private ImageView mIvPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_photo);
        mIvPhoto = (ImageView) findViewById(R.id.id_iv);

        //创建目录
        File fileDirector = new File(Environment.getExternalStorageDirectory() + "/my_photo/");
        if (!fileDirector.exists()) {
            fileDirector.mkdirs();
        }
    }

    public void installApk(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_PERMISSION_CODE_SDCARD);
        } else {
            installApk();
        }
    }


    /**
     * 使用系统工具打开文件
     */
    private void installApk() {
        //  //系统打开apk安装
        File file = new File(Environment.getExternalStorageDirectory(), "app-debug.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) //7.0以上的兼容
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        FileProvider7.setIntentDataAndType(intent, "application/vnd.android.package-archive", file, true);
        startActivity(intent);
    }

    /**
     * 使用系统工具打开文件
     */
    private void openFileByApplication() {
        //系统自带打开pdf
        File file = new File(Environment.getExternalStorageDirectory(), "down_protocol/protocol1539765318985.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) //7.0以上的兼容
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        FileProvider7.setIntentDataAndType(intent, "application/pdf", file, true);
        startActivity(intent);
    }


    public void takePhotoNoCompress(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_PERMISSION_CODE_TAKE_PHOTO);
        } else {
            takePhotoNoCompress();
        }
    }


    /**
     * 拍照
     */
    private void takePhotoNoCompress() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA).format(new Date()) + ".png";

            //创建目录
            File fileDirector = new File(getExternalCacheDir() + "/my_photo/");
            if (!fileDirector.exists()) {
                fileDirector.mkdirs();
            }
            File file = new File(fileDirector, filename);
            mCurrentPhotoPath = file.getAbsolutePath();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0以上的兼容
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            Uri takePhotoUri = FileProvider7.getUriForFile(file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, takePhotoUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    /**
     * 打开相册
     *
     * @param view
     */
    public void doPickPhotoFromGallery(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_PERMISSION_CODE_TAKE_PHOTO);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_PERMISSION_CODE_SDCARD) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                installApk();
            } else {
                // Permission Denied
                Toast.makeText(AddPhotoActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQ_PERMISSION_CODE_TAKE_PHOTO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoNoCompress();
            } else {
                // Permission Denied
                Toast.makeText(AddPhotoActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_TAKE_PHOTO) {
            doCropPhoto(new File(mCurrentPhotoPath));
        } else if (resultCode == RESULT_OK && requestCode == PHOTO_CROP_RESULT) {
            mIvPhoto.setImageBitmap(BitmapFactory.decodeFile(cropPath));
        } else if (resultCode == RESULT_OK && requestCode == PHOTO_PICKED_WITH_DATA) {
            mIvPhoto.setImageBitmap(BitmapFactory.decodeFile(getPathStr(data)));
            //  也可以用工具类                    RealPathFromUriUtils
        }
    }


    /**
     * 剪裁
     *
     * @param photoUri
     */
    private void doCropPhoto(Uri photoUri) {
        try {
            // 启动gallery去剪辑这个照片
            Intent intent = getCropImageIntent(photoUri);
            startActivityForResult(intent, PHOTO_CROP_RESULT);
        } catch (Exception e) {
            Log.e("PhotoUtils", "剪裁异常" + e.getMessage());
        }
    }

    /**
     * 剪裁
     *
     * @param f
     */
    private void doCropPhoto(File f) {
        try {
            // 启动gallery去剪辑这个照片
            Intent intent = getCropImageIntent(FileProvider7.getUriForFile(f));
            this.startActivityForResult(intent, PHOTO_CROP_RESULT);
        } catch (Exception e) {
            Log.e("PhotoUtils", "剪裁异常" + e.getMessage());
        }
    }

    /**
     * 调用系统图片剪辑程序
     */
    private Intent getCropImageIntent(Uri photoUri) {
        String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA).format(new Date()) + ".png";

        //创建目录
        File fileDirector = new File(getExternalCacheDir() + "/my_photo/");
        if (!fileDirector.exists()) {
            if (!fileDirector.mkdirs()) Log.e(this.getClass().getSimpleName(), "创建路径文件夹失败！");
        }
        File file = new File(fileDirector, filename);
        cropPath = file.getAbsolutePath();
        //不能使用7.0以上的Uri，因为那个Uri不能跨进程
        Uri cropUri = Uri.fromFile(file);
        Intent intent = new Intent("com.android.camera.action.CROP");
        //加入裁剪的Uri
        intent.setDataAndType(photoUri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0以上的兼容
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        //发送裁剪信号
        intent.putExtra("crop", "true");
        //x方向上的比例
        intent.putExtra("aspectX", 1);
        //y方向上的比例
        intent.putExtra("aspectY", 1);
        //裁剪区的宽
        intent.putExtra("outputX", 250);
        //裁剪区的高
        intent.putExtra("outputY", 250);
        //是否将数据保留在bitmap并返回
        intent.putExtra("return-data", false);
        //剪裁后Uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        //剪裁格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG);
        //不使用人脸识别
        intent.putExtra("noFaceDetection", true);

        return intent;
    }


    /**
     * 系统处理图片的方法
     */
    private String getPathStr(Intent data) {
        String imagePath = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Uri uri = data.getData();
            if (uri != null && DocumentsContract.isDocumentUri(this, uri)) {
                String docId = DocumentsContract.getDocumentId(uri);        //数据表里指定的行
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }

            } else if (uri != null && "content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(uri, null);
            } else {
                if (uri != null) imagePath = uri.getPath();
            }
        } else {
            Uri uri = data.getData();
            imagePath = getImagePath(uri, null);
        }
        return imagePath;
    }


    /**
     * 通过uri和selection来获取真实的图片路径,从相册获取图片时要用doCropPhoto
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


}
