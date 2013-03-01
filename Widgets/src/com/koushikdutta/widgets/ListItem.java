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
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItem {
    private String Title;
    private String Summary;
    private BetterListFragmentInternal mFragment;
    private boolean Enabled = true;

    private int Icon;
    
    public ListItem setIcon(int icon) {
        mDrawable = null;
        Icon = icon;
        mFragment.mAdapter.notifyDataSetChanged();
        return this;
    }
    
    public ListItem setDrawable(Drawable drawable) {
        mDrawable = drawable;
        Icon = 0;
        mFragment.mAdapter.notifyDataSetChanged();
        return this;
    }
    
    int mAttr = 0;
    public ListItem setAttrDrawable(int attr) {
        mAttr = attr;
        mDrawable = null;
        Icon = 0;
        return this;
    }
    
    public ListItem setEnabled(boolean enabled) {
        Enabled = enabled;
        mFragment.mAdapter.notifyDataSetChanged();
        return this;
    }
    
    public boolean getEnabled() {
        return Enabled;
    }
    
    public ListItem setTitle(int title) {
        if (title == 0)
            return setTitle(null);
        else
            return setTitle(mFragment.getString(title));
    }
    
    public String getTitle() {
        return Title;
    }
    
    public ListItem setTitle(String title) {
        Title = title;
        mFragment.mAdapter.notifyDataSetChanged();
        return this;
    }

    public ListItem setSummary(int summary) {
        if (summary == 0)
            return setSummary(null);
        else
            return setSummary(mFragment.getString(summary));
    }
    
    public ListItem setSummary(String summary) {
        Summary = summary;
        mFragment.mAdapter.notifyDataSetChanged();
        return this;
    }
    
    public ListItem(BetterListFragmentInternal context, int title, int summary) {
        if (title != 0)
            Title = context.getString(title);
        if (summary != 0)
            Summary = context.getString(summary);
        mFragment = context;
    }
    
    public ListItem(BetterListFragmentInternal context, String title, String summary) {
        Title = title;
        Summary = summary;
        mFragment = context;
    }
    
    public ListItem(BetterListFragmentInternal context, int title, int summary, int icon) {
        this(context, title, summary);
        Icon = icon;
    }
    
    public ListItem(BetterListFragmentInternal context, String title, String summary, int icon) {
        this(context, title, summary);
        Icon = icon;
    }
    
    Drawable mDrawable;
    public ListItem(BetterListFragmentInternal context, String title, String summary, Drawable drawable) {
        this(context, title, summary);
        mDrawable = drawable;
    }
    
    private boolean CheckboxVisible = false;
    private boolean checked = false;
    
    public ListItem setCheckboxVisible(boolean visible) {
        CheckboxVisible = visible;
        mFragment.mAdapter.notifyDataSetChanged();
        return this;
    }

    public boolean getCheckboxVisible() {
        return CheckboxVisible;
    }
    
    public boolean getChecked() {
        return checked;
    }
    
    public ListItem setChecked(boolean isChecked) {
        checked = isChecked;
        CheckboxVisible = true;
        mFragment.mAdapter.notifyDataSetChanged();
        return this;
    }
    
    public View getView(Context context, View convertView) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null || convertView.getTag() != null) {
            convertView = inflater.inflate(mFragment.getListItemResource(), null);
        }
        
        TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView summary = (TextView)convertView.findViewById(R.id.summary);
        CompoundButton cb = (CompoundButton)convertView.findViewById(R.id.checkbox);
        cb.setOnCheckedChangeListener(null);
        cb.setChecked(checked);
        final View cv = convertView;
        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checked = isChecked;
                ListItem.this.onClick(cv);
            }
        });

        cb.setVisibility(CheckboxVisible ? View.VISIBLE : View.GONE);
        cb.setChecked(checked);

        cb.setEnabled(Enabled);
        title.setEnabled(Enabled);
        summary.setEnabled(Enabled);

        if (Title != null) {
            title.setVisibility(View.VISIBLE);
            title.setText(Title);
        }
        else
            title.setVisibility(View.GONE);
        if (Summary != null) {
            summary.setVisibility(View.VISIBLE);
            summary.setText(Summary);
        }
        else
            summary.setVisibility(View.GONE);

        ImageView iv = (ImageView)convertView.findViewById(R.id.image);
        if (iv != null) {
            if (mAttr != 0) {
                Context ctx = inflater.getContext();
                TypedValue value = new TypedValue();
                ctx.getTheme().resolveAttribute(mAttr, value, true);
                mAttr = 0;
                Icon = value.resourceId;
            }
            
            if (Icon != 0) {
                iv.setVisibility(View.VISIBLE);
                iv.setImageResource(Icon);
            }
            else if (mDrawable != null) {
                iv.setVisibility(View.VISIBLE);
                iv.setImageDrawable(mDrawable);
            }
            else {
                iv.setVisibility(View.GONE);
            }
        }
        
        return convertView;
    }
    
    void onClickInternal(View view) {
        if (CheckboxVisible) {
            CompoundButton cb = (CompoundButton)view.findViewById(R.id.checkbox);
            // this will trigger onclick
            cb.setChecked(!cb.isChecked());
        }
        else {
            onClick(view);
        }
    }
    
    public void onClick(View view) {
    }
    
    public boolean onLongClick() {
        return false;
    }
}
