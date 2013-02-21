package com.koushikdutta.widgets.sample;

import android.os.Bundle;
import android.view.View;

import com.koushikdutta.widgets.ListContentActivity;
import com.koushikdutta.widgets.ListItem;

public class ListContentTest extends ListContentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, View view) {
        super.onCreate(savedInstanceState, view);

//        android:background="?android:attr/activatedBackgroundIndicator"
        addItem(R.string.devices, new ListItem(getFragment(), "Nexus S", null, R.drawable.nexusone));
        addItem(R.string.devices, new ListItem(getFragment(), "Nexus One", null, R.drawable.nexusone));
        addItem(R.string.devices, new ListItem(getFragment(), "Nexus 4", null, R.drawable.nexusone));
        addItem(R.string.devices, new ListItem(getFragment(), "Nexus 7", null, R.drawable.nexusone));
        addItem(R.string.devices, new ListItem(getFragment(), "Nexus 10", null, R.drawable.nexusone));

        getFragment().getListView().setItemChecked(0, true);
    }
}
