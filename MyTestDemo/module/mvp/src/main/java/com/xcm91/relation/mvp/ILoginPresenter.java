package com.xcm91.relation.mvp;

/**
 * Created by Anthony on 2016/2/15.
 * Class Note:登陆的Presenter 的接口，实现类为LoginPresenterImpl，完成登陆的验证，以及销毁当前view
 */
public interface ILoginPresenter {
    void validateCredentials(String username, String password);

    void onDestroy();

    void onUsernameError();

    void onPasswordError();

    void onSuccess();
}