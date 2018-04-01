package com.gdut.dkmfromcg.ojbk_ui.bottom;

/**
 * Created by dkmFromCG on 2018/3/15.
 * function:
 */

public class TabItemBean {
    private final CharSequence ICON;
    private final CharSequence TITLE;

    public TabItemBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }

}
