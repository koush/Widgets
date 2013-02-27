package com.koushikdutta.widgets;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class SupportFragment<T extends FragmentInterface> extends Fragment implements FragmentInterfaceWrapper {
    public abstract T createFragmentInterface();
    
    T internal;
    public SupportFragment() {
        internal = createFragmentInterface();
    }

    @Override
    public T getInternal() {
        return internal;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        internal.onCreate(savedInstanceState);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        internal.onConfigurationChanged(newConfig);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return internal.onCreateView(inflater, container, savedInstanceState);
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        
        internal.onCreateOptionsMenu(menu, inflater);
    }
}
