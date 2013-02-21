package com.koushikdutta.widgets;

import android.support.v4.app.Fragment;

public interface ListContentAdapter {
    public Fragment getFragment(ListItem listItem, Fragment convertFragment);
}
