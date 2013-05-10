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

import com.koushikdutta.widgets.BetterListFragmentInternal.ListItemAdapter;

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
    
    
    public void removeSection(int section) {
        getInternal().removeSection(section);
    }
    
    public void removeSection(String section) {
        getInternal().removeSection(section);
    }
    
    
    
    public ListItemAdapter ensureHeader(int sectionName) {
        return getInternal().ensureHeader(sectionName);
    }
    
    public ListItemAdapter ensureHeader(String sectionName) {
        return getInternal().ensureHeader(sectionName);
    }

    public ListItemAdapter ensureHeader(int index, int sectionName) {
        return getInternal().ensureHeader(index, sectionName);
    }
    public ListItemAdapter ensureHeader(int index, String sectionName) {
        return getInternal().ensureHeader(index, sectionName);

    }
    
    public ListItemAdapter getSection(int section) {
        return getInternal().getSection(section);
    }
    
    public ListItemAdapter getSection(String section) {
        return getInternal().getSection(section);
    }

    public ListItem addItem(int sectionName, ListItem item) {
        return getInternal().addItem(sectionName, item);
    }

    public ListItem addItem(int sectionName, ListItem item, int index) {
        return getInternal().addItem(sectionName, item);
    }
    
    public ListItem addItem(String sectionName, ListItem item) {
        return getInternal().addItem(sectionName, item);
    }

    public void clear() {
        getInternal().clear();
    }
    
    public void clearSection(int section) {
        getInternal().clearSection(section);
    }
    
    public void clearSection(String section) {
        getInternal().clearSection(section);
    }
    
    public void removeItem(ListItem item) {
        getInternal().removeItem(item);
    }
    
}
