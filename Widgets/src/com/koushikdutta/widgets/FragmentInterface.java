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
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FragmentInterface {
    protected FragmentInterfaceWrapper mFragment;
    
    public Activity getActivity() {
        return getFragment().getActivity();
    }
    
    public Context getContext() {
        return getFragment().getContext();
    }
    
    public FragmentInterfaceWrapper getFragment() {
        return mFragment;
    }
    
    public FragmentInterface(FragmentInterfaceWrapper fragment) {
        mFragment = fragment;
    }
    
    public String getString(int res) {
        return getActivity().getString(res);
    }    
    public String getString(int res, Object... formatArgs) {
        return getActivity().getString(res, formatArgs);
    }
    
    public Resources getResources() {
        return getActivity().getResources();
    }
    
    abstract public void onConfigurationChanged(Configuration newConfig);

    abstract public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    
    abstract public void onCreate(Bundle savedInstanceState);

    abstract public void onCreateOptionsMenu(Menu menu, MenuInflater inflater);

    abstract public void onDetach();
    abstract public void onAttach(Activity activity);
    abstract public void onResume();
}
