package com.koushikdutta.widgets;

import android.os.Bundle;
import android.view.View;

public class BetterListFragment extends SupportFragment<BetterListFragmentInternal> {
    @Override
    public BetterListFragmentInternal createFragmentInterface() {
        return new BetterListFragmentInternal(this) {
            @Override
            protected int getListFragmentResource() {
                int ret = BetterListFragment.this.getListFragmentResource();
                if (ret == -1)
                    return super.getListFragmentResource();
                return ret;
            }
            
            @Override
            protected void onCreate(Bundle savedInstanceState, View view) {
                super.onCreate(savedInstanceState, view);
                BetterListFragment.this.onCreate(savedInstanceState, view);
            }
        };
    }
    
    protected int getListFragmentResource() {
        return -1;
    }
    
    protected void onCreate(Bundle savedInstanceState, View view) {
    }
}
