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

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState, View ret) {
        mContent = (ViewGroup)ret.findViewById(R.id.content);
        mContainer = (ViewGroup)ret.findViewById(R.id.list_content_container);

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        if (!isPaged()) {
            if (getFragmentManager().findFragmentByTag("content") != null)
                ret.findViewById(R.id.content).setVisibility(View.VISIBLE);
        }

        super.onCreate(savedInstanceState, ret);
    }

    public static boolean isPaged(Activity activity) {
        return activity.findViewById(R.id.content) == null;
    }
    
    public boolean isPaged() {
        return mContent == null;
    }
    
    int getContentId() {
        if (isPaged())
            return R.id.activity_content;
        return R.id.content;
    }

    public void setContent(Fragment content, boolean clearChoices, CharSequence breadcrumb) {
        FragmentActivity fa = (FragmentActivity)getActivity();
        final FragmentManager fm = fa.getSupportFragmentManager();
        FragmentTransaction ft = fa.getSupportFragmentManager().beginTransaction();
        if (isPaged()) {
            ft.setCustomAnimations(R.anim.enter, R.anim.exit);
            ft.remove(this);
            fm.popBackStack("content", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.setBreadCrumbTitle(breadcrumb);
            ft.setBreadCrumbShortTitle(breadcrumb);
            ft.addToBackStack("content");
        }
        else {
            if (getView() != null)
                getView().findViewById(R.id.content).setVisibility(View.VISIBLE);
        }
        ft.replace(getContentId(), content, "content");
        int transition = getView() == null ? FragmentTransaction.TRANSIT_NONE : FragmentTransaction.TRANSIT_FRAGMENT_FADE;
        ft.setTransition(transition);
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
