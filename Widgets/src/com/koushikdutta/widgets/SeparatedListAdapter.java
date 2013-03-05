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

import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

public class SeparatedListAdapter<T extends Adapter> extends BaseAdapter {
    public void clear() {
        sections.clear();
        headers.clear();
        notifyDataSetChanged();
    }
    
    protected int getListHeaderResource() {
        return R.layout.list_header;
    }

    private final HashMap<String, T> sections = new HashMap<String, T>();
    private final ArrayAdapter<String> headers;
    private final static int TYPE_SECTION_HEADER = 0;

    public SeparatedListAdapter(Context context) {
        headers = new ArrayAdapter<String>(context, getListHeaderResource(), android.R.id.text1);
    }

    public void addSection(String section, T adapter) {
        this.headers.add(section);
        this.sections.put(section, adapter);
        notifyDataSetChanged();
    }

    public void addSection(int index, String section, T adapter) {
        this.headers.insert(section, index);
        this.sections.put(section, adapter);
        notifyDataSetChanged();
    }
    
    public void removeSection(String section) {
        this.headers.remove(section);
        this.sections.remove(section);
        notifyDataSetChanged();
    }
    
    public T getSection(String section) {
        return sections.get(section);
    }
    
    public Iterable<T> getSections() {
        return sections.values();
    }

    @Override
    public Object getItem(int position) {
        for (int i = 0; i < headers.getCount(); i++) {
            String section = headers.getItem(i);
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if (position == 0)
                return section;
            if (position < size)
                return adapter.getItem(position - 1);

            // otherwise jump into next section
            position -= size;
        }
        return null;
    }

    public int getCount() {
        // total together all sections, plus one for each section header
        int total = 0;
        for (Adapter adapter : this.sections.values())
            total += adapter.getCount() + 1;
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
        int type = 1;
        for (int i = 0; i < headers.getCount(); i++) {
            String section = headers.getItem(i);
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if (position == 0)
                return TYPE_SECTION_HEADER;
            if (position < size)
                return type + adapter.getItemViewType(position - 1);

            // otherwise jump into next section
            position -= size;
            type += adapter.getViewTypeCount();
        }
        return -1;
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) != TYPE_SECTION_HEADER);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int sectionnum = 0;
        for (int i = 0; i < headers.getCount(); i++) {
            String section = headers.getItem(i);
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if (position == 0)
                return headers.getView(sectionnum, convertView, parent);
            if (position < size)
                return adapter.getView(position - 1, convertView, parent);

            // otherwise jump into next section
            position -= size;
            sectionnum++;
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
