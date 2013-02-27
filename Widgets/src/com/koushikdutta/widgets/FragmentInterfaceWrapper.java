package com.koushikdutta.widgets;

import android.app.Activity;

public interface FragmentInterfaceWrapper {
    public Activity getActivity();
    public FragmentInterface getInternal();
    public void setHasOptionsMenu(boolean options);
}
