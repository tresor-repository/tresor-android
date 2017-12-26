package com.tresor.common.model;

/**
 * Created by kris on 8/11/17. Tokopedia
 */

public class HashTagFilterModel {

    private String hashTagString;

    private boolean selected;

    public String getHashTagString() {
        return hashTagString;
    }

    public void setHashTagString(String hashTagString) {
        this.hashTagString = hashTagString;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
