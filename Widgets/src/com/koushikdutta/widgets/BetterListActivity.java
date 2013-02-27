package com.koushikdutta.widgets;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.koushikdutta.widgets.BetterListFragmentInternal.ActivityBaseFragmentListener;



public class BetterListActivity extends FragmentActivity implements ActivityBaseFragmentListener {
    Class<? extends BetterListFragment> clazz;
    public BetterListActivity(Class<? extends BetterListFragment> clazz) {
        super();
        this.clazz = clazz;
    }
    
    public BetterListActivity() {
        super();
        this.clazz = BetterListFragment.class;
    }
    
    public BetterListFragmentInternal getFragment() {
        return fragment.getInternal();        
    }
    
    public View getView() {
        return fragment.getView();
    }
    
    protected int getListContainerId() {
        return android.R.id.content;
    }
    
    protected int getContentView() {
        return 0;
    }
    
    BetterListFragment fragment;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        int cv = getContentView();
        if (0 != cv)
            setContentView(cv);
        
        try {
            fragment = (BetterListFragment)clazz.getConstructors()[0].newInstance();
            fragment.setArguments(getIntent().getExtras());
            fragment.getInternal().setListener(this);
            getSupportFragmentManager().beginTransaction().add(getListContainerId(), fragment).commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState, View view) {
    }
    

    protected ListItem addItem(int sectionName, ListItem item) {
        return getFragment().addItem(getString(sectionName), item);
    }

    protected ListItem addItem(int sectionName, ListItem item, int index) {
        return getFragment().addItem(getString(sectionName), item, index);
    }
    
    protected ListItem addItem(String sectionName, ListItem item) {
        return getFragment().addItem(sectionName, item, -1);
    }
    
    public void setEmpty(int res) {
        getFragment().setEmpty(res);
    }

    public boolean isDestroyedLegacy() {
        return mDestroyed;
    }
    
    private boolean mDestroyed = false;
    @Override
    protected void onDestroy() {
        mDestroyed = true;
        super.onDestroy();
    }
}
