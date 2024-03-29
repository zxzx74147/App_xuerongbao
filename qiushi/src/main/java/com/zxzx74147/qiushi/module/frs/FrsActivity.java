package com.zxzx74147.qiushi.module.frs;

import android.os.Bundle;

import com.zxzx74147.devlib.network.HttpResponse;
import com.zxzx74147.devlib.network.HttpResponseListener;
import com.zxzx74147.qiushi.common.QSBaseListActivity;
import com.zxzx74147.qiushi.common.QSViewBinder;
import com.zxzx74147.qiushi.common.data.CardItemListData;
import com.zxzx74147.qiushi.libs.network.QSHttpRequest;
import com.zxzx74147.qiushi.libs.network.QSRequestManageer;
import com.zxzx74147.qiushi.module.statistics.HistoryManager;

/**
 * Created by zhengxin on 15/9/1.
 */
public class FrsActivity extends QSBaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected QSViewBinder getItemBinder() {
        return new FrsItemBinder();
    }

    @Override
    protected void refreshData() {

        QSHttpRequest<CardItemListData> request = new QSHttpRequest<>(CardItemListData.class,new HttpResponseListener<CardItemListData>() {
            @Override
            public void onResponse(HttpResponse<CardItemListData> response) {
                CardItemListData data = response.result;
                setData(data.items);
                HistoryManager.addHistory(data.items.getFirst().id);
            }
        });
        request.setPath("article/list/suggest");
        request.addParams("r", "7c4cea831441528515776");
        QSRequestManageer.sharedInstance().dealRequest(request);
        sendRequest(request);
    }

}
