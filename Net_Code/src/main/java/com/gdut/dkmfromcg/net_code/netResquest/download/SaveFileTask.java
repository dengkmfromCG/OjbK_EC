package com.gdut.dkmfromcg.net_code.netResquest.download;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.gdut.dkmfromcg.net_code.netResquest.callback.RequestCallback;
import com.gdut.dkmfromcg.net_code.util.FileUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by dkmFromCG on 2018/3/12.
 * function:
 */

public class SaveFileTask extends AsyncTask<Object, Integer, File> {


    private final RequestCallback<String> mRequestCallback;
    private final Context mContext;
    public SaveFileTask(Context context,RequestCallback<String> requestCallback) {
        this.mContext=context;
        this.mRequestCallback=requestCallback;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream is = body.byteStream();
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        if (name == null) {
            //这里应该监听 计算过程 ,得到 下载进度
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            //这里应该监听 计算过程 ,得到 下载进度
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (mRequestCallback!=null){
            mRequestCallback.onProgress(0,0,0);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (mRequestCallback != null) {
            mRequestCallback.onSuccess(file.getPath());
        }
        autoInstallApk(file);
    }

    /**
     * 如果下载好的文件是 apk 文件,则自动安装
     * @param file
     */
    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            mContext.startActivity(install);
        }
    }


}
