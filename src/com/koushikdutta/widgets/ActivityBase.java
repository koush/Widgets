package com.koushikdutta.widgets;

import android.support.v4.app.FragmentActivity;
import android.view.View;



public class ActivityBase extends FragmentActivity {
    Class<? extends ActivityBaseFragment> clazz;
    public ActivityBase(Class<? extends ActivityBaseFragment> clazz) {
        super();
        this.clazz = clazz;
    }
    
    public ActivityBaseFragment getFragment() {
        return fragment;        
    }
    
    public View getView() {
        return fragment.getView();
    }
    
    ActivityBaseFragment fragment;
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
            fragment = (ActivityBaseFragment)clazz.getConstructors()[0].newInstance();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
