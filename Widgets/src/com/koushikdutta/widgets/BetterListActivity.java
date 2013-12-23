/*
 * Copyright (C) 2013 Koushik Dutta (@koush)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.koushikdutta.widgets;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.koushikdutta.widgets.BetterListFragment.ActivityBaseFragmentListener;



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

    protected int getListContainerId() {
        return R.id.activity_content;
    }
    
    protected int getContentView() {
        return R.layout.container_activity;
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
            fragment.setListener(this);
            getSupportFragmentManager().beginTransaction().replace(getListContainerId(), fragment, "betterlist").commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState, View view) {
    }


    protected ListItem addItem(int sectionName, ListItem item) {
        return fragment.addItem(getString(sectionName), item);
    }

    protected ListItem addItem(int sectionName, ListItem item, int index) {
        return fragment.addItem(getString(sectionName), item, index);
    }
    
    protected ListItem addItem(String sectionName, ListItem item) {
        return fragment.addItem(sectionName, item, -1);
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
