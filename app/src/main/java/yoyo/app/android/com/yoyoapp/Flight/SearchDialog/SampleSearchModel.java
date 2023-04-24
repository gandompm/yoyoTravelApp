package yoyo.app.android.com.yoyoapp.Flight.SearchDialog;

import ir.mirrajabi.searchdialog.core.Searchable;

// sample search model for city item
public class SampleSearchModel implements Searchable {
    private String mTitle;

    public SampleSearchModel(String title) {
        mTitle = title;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    public SampleSearchModel setTitle(String title) {
        mTitle = title;
        return this;
    }
}