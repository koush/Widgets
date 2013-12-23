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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import junit.framework.Assert;

public class ListContentFragment extends BetterListFragment {
    ViewGroup mContent;
    ViewGroup mContainer;

    public ViewGroup getContainer() {
        return mContainer;
    }

    @Override
    protected int getListHeaderResource() {
        return R.layout.list_content_header;
    }

    Fragment mCurrentContent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState, View ret) {
        mContent = (ViewGroup)ret.findViewById(R.id.content);
        mContainer = (ViewGroup)ret.findViewById(R.id.list_content_container);

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        super.onCreate(savedInstanceState, ret);
    }
    
    public boolean isPaged() {
        return mContent == null;
    }
    
    int getContentId() {
        if (isPaged())
            return R.id.list_content_container;
        return R.id.content;
    }
    
    static Object backstackListener;
    static Object pendingFragment;

    
    @SuppressLint("InlinedApi")
    private void onDetachNative() {
        Activity fa = getActivity();
        fa.getFragmentManager().popBackStack("content", android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        
        if (getActivity() == null || getActivity().isFinishing() || getActivity().isChangingConfigurations())
            return;
        
        if (!isPaged())
            return;
        
        if (getActivity() instanceof FragmentActivity) {
            FragmentActivity fa = (FragmentActivity)getActivity();
            fa.getSupportFragmentManager().popBackStack("content", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        else {
            onDetachNative();
        }
    }
    
    Object listener;

    public void setContent(Fragment content, boolean clearChoices, String breadcrumb) {
        mCurrentContent = content;
        FragmentActivity fa = (FragmentActivity)getActivity();
        final FragmentManager fm = fa.getSupportFragmentManager();
        FragmentTransaction ft = fa.getSupportFragmentManager().beginTransaction();
        if (isPaged()) {
            ft.setCustomAnimations(R.anim.enter, R.anim.exit);
            View v = getView();
            Assert.assertNotNull(v);
            final View l = v.findViewById(R.id.list_fragment);
            Assert.assertNotNull(l);
            l.setVisibility(View.GONE);
            fm.addOnBackStackChangedListener(new OnBackStackChangedListener() {
                {
                    listener = this;
                }
                @Override
                public void onBackStackChanged() {
                    if (isDetached() || isRemoving()) {
                        fm.removeOnBackStackChangedListener(this);
                        return;
                    }
                    View v = getView();
                    if (v == null)
                        return;
                    final View l = v.findViewById(R.id.list_fragment);
                    if (l == null)
                        return;
                    if (fm.getBackStackEntryCount() > 0 && "content".equals(fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName())) {
                        l.setVisibility(View.GONE);
                    }
                    else {
                        l.setVisibility(View.VISIBLE);
                    }
                }
            });

            fm.popBackStack("content", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.setBreadCrumbTitle(breadcrumb);
            ft.setBreadCrumbShortTitle(breadcrumb);
            ft.addToBackStack("content");
        }
        ft.replace(getContentId(), mCurrentContent, "content");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        if (clearChoices)
            getListView().clearChoices();
    }

    @Override
    protected int getListItemResource() {
        return R.layout.list_item_selectable;
    }

    @Override
    protected int getListFragmentResource() {
        return R.layout.list_content;
    }
}
