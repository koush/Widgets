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

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
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

    public boolean isPaged() {
        return mContent == null;
    }
    
    int getContentId() {
        if (isPaged())
            return R.id.activity_content;
        return R.id.content;
    }

    public void setContent(Fragment content, boolean clearChoices, String breadcrumb) {
        final FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (isPaged()) {
            ft.remove(this);
            fm.popBackStack("content", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.setBreadCrumbTitle(breadcrumb);
            ft.setBreadCrumbShortTitle(breadcrumb);
            ft.addToBackStack(breadcrumb);
        }
        else {
            if (getView() != null)
                getView().findViewById(R.id.content).setVisibility(View.VISIBLE);
        }
        Fragment c = fm.findFragmentByTag("content");
        if (c != null) {
            if (!isPaged())
                ft.addToBackStack(breadcrumb);
//            ft.remove(c);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.replace(getContentId(), content, "content");
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
