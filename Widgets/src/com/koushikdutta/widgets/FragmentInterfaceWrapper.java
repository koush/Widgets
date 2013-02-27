package com.koushikdutta.widgets;

import android.app.Activity;
import android.os.Bundle;

public interface FragmentInterfaceWrapper {
    public Activity getActivity();
    public FragmentInterface getInternal();
    public void setHasOptionsMenu(boolean options);
    
    void setArguments(Bundle bundle);
    Bundle getArguments();
}
