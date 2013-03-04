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
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class NativeFragment<T extends FragmentInterface> extends Fragment implements FragmentInterfaceWrapper {
    public abstract T createFragmentInterface();
    
    @Override
    public void onResume() {
        super.onResume();
        internal.onResume();
    }
    
    @Override
    public Context getContext() {
        return getActivity();
    }
    
    public void onDetach() {
        super.onDetach();
        internal.onDetach();
    };
    
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        internal.onAttach(activity);
    };
    
    T internal;
    public NativeFragment() {
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
