package com.zxzx74147.devlib.widget.tabhost;

public interface IFragmentTabIndicator {
    public void setSelected(boolean isSelected);

    public boolean getIsSelected();

    public void showTip();

    public void showTip(int num);//在tab处显示数字提示 eg.未读消息

    public void dismissTip();

    public boolean hasTip();
}
