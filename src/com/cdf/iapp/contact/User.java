package com.cdf.iapp.contact;

import com.cdf.iapp.R;

/**
 * Created by Administrator on 2016/1/8.
 */
public class User {
    private String name;
    private String letter;
    private int icon = R.drawable.ic_launcher;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
