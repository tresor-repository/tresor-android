package com.tresor.home.model;

/**
 * Created by kris on 7/6/17. Tokopedia
 */

public class IconModel {

    private int iconImageId;

    private boolean available = true;

    private boolean choosen = false;

    public int getIconImageId() {
        return iconImageId;
    }

    public void setIconImageId(int iconImageId) {
        this.iconImageId = iconImageId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isChoosen() {
        return choosen;
    }

    public void setChoosen(boolean choosen) {
        this.choosen = choosen;
    }
}
