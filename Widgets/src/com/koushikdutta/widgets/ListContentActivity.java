package com.koushikdutta.widgets;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


public class ListContentActivity extends ActivityBase {
    public static class ListContentFragment extends ActivityBaseFragment {
        @Override
        public int getListItemResource() {
            return R.layout.list_item_selectable;
        }
    }

    public ListContentActivity() {
        super(ListContentFragment.class);
    }
    
    @Override
    protected int getListContainerId() {
        return R.id.list;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_content);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState, View view) {
        super.onCreate(savedInstanceState, view);
        
        getFragment().getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        getFragment().getListView().setCacheColorHint(android.R.color.transparent);
//        getFragment().getListView().setSelector(R.drawable.list_selector);
    }
}
