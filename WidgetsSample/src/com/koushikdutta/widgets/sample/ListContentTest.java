package com.koushikdutta.widgets.sample;

import java.util.Date;
import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.koushikdutta.widgets.BetterListActivity;
import com.koushikdutta.widgets.ListContentFragment;
import com.koushikdutta.widgets.ListContentFragmentInternal;
import com.koushikdutta.widgets.ListItem;

public class ListContentTest extends BetterListActivity {
    public ListContentTest() {
        super(ListContentFragment.class);
    }
    
    public ListContentFragmentInternal getFragment() {
        return (ListContentFragmentInternal)super.getFragment();
    }
    
    protected void onResume() {
        super.onResume();
        getFragment().getListView().setItemChecked(1, true);

    };
    
    
    Handler handler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState, View view) {
        super.onCreate(savedInstanceState, view);

        List<PackageInfo> pkgs = getPackageManager().getInstalledPackages(0);
        int count = 0;
        for (PackageInfo pkg: pkgs) {
//            if (count == 6)
//                break;
            if ((pkg.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
                continue;
            
            count++;

            addItem("Allow", new ListItem(getFragment(), pkg.applicationInfo.loadLabel(getPackageManager()).toString(), new Date().toLocaleString(), pkg.applicationInfo.loadIcon(getPackageManager())));
        }
        
//        android:background="?android:attr/activatedBackgroundIndicator"
//        addItem("Allow", new ListItem(getFragment(), "Nexus S", null, R.drawable.nexusone));
//        addItem("Allow", new ListItem(getFragment(), "Nexus One", null, R.drawable.nexusone));
//        addItem("Allow", new ListItem(getFragment(), "Nexus 4", null, R.drawable.nexusone));
//        addItem("Deny", new ListItem(getFragment(), "Nexus 7", null, R.drawable.nexusone));
//        addItem("Deny", new ListItem(getFragment(), "Nexus 10", null, R.drawable.nexusone));

        handler.postDelayed(new Runnable() {
            
            @Override
            public void run() {
//                getFragment().getListView().requestFocus();
//                getFragment().getListView().requestFocusFromTouch();
//                getFragment().getListView().setItemChecked(1, true);
            }
        }, 200);
//        getFragment().getListView().setItemChecked(1, true);
        
//        getFragment().setContentAdapter(new ListContentAdapter() {
//            @Override
//            public View getView(ListItem listItem, View convertView) {
//                TextView tv = new TextView(ListContentTest.this);
//                tv.setText(listItem.getTitle());
//                return tv;
//            }
//        });
        
//        getFragment().getListView().setPadding(0, 0, 0, 0);
    }
}
