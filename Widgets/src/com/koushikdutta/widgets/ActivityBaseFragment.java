package com.koushikdutta.widgets;

import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityBaseFragment extends Fragment {
    protected boolean mDestroyed = false;
    
    protected ListView mListView;
    protected MyAdapter mAdapter;
    
    protected class MyAdapter extends SeparatedListAdapter {
        public MyAdapter(Context context) {
            super(context);
        }
        
        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }
        
        @Override
        public boolean isEnabled(int position) {
            if (!super.isEnabled(position))
                return false;
            ListItem item = (ListItem) getItem(position);
            return item.getEnabled();
        }
    }
    
    public class MyListAdapter extends ArrayAdapter<ListItem> {
        public MyListAdapter(Context context) {
            super(context, 0);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItem item = getItem(position);
            return item.getView(getContext(), convertView);
        }
        
        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }
        
        @Override
        public boolean isEnabled(int position) {
            ListItem item = getItem(position);
            return item.getEnabled();
        }
    }
    
    HashMap<String, MyListAdapter> mAdapters = new HashMap<String, ActivityBaseFragment.MyListAdapter>();
    
    protected MyListAdapter ensureHeader(int sectionName) {
        String sn = getString(sectionName);
        MyListAdapter adapter = mAdapters.get(sn);
        if (adapter == null) {
            adapter = new MyListAdapter(getActivity());
            mAdapters.put(sn, adapter);
            mAdapter.addSection(sn, adapter);
            mListView.setAdapter(null);
            mListView.setAdapter(mAdapter);
        }
        return adapter;
    }

    protected ListItem addItem(int sectionName, ListItem item) {
        return addItem(getString(sectionName), item);
    }

    protected ListItem addItem(int sectionName, ListItem item, int index) {
        return addItem(getString(sectionName), item, index);
    }
    
    protected ListItem addItem(String sectionName, ListItem item) {
        return addItem(sectionName, item, -1);
    }
    
    public int getSectionItemCount(int section) {
        return getSectionItemCount(getString(section));
    }
    
    public int getSectionItemCount(String section) {
        MyListAdapter adapter = mAdapters.get(section);
        if (adapter == null)
            return 0;
        return adapter.getCount();
    }

    protected ListItem addItem(String sectionName, ListItem item, int index) {
        MyListAdapter adapter = mAdapters.get(sectionName);
        if (adapter == null) {
            adapter = new MyListAdapter(getActivity());
            mAdapters.put(sectionName, adapter);
            mAdapter.addSection(sectionName, adapter);
            if (mListView != null) {
                mListView.setAdapter(null);
                mListView.setAdapter(mAdapter);
            }
//            mAdapter.notifyDataSetChanged();
        }
        
        if (index != -1)
            adapter.insert(item, index);
        else
            adapter.add(item);
        
        return item;
    }
    
    protected ListItem findItem(int item) {
        String text = getString(item);
        
        for (Adapter adapter: mAdapter.sections.values())
        {
            MyListAdapter m = (MyListAdapter)adapter;
            for (int i = 0; i < m.getCount(); i++) {
                ListItem li = m.getItem(i);
                if (text.equals(li.getTitle()))
                    return li;
            }
        }
        
        return null;
    }
    
    private ActivityBaseFragmentListener mListener;
    public ActivityBaseFragmentListener getListener() {
        return mListener;
    }
    
    public void setListener(ActivityBaseFragmentListener listener) {
        mListener = listener;
    }
    
    public static interface ActivityBaseFragmentListener {
        void onCreate(Bundle savedInstanceState, View view);
    }
    
    protected void onCreate(Bundle savedInstanceState, View view) {
        if (mListener != null)
            mListener.onCreate(savedInstanceState, view);
    }
    
    TextView mEmpty;
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.activity_base_fragment, null);
        
        mListView = (ListView)ret.findViewById(R.id.listview);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Object item = mAdapter.getItem(arg2);
                if (item instanceof ListItem) {
                    ListItem li = (ListItem)item;
                    li.onClickInternal(arg1);
                }
            }
        });
        
        mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Object item = mAdapter.getItem(arg2);
                if (item instanceof ListItem) {
                    ListItem li = (ListItem)item;
                    return li.onLongClick();
                }
                return false;
            }
        });
        
        mListView.setAdapter(mAdapter);
        mEmpty = (TextView)ret.findViewById(R.id.empty);
        
        onCreate(savedInstanceState, ret);
        return ret;
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new MyAdapter(getActivity());
    }

    Handler handler = new Handler();
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        mDestroyed = true;
    }
    
    public int getListItemResource() {
        return R.layout.list_item_small;
    }

    protected void clear() {
        mAdapter.clear();
        mAdapters.clear();
    }
    
    protected void clearSection(int section) {
        clearSection(getActivity().getString(section));
    }
    
    protected void clearSection(String section) {
        MyListAdapter adapter = mAdapters.get(section);
        if (adapter == null)
            return;
        adapter.clear();
        mAdapter.notifyDataSetChanged();
    }
    
    public void removeItem(ListItem item) {
        for (MyListAdapter adapter: mAdapters.values()) {
            adapter.remove(item);
        }
        mAdapter.notifyDataSetChanged();
    }
    
    public void setEmpty(int res) {
        mListView.setEmptyView(mEmpty);
        mEmpty.setText(res);
    }
}
