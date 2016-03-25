package com.wazxb.xuerongbao.util;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.common.webview.CommonWebActivity;
import com.wazxb.xuerongbao.storage.data.LoanItemData;
import com.wazxb.xuerongbao.storage.data.ProdData;
import com.wazxb.xuerongbao.widget.CashView;
import com.wazxb.xuerongbao.widget.CreditBar;
import com.wazxb.xuerongbao.widget.InfoTextView;
import com.wazxb.xuerongbao.widget.InputTextView;
import com.wazxb.xuerongbao.widget.PaybackBar;
import com.wazxb.xuerongbao.widget.UploadImageView;
import com.zxzx74147.devlib.ZXApplicationDelegate;
import com.zxzx74147.devlib.utils.ZXStringUtil;
import com.zxzx74147.devlib.widget.StrokeTextView;

/**
 * Created by zhengxin on 16/3/6.
 */
public class ZXBDataBindingUtil {

    @BindingAdapter("app:input_edit_text")
    public static void setLayoutHeight(InputTextView view, String edit) {
        view.setContent(edit);
    }

    @BindingAdapter("app:info_content_drawable")
    public static void setRightDrawable(InfoTextView view, Drawable edit) {
        view.setRightDrawable(edit);
    }

    @BindingAdapter("app:content_text")
    public static void setRightDrawable(InfoTextView view, String edit) {
        view.setContent(edit);
    }


    @BindingAdapter("app:img_not_null")
    public static void setRightDrawable(UploadImageView view, boolean isNotNull) {
        view.setIsNotNull(isNotNull);
    }


    @BindingAdapter("app:progress")
    public static void setCreditProgress(CreditBar view, int edit) {
        view.setProgress(edit);
    }

    @BindingAdapter("app:click_url")
    public static void setRightDrawable(final View view, final String url) {
        if (view == null || !ZXStringUtil.checkString(url)) {
            return;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = null;
                if (view instanceof TextView) {

                    title = ((TextView) view).getText().toString();
                    if (ZXApplicationDelegate.getApplication().getResources().getString(R.string.xxw_remind).equals(title)) {
                        title = "上传方法";
                    }
                    title = title.replace("《", "");
                    title = title.replace("》", "");
                    if (title.length() > 5) {
                        title = title.substring(title.length() - 4);
                    }
                }
                CommonWebActivity.startActivity(view.getContext(), title, url);
            }
        });
    }

    @BindingAdapter("app:info_edit_text")
    public static void setLayoutHeig(InfoTextView view, String edit) {
        view.setContent(edit);
    }

    @BindingAdapter("app:info_remind_text")
    public static void setRemind(InfoTextView view, String num) {
        view.setRemind(num);
    }

    @BindingAdapter("app:inner_color")
    public static void setInnerColor(StrokeTextView view, int num) {
        view.setInnerColor(num);
    }

    @BindingAdapter("app:input_sex")
    public static void setLayoutHeight(InputTextView view, int sex) {
        if (sex == 1) {
            view.setContent("男");
        } else if (sex == 2) {
            view.setContent("女");
        } else {
            view.setContent("");
        }
    }

    @BindingAdapter("app:drawable_id")
    public static void setLayoutHeight(ImageView view, int drawable) {
        view.setImageResource(drawable);
    }

    @BindingAdapter("app:img_url")
    public static void setLayoutHeight(UploadImageView view, String url) {
        view.setImageUrl(url);
    }

    public static String splitUrl(String urls, int index) {
        if (!ZXStringUtil.checkString(urls)) {
            return urls;
        }
        String[] url = urls.split(",");
        if (url.length > index) {
            return url[index];
        }
        if (index == 0) {
            return urls;
        }
        return null;
    }

    @BindingAdapter("app:prodData")
    public static void setLayoutHeight(CashView view, ProdData prod) {
        view.setProd(prod);
    }

    @BindingAdapter("app:int_max")
    public static void setPaybackBarMax(PaybackBar view, int max) {
        view.setMax(max);
    }

    @BindingAdapter("app:int_process")
    public static void setPaybackBarProgress(PaybackBar view, int process) {
        view.setProcess(process);
    }

    @BindingAdapter("app:loan_process")
    public static void setPaybackBarProgress(PaybackBar view, LoanItemData data) {
        view.setLoanData(data);
    }
}
