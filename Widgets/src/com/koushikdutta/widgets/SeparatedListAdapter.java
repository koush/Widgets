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

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.HashSet;

public class SeparatedListAdapter<T extends Adapter> extends BaseAdapter {
    private final static int TYPE_SECTION_HEADER = 0;
    private final HashMap<String, T> sections = new HashMap<String, T>();
    private final ArrayAdapter<String> headers;
    private boolean hideEmpty;
    private HashSet<String> hiddenSections = new HashSet<String>();

    private boolean isSectionHeaderHidden(String section, T adapter) {
        return hiddenSections.contains(section) || (hideEmpty && adapter.getCount() == 0);
    }

    private DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            SeparatedListAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            SeparatedListAdapter.this.notifyDataSetInvalidated();
        }
    };

    public void hideSectionHeader(String section) {
        hiddenSections.add(section);
    }

    public SeparatedListAdapter(Context context) {
        headers = new ArrayAdapter<String>(context, getListHeaderResource(), android.R.id.text1);
    }

    public void clear() {
        sections.clear();
        headers.clear();
        notifyDataSetChanged();
    }

    public boolean getHideEmptySections() {
        return hideEmpty;
    }

    public void setHideEmptySections(boolean hideEmptySections) {
        this.hideEmpty = hideEmptySections;
    }

    protected int getListHeaderResource() {
        return R.layout.list_header;
    }

    public void addSection(String section, T adapter) {
        this.headers.add(section);
        this.sections.put(section, adapter);
        adapter.registerDataSetObserver(observer);
        notifyDataSetChanged();
    }

    public void addSection(int index, String section, T adapter) {
        this.headers.insert(section, index);
        this.sections.put(section, adapter);
        adapter.registerDataSetObserver(observer);
        notifyDataSetChanged();
    }

    public void removeSection(String section) {
        this.headers.remove(section);
        T adapter = this.sections.remove(section);
        if (adapter != null)
            adapter.unregisterDataSetObserver(observer);
        notifyDataSetChanged();
    }

    public T getSection(String section) {
        return sections.get(section);
    }

    public Iterable<T> getSections() {
        return sections.values();
    }

    private class ItemAdapterInfo {
        String section;
        T adapter;
        int adapterPosition;
        int position;
    }

    private ItemAdapterInfo getItemAdapterInfo(int position) {
        for (int i = 0; i < headers.getCount(); i++) {
            String section = headers.getItem(i);
            T adapter = sections.get(section);
            int size = adapter.getCount();
            boolean sectionHidden = isSectionHeaderHidden(section, adapter);
            if (!sectionHidden)
                size++;

            // check if position inside this section
            if (position < size) {
                ItemAdapterInfo info = new ItemAdapterInfo();
                info.adapter = adapter;
                info.position = position;
                if (sectionHidden)
                    info.position++;
                info.adapterPosition = i;
                info.section = section;
                return info;
            }

            // otherwise jump into next section
            position -= size;
        }
        return null;
    }

    public Adapter getItemAdapter(int position) {
        ItemAdapterInfo info = getItemAdapterInfo(position);
        if (info == null)
            return null;
        return info.adapter;
    }

    @Override
    public Object getItem(int position) {
        ItemAdapterInfo info = getItemAdapterInfo(position);
        if (info == null)
            return null;
        if (info.position == 0)
            return info.section;
        return info.adapter.getItem(info.position - 1);
    }

    @Override
    public int getCount() {
        // total together all sections, plus one for each section header
        int total = 0;
        for (int i = 0; i < headers.getCount(); i++) {
            String section = headers.getItem(i);
            T adapter = sections.get(section);
            total += adapter.getCount();
            if (!isSectionHeaderHidden(section, adapter))
                total++;
        }
        return total;
    }

    public int getSectionCount() {
        return headers.getCount();
    }

    @Override
    public int getViewTypeCount() {
        // assume that headers count as one, and that there will be at least a itemViewType.
        // then total all sections
        int total = 2;
        for (Adapter adapter : this.sections.values())
            total += adapter.getViewTypeCount();
        return total;
    }

    @Override
    public int getItemViewType(int position) {
        ItemAdapterInfo info = getItemAdapterInfo(position);
        if (info == null)
            return -1;
        if (info.position == 0)
            return TYPE_SECTION_HEADER;
        return 1 + info.adapterPosition + info.adapter.getItemViewType(info.position - 1);
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) != TYPE_SECTION_HEADER);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemAdapterInfo info = getItemAdapterInfo(position);
        if (info == null)
            return null;
        if (info.position == 0)
            return headers.getView(info.adapterPosition, convertView, parent);
        return info.adapter.getView(info.position - 1, convertView, parent);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
