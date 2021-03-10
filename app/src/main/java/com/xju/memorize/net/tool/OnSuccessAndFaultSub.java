package com.xju.memorize.net.tool;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.xju.memorize.bean.ErrorBean;
import com.xju.memorize.component.dialog.LoadingDialog;
import com.xju.memorize.event.BackHomeEvent;
import com.xju.memorize.util.GsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import androidx.annotation.NonNull;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

//用于在Http请求开始时，自动显示一个ProgressDialog
//在Http请求结束是，关闭ProgressDialog
//调用者自己对请求数据进行处理   成功时 通过result是否等于1分别回调onSuccess和onFault，默认处理了401错误转登录。
//回调结果为String，需要手动序列化

public class OnSuccessAndFaultSub extends DisposableObserver<ResponseBody>
        implements ProgressCancelListener {
    private static final String TAG = OnSuccessAndFaultSub.class.toString();

    //是否需要显示默认Loading
    private boolean showProgress = true;
    private OnSuccessAndFaultListener mOnSuccessAndFaultListener;

    private Context context;
    private LoadingDialog progressDialog;


    //mOnSuccessAndFaultListener 成功回调监听
    public OnSuccessAndFaultSub(OnSuccessAndFaultListener mOnSuccessAndFaultListener) {
        this.mOnSuccessAndFaultListener = mOnSuccessAndFaultListener;
    }


    //mOnSuccessAndFaultListener 成功回调监听
    public OnSuccessAndFaultSub(OnSuccessAndFaultListener mOnSuccessAndFaultListener, Context context) {
        this.mOnSuccessAndFaultListener = mOnSuccessAndFaultListener;
        this.context = context;
        progressDialog = new LoadingDialog(context, "正在加载...");
    }

    //@param mOnSuccessAndFaultListener 成功回调监听
    // * @param context 上下文
    //* @param showProgress 是否需要显示默认Loading
    public OnSuccessAndFaultSub(OnSuccessAndFaultListener mOnSuccessAndFaultListener, Context context, boolean showProgress) {
        this.mOnSuccessAndFaultListener = mOnSuccessAndFaultListener;
        this.context = context;
        progressDialog = new LoadingDialog(context, "正在加载...");
        this.showProgress = showProgress;
    }

    private void showProgressDialog() {
        if (showProgress && null != progressDialog) {
            progressDialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (showProgress && null != progressDialog) {
            progressDialog.dismiss();
        }
    }

    //* 订阅开始时调用
    //* 显示ProgressDialog
    @Override
    public void onStart() {
        showProgressDialog();
    }


    //完成，隐藏ProgressDialog
    @Override
    public void onComplete() {
        dismissProgressDialog();
        progressDialog = null;
    }

    @Override
    public void onError(@NonNull Throwable e) {

        try {
            if (e instanceof SocketTimeoutException) {
                mOnSuccessAndFaultListener.onFault("请求超时:" + e.getMessage());
            } else if (e instanceof ConnectException) {
                mOnSuccessAndFaultListener.onFault("网络连接超时:" + e.getMessage());
            } else if (e instanceof SSLHandshakeException) {
                mOnSuccessAndFaultListener.onFault("安全证书异常:" + e.getMessage());
            } else if (e instanceof UnknownHostException) {
                mOnSuccessAndFaultListener.onFault(e.getMessage());
            } else if (e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                int code = httpException.code();

                if (code >= 500 && code < 600) {
                    mOnSuccessAndFaultListener.onFault(e.getMessage());
                    return;
                }

                // 服务器返回的业务错误信息
                ErrorBean errorBean = parseResponseMessage(httpException);

                if (errorBean == null) {
                    mOnSuccessAndFaultListener.onFault(e.getMessage());
                    return;
                }

                // 403 登录失效，特殊处理
                if (errorBean.getCode() == 403) {
                    BackHomeEvent event = new BackHomeEvent();
                    event.setOpenLogin(true);
                    EventBus.getDefault().post(event);
                    return;
                }

                mOnSuccessAndFaultListener.onFault(errorBean.getMessage());
            } else {
                mOnSuccessAndFaultListener.onFault(e.getMessage());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            mOnSuccessAndFaultListener.onFault(e2.getMessage());
        } finally {
            dismissProgressDialog();
            progressDialog = null;
        }

    }

    /**
     * 从 HttpException 对象解析出服务的业务错误对象
     *
     * @param httpException HttpException
     * @return String
     */
    private ErrorBean parseResponseMessage(HttpException httpException) {
        try {
            String string = httpException.response().errorBody().string();
            if (!TextUtils.isEmpty(string)) {
                return GsonUtil.fromJson(string, ErrorBean.class);
            }
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
        return null;
    }

    //* 当result等于1回调给调用者，否则自动显示错误信息，若错误信息为401跳转登录页面。
    //        * ResponseBody  body = response.body();//获取响应体
    // * InputStream inputStream = body.byteStream();//获取输入流
    // * byte[] bytes = body.bytes();//获取字节数组
    // * String str = body.string();//获取字符串数据
    @Override
    public void onNext(ResponseBody body) {
        try {
            JSONObject jsonObject = new JSONObject(body.string());
            int resultCode = jsonObject.getInt("code");
            if (resultCode == 200) {
                String result = jsonObject.getString("data");
//                if (!Constant.isProduction) {
//                    Log.d("memorize:OnSuccessAndbody", result);
//                }
                mOnSuccessAndFaultListener.onSuccess(result);
            } else {
                String errorMsg = jsonObject.getString("message");
                mOnSuccessAndFaultListener.onFault(errorMsg);
//                if (!Constant.isProduction) {
//                    Log.d("memorize:", "errorMsg: " + errorMsg);
//                }
            }
        } catch (Exception e) {
            mOnSuccessAndFaultListener.onFault(e.getMessage());
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }


    //取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
    @Override
    public void onCancelProgress() {
        if (!this.isDisposed()) {
            this.dispose();
        }
    }
}
