package com.wazxb.xuerongbao.moudles.common;

import android.databinding.ViewDataBinding;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.wazxb.xuerongbao.R;
import com.wazxb.xuerongbao.base.list.ZXBViewHolder;
import com.wazxb.xuerongbao.databinding.ItemSchoolBinding;
import com.wazxb.xuerongbao.storage.data.SchoolItemData;
import com.zxzx74147.devlib.ZXApplicationDelegate;
import com.zxzx74147.devlib.utils.ZXStringUtil;

/**
 * Created by zhengxin on 16/3/11.
 */
public class SchoolViewHolder extends ZXBViewHolder<SchoolItemData> {

    public SchoolViewHolder(ViewDataBinding binding) {
        super(binding);
    }

    @Override
    protected void bindData(final SchoolItemData data) {
        final ItemSchoolBinding binding = (ItemSchoolBinding) mBinding;
        binding.setData(data);
        binding.executePendingBindings();
        String match = SchoolSelActivity.SELECT;
        if (!ZXStringUtil.checkString(match)) {
            return;
        }
        int start = data.name.indexOf(match);
        if (start < 0) {
            return;
        }
        SpannableString sp = new SpannableString(data.name);
        //设置背景颜色
        sp.setSpan(new ForegroundColorSpan(ZXApplicationDelegate.getApplication().getResources().getColor(R.color.orange)), start, start + match.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//      sp.setSpan(new ForegroundColorSpan(Color.YELLOW), 3 ,5,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.school.setText(sp);

    }


}
