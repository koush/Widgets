package com.koushikdutta.widgets.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.koushikdutta.widgets.BetterListActivity;
import com.koushikdutta.widgets.ListItem;


public class MainActivity extends BetterListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, View view) {
        super.onCreate(savedInstanceState, view);
        
        addItem("Content", new ListItem(getFragment(), "List Content", null, R.drawable.ic_launcher) {
            @Override
            public void onClick(View view) {
                super.onClick(view);
                startActivity(new Intent(MainActivity.this, ListContentTest.class));
            }
        });        
        addItem("Theme", new ListItem(getFragment(), "Dark Theme", null, R.drawable.ic_launcher) {
            @Override
            public void onClick(View view) {
                super.onClick(view);
                startActivity(new Intent(MainActivity.this, MainActivityDark.class));
            }
        });
        
        addItem("Theme", new ListItem(getFragment(), "Content", "Dark Theme", R.drawable.ic_launcher) {
            @Override
            public void onClick(View view) {
                super.onClick(view);
                startActivity(new Intent(MainActivity.this, ListContentTestDark.class));
            }
        });
        addItem(R.string.cloud, new ListItem(getFragment(), R.string.googledrive, R.string.googledrive_summary, R.drawable.drive) {
            @Override
            public void onClick(View view) {
                super.onClick(view);
                Toast.makeText(MainActivity.this, "You clicked Google Drive!", Toast.LENGTH_SHORT).show();
            }
        });
        addItem(R.string.cloud, new ListItem(getFragment(), R.string.dropbox, 0, R.drawable.dropbox));
        addItem(R.string.cloud, new ListItem(getFragment(), R.string.box, 0, R.drawable.box));
        
        addItem(R.string.devices, new ListItem(getFragment(), "Nexus S", null, R.drawable.nexusone)).setCheckboxVisible(true).setChecked(true);
        addItem(R.string.devices, new ListItem(getFragment(), "Nexus One", null, R.drawable.nexusone)).setCheckboxVisible(true);
        addItem(R.string.devices, new ListItem(getFragment(), "Nexus 4", null, R.drawable.nexusone)).setCheckboxVisible(true);
        addItem(R.string.devices, new ListItem(getFragment(), "Nexus 7", null, R.drawable.nexusone)).setCheckboxVisible(true).setChecked(true);;
        addItem(R.string.devices, new ListItem(getFragment(), "Nexus 10", null, R.drawable.nexusone)).setCheckboxVisible(true);
    }
}
