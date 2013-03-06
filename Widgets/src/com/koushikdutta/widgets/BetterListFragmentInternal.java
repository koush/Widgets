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

import java.util.Comparator;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BetterListFragmentInternal extends FragmentInterface {
    ListView mListView;
    MyAdapter mAdapter;
    
    @Override
    public void onResume() {
    }
    
    public BetterListFragmentInternal(FragmentInterfaceWrapper fragment) {
        super(fragment);
    }
    
    public class MyAdapter extends SeparatedListAdapter<ListItemAdapter> {
        public MyAdapter(Context context) {
            super(context);
        }
        
        @Override
        protected int getListHeaderResource() {
            return BetterListFragmentInternal.this.getListHeaderResource();
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
            if (item == null)
                return false;
            return item.getEnabled();
        }
    }
    
    public class ListItemAdapter extends ArrayAdapter<ListItem> {
        public ListItemAdapter(Context context) {
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
        
        boolean sorting;
        @Override
        public void notifyDataSetChanged() {
            if (sorter != null && !sorting) {
                sorting = true;
                super.sort(sorter);
                sorting = false;
            }
            else {
                super.notifyDataSetChanged();
            }
        }
        
        private Comparator<ListItem> sorter;
        public void setSort(Comparator<ListItem> sorter) {
            this.sorter = sorter;
            notifyDataSetChanged();
            mAdapter.notifyDataSetChanged();
        }
        
        public Comparator<ListItem> getSort() {
            return sorter;
        }
        
        public Comparator<ListItem> ALPHA = new Comparator<ListItem>() {
            @Override
            public int compare(ListItem lhs, ListItem rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        };
        
        public Comparator<ListItem> ALPHAIGNORECASE = new Comparator<ListItem>() {
            @Override
            public int compare(ListItem lhs, ListItem rhs) {
                return lhs.getTitle().compareToIgnoreCase(rhs.getTitle());
            }
        };

        public Comparator<ListItem> NONE = new Comparator<ListItem>() {
            @Override
            public int compare(ListItem lhs, ListItem rhs) {
                return ((Integer)getPosition(lhs)).compareTo(getPosition(rhs));
            }
        };
    }
    
//    HashMap<String, MyListAdapter> mAdapters = new HashMap<String, ActivityBaseFragment.MyListAdapter>();
    
    public ListItemAdapter ensureHeader(int sectionName) {
        return ensureHeader(mAdapter.getSectionCount(), getContext().getString(sectionName));
    }
    
    public ListItemAdapter ensureHeader(String sectionName) {
        return ensureHeader(mAdapter.getSectionCount(), sectionName);
    }

    public ListItemAdapter ensureHeader(int index, int sectionName) {
        return ensureHeader(index, getContext().getString(sectionName));
    }
    public ListItemAdapter ensureHeader(int index, String sectionName) {
        ListItemAdapter adapter = mAdapter.getSection(sectionName);
        if (adapter == null) {
            adapter = new ListItemAdapter(getContext());
            mAdapter.addSection(index, sectionName, adapter);
            mListView.setAdapter(null);
            mListView.setAdapter(mAdapter);
        }
        return adapter;
    }
    
    public ListItemAdapter getSection(int section) {
        return getSection(getContext().getString(section));
    }
    
    public ListItemAdapter getSection(String section) {
        return mAdapter.getSection(section);
    }

    public ListItem addItem(int sectionName, ListItem item) {
        return addItem(getContext().getString(sectionName), item);
    }

    public ListItem addItem(int sectionName, ListItem item, int index) {
        return addItem(getContext().getString(sectionName), item, index);
    }
    
    public ListItem addItem(String sectionName, ListItem item) {
        return addItem(sectionName, item, -1);
    }
    
    public int getSectionItemCount(int section) {
        return getSectionItemCount(getContext().getString(section));
    }
    
    public int getSectionItemCount(String section) {
        ListItemAdapter adapter = mAdapter.getSection(section);
        if (adapter == null)
            return 0;
        return adapter.getCount();
    }

    public ListItem addItem(String sectionName, ListItem item, int index) {
        ListItemAdapter adapter = mAdapter.getSection(sectionName);
        if (adapter == null) {
            adapter = new ListItemAdapter(getContext());
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

        mAdapter.notifyDataSetChanged();
        return item;
    }
    
    public ListItem findItem(int item) {
        String text = getContext().getString(item);
        
        for (ListItemAdapter adapter: mAdapter.getSections())
        {
            for (int i = 0; i < adapter.getCount(); i++) {
                ListItem li = adapter.getItem(i);
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
    
    protected int getListFragmentResource() {
        return R.layout.list_fragment;
    }
    
    void onListItemClick(ListItem li) {
        
    }

    ViewGroup mTitleContainer;
    TextView mEmpty;
    
    
    protected int getListHeaderResource() {
        return R.layout.list_header;
    }
    
    protected int getListItemResource() {
        return R.layout.list_item;
    }

    public void clear() {
        mAdapter.clear();
    }
    
    public void clearSection(int section) {
        clearSection(mListView.getContext().getString(section));
    }
    
    public void clearSection(String section) {
        ListItemAdapter adapter = mAdapter.getSection(section);
        if (adapter == null)
            return;
        adapter.clear();
        mAdapter.notifyDataSetChanged();
    }
    
    public void removeSection(int section) {
        removeSection(mListView.getContext().getString(section));
    }
    
    public void removeSection(String section) {
        mAdapter.removeSection(section);
    }
    
    public void removeItem(ListItem item) {
        for (ListItemAdapter adapter: mAdapter.getSections()) {
            adapter.remove(item);
        }
        mAdapter.notifyDataSetChanged();
    }
    
    public void setEmpty(int res) {
        mListView.setEmptyView(mEmpty);
        mEmpty.setText(res);
    }
    
    public ListView getListView() {
        return mListView;
    }
    
    public MyAdapter getAdapter() {
        return mAdapter;
    }
    
    public ViewGroup getTitleContainer() {
        return mTitleContainer;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mListView.invalidateViews();
    }

    @Override
    public void onDetach() {
    }
    
    @Override
    public void onAttach(Activity activity) {
    }
    
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret = inflater.inflate(getListFragmentResource(), container, false);

        mTitleContainer = (ViewGroup)ret.findViewById(R.id.title_container);

        mListView = (ListView)ret.findViewById(R.id.listview);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Object item = mAdapter.getItem(arg2);
                if (item instanceof ListItem) {
                    ListItem li = (ListItem)item;
                    onListItemClick(li);
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
        
        mEmpty = (TextView)ret.findViewById(R.id.empty);

        onCreate(savedInstanceState, ret);
        mListView.setAdapter(mAdapter);
        return ret;
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        mAdapter = new MyAdapter(getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }
}
