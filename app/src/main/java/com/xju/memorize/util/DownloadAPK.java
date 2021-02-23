package com.xju.memorize.util;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadAPK extends AsyncTask<String, Integer, String> {
    ProgressDialog progressDialog;
    File file;
    private onOkClick onOkClick;

    public DownloadAPK(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    protected String doInBackground(String... params) {
        URL url;
        HttpURLConnection conn;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;

        try {
            url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            int fileLength = conn.getContentLength();
            bis = new BufferedInputStream(conn.getInputStream());
            String fileName = Environment.getExternalStorageDirectory().getPath() + "/magkare/action.apk";
            file = new File(fileName);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            byte data[] = new byte[4 * 1024];
            long total = 0;
            int count;
            while ((count = bis.read(data)) != -1) {
                total += count;
                publishProgress((int) (total * 100 / fileLength));
                fos.write(data, 0, count);
                fos.flush();
            }
            fos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        progressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(onOkClick != null){
            onOkClick.onOK(file);
        }
//        openFile(file);
        progressDialog.dismiss();
    }

//    private void openFile(File file) {
//
//        if (!file.exists()) {
//            Toast.makeText(BaseApplication.appContext, "apk不存在", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        if (file.getName().endsWith(".apk")) {
//
//            try {
//                //兼容7.0
//                Uri uri;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 适配Android 7系统版本
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                    uri = FileProvider.getUriForFile(BaseApplication.appContext, BaseApplication.appContext.getPackageName() + ".fileprovider", file);//通过FileProvider创建一个content类型的Uri
//                } else {
//                    uri = Uri.fromFile(file);
//                }
//                intent.setDataAndType(uri, "application/vnd.android.package-archive"); // 对应apk类型
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            Toast.makeText(BaseApplication.appContext, "不是apk文件", Toast.LENGTH_LONG).show();
//        }
//        //弹出安装界面
//        BaseApplication.appContext.startActivity(intent);
//    }

    public void setOnOkClick(DownloadAPK.onOkClick onOkClick) {
        this.onOkClick = onOkClick;
    }

    public interface onOkClick{
        void onOK(File file);
    }
}
