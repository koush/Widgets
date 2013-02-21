package com.koushikdutta.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItem {
    private String Title;
    private String Summary;
    private ActivityBaseFragment Context;
    private boolean Enabled = true;

    private int Icon;
    
    public ListItem setIcon(int icon) {
        mDrawable = null;
        Icon = icon;
        Context.mAdapter.notifyDataSetChanged();
        return this;
    }
    
    public ListItem setDrawable(Drawable drawable) {
        mDrawable = drawable;
        Icon = 0;
        Context.mAdapter.notifyDataSetChanged();
        return this;
    }
    
    public ListItem setEnabled(boolean enabled) {
        Enabled = enabled;
        Context.mAdapter.notifyDataSetChanged();
        return this;
    }
    
    public boolean getEnabled() {
        return Enabled;
    }
    
    public ListItem setTitle(int title) {
        if (title == 0)
            return setTitle(null);
        else
            return setTitle(Context.getString(title));
    }
    
    public String getTitle() {
        return Title;
    }
    
    public ListItem setTitle(String title) {
        Title = title;
        Context.mAdapter.notifyDataSetChanged();
        return this;
    }

    public ListItem setSummary(int summary) {
        if (summary == 0)
            return setSummary(null);
        else
            return setSummary(Context.getString(summary));
    }
    
    public ListItem setSummary(String summary) {
        Summary = summary;
        Context.mAdapter.notifyDataSetChanged();
        return this;
    }
    
    public ListItem(ActivityBaseFragment context, int title, int summary) {
        if (title != 0)
            Title = context.getString(title);
        if (summary != 0)
            Summary = context.getString(summary);
        Context = context;
    }
    
    public ListItem(ActivityBaseFragment context, String title, String summary) {
        Title = title;
        Summary = summary;
        Context = context;
    }
    
    public ListItem(ActivityBaseFragment context, int title, int summary, int icon) {
        this(context, title, summary);
        Icon = icon;
    }
    
    public ListItem(ActivityBaseFragment context, String title, String summary, int icon) {
        this(context, title, summary);
        Icon = icon;
    }
    
    Drawable mDrawable;
    public ListItem(ActivityBaseFragment context, String title, String summary, Drawable drawable) {
        this(context, title, summary);
        mDrawable = drawable;
    }
    
    private boolean CheckboxVisible = false;
    private boolean checked = false;
    
    public ListItem setCheckboxVisible(boolean visible) {
        CheckboxVisible = visible;
        Context.mAdapter.notifyDataSetChanged();
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
        Context.mAdapter.notifyDataSetChanged();
        return this;
    }
//    
//    boolean mUseOnOff = false;
//    public void useYesNo() {
//        mUseOnOff = true;
//    }
//    
//    private void setSwitch(View view) {
//        Switch s = (Switch)view;
//        s.setTextOn(Context.getString(R.string.yes).toUpperCase());
//        s.setTextOff(Context.getString(R.string.no).toUpperCase());
//        view.setTag(view);
//    }
    
    public View getView(Context context, View convertView) {
        if (convertView == null || convertView.getTag() != null)
            convertView = LayoutInflater.from(context).inflate(Context.getListItemResource(), null);
        
        
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
//        if (mUseOnOff && !(cb instanceof CheckBox)) {
//            setSwitch(cb);
//        }
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
