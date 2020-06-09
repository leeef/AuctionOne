package com.hlnwl.auction.bean;

/**
 * Created by wanbei on 2017/11/28.
 * 设置单选的bean,供子类继承
 */

public class SelectedBean {
    private boolean isSelected=false;
    private int selectPosition;

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}