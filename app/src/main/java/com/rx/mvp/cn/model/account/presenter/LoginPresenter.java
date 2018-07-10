package com.rx.mvp.cn.model.account.presenter;

import com.rx.mvp.cn.base.BasePresenter;
import com.rx.mvp.cn.core.net.http.observer.HttpRxObserverCallback;
import com.rx.mvp.cn.model.account.biz.UserBiz;
import com.rx.mvp.cn.model.account.entity.UserBean;
import com.rx.mvp.cn.model.other.presenter.PhoneAddressPresenter;
import com.rx.mvp.cn.model.account.iface.ILoginView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 登录Presenter
 *
 * @author ZhongDaFeng
 */

public class LoginPresenter extends BasePresenter<ILoginView, LifecycleProvider> {

    private final String TAG = PhoneAddressPresenter.class.getSimpleName();

    public LoginPresenter(ILoginView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 登录
     *
     * @author ZhongDaFeng
     */
    public void login(String userName, String password) {

        if (getView() != null)
            getView().showLoading();


        HttpRxObserverCallback httpRxObserverCallback = new HttpRxObserverCallback(TAG + "login") {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((UserBean) object[0]);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showToast(desc);
                }
            }

            @Override
            public void onCancel() {
                if (getView() != null) {
                    getView().closeLoading();
                }
            }
        };

        new UserBiz().login(userName, password, getActivity(), httpRxObserverCallback);

        /**
         * ******此处代码为了测试取消请求,不是规范代码*****
         */
        /*try {
            Thread.sleep(50);
            //取消请求
            if (!httpRxObserverCallback.isDisposed()) {
                httpRxObserverCallback.cancel();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


    }

}
