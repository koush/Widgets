package com.koushikdutta.widgets;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ViewSwitcher;

public class ListContentFragmentInternal extends BetterListFragmentInternal {
    ViewGroup mContent;
    ViewGroup mContainer;

    public ViewGroup getContainer() {
        return mContainer;
    }
    
    public ListContentFragmentInternal(FragmentInterfaceWrapper fragment) {
        super(fragment);
    }

    @Override
    protected int getListHeaderResource() {
        return R.layout.list_content_header;
    }
    
    @Override
    protected void setPadding() {
        super.setPadding();
        float hor = getResources().getDimension(R.dimen.list_horizontal_margin);
        float ver = getResources().getDimension(R.dimen.list_vertical_margin);
        getListView().setPadding(0, 0, 0, 0);
        ver = 0;
        mContainer.setPadding((int)hor, (int)ver, (int)hor, (int)ver);
    }
    
    FragmentInterfaceWrapper mCurrentContent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState, View ret) {
        mContent = (ViewGroup)ret.findViewById(R.id.content);
        mContainer = (ViewGroup)ret.findViewById(R.id.list_content_container);

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
        super.onCreate(savedInstanceState, ret);
    }
    
    public boolean isPaged() {
        return mContainer instanceof ViewSwitcher;
    }
    
    void setContentNative(FragmentInterfaceWrapper last) {
        android.app.Fragment f = (android.app.Fragment)mCurrentContent;
        Activity fa = getActivity();
        android.app.FragmentTransaction ft = fa.getFragmentManager().beginTransaction();
        if (last != null)
            ft.replace(R.id.content, f);
        else
            ft.add(R.id.content, f);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (mContainer instanceof ViewSwitcher) {
            ViewSwitcher switcher = (ViewSwitcher)mContainer;
            if (mContent != switcher.getCurrentView())
                switcher.showNext();
        }
        ft.commit();
    }
    
    public void setContent(FragmentInterfaceWrapper content, boolean clearChoices) {
        FragmentInterfaceWrapper last = mCurrentContent;
        mCurrentContent = content;
        if (getActivity() instanceof FragmentActivity) {
            Fragment f = (Fragment)mCurrentContent;
            FragmentActivity fa = (FragmentActivity)getActivity();
            FragmentTransaction ft = fa.getSupportFragmentManager().beginTransaction();
            if (last != null)
                ft.replace(R.id.content, f);
            else
                ft.add(R.id.content, f);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (mContainer instanceof ViewSwitcher) {
                ViewSwitcher switcher = (ViewSwitcher)mContainer;
                if (mContent != switcher.getCurrentView())
                    switcher.showNext();
            }
            ft.commit();
        }
        else {
            setContentNative(last);
        }

        if (clearChoices)
            getListView().clearChoices();
    }

    public void goBackNative() {
        android.app.Fragment f = (android.app.Fragment)mCurrentContent;
        Activity fa = getActivity();
        ((ViewSwitcher)mContainer).showPrevious();
        fa.getFragmentManager().beginTransaction()
        .remove(f)
        .commit();
        mCurrentContent = null;
    }
    
    public boolean onBackPressed() {
        if (mCurrentContent == null)
            return false;
        if (mContainer instanceof ViewSwitcher) {
            if (getActivity() instanceof FragmentActivity) {
                Fragment f = (Fragment)mCurrentContent;
                FragmentActivity fa = (FragmentActivity)getActivity();
                ((ViewSwitcher)mContainer).showPrevious();
                fa.getSupportFragmentManager().beginTransaction()
                .remove(f)
                .commit();
                mCurrentContent = null;
                return true;
            }
            else {
                goBackNative();
                return true;
            }
        }
        return false;
    }

    @Override
    protected int getListItemResource() {
        return R.layout.list_item_selectable;
    }

    @Override
    protected int getListFragmentResource() {
        return R.layout.list_content;
    }
    
    public ViewGroup getContent() {
        return mContent;
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setPadding();
    }
}
