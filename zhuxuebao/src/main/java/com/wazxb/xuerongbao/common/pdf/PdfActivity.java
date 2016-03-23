package com.wazxb.xuerongbao.common.pdf;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.ZXBBaseActivity;
import com.wazxb.xuerongbao.common.webview.CommonWebActivity;
import com.wazxb.xuerongbao.databinding.ActivityPdfBinding;
import com.zxzx74147.devlib.utils.ZXFileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhengxin on 16/3/21.
 */
public class PdfActivity extends ZXBBaseActivity<CommonWebActivity.PDFData> {

    ActivityPdfBinding mBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pdf);
        CommonWebActivity.PDFData data = getParam();
        AsyncDownloader task = new AsyncDownloader(data.url);
        mBinding.titleBar.setText(data.title);
        task.execute();

    }

    private class AsyncDownloader extends AsyncTask<Void, Long, Boolean> {
        private String URL;
        File file = ZXFileUtil.createFileIfNotFound("temp.pdf");

        public AsyncDownloader(String url) {
            URL = url;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            OkHttpClient httpClient = new OkHttpClient();
            Call call = httpClient.newCall(new Request.Builder().url(URL).get().build());
            try {
                Response response = call.execute();
                if (response.code() == 200) {
                    InputStream inputStream = null;
                    try {

                        FileOutputStream of = new FileOutputStream(file);
                        inputStream = response.body().byteStream();
                        byte[] buff = new byte[1024 * 4];
                        long downloaded = 0;
                        long target = response.body().contentLength();

                        publishProgress(0L, target);
                        while (true) {
                            int readed = inputStream.read(buff);
                            if (readed == -1) {
                                break;
                            }
                            of.write(buff, 0, readed);
                            //write buff
                            downloaded += readed;
                            publishProgress(downloaded, target);
                            if (isCancelled()) {
                                return false;
                            }
                        }
                        of.close();
                        return downloaded == target;
                    } catch (IOException ignore) {
                        return false;
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(Long... values) {

        }

        @Override
        protected void onPostExecute(Boolean result) {
            mBinding.pdfview.fromFile(file).defaultPage(1)
                    .showMinimap(false)
                    .enableSwipe(true)
                    .load();
        }
    }
}
