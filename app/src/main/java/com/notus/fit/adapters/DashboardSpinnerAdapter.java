package com.notus.fit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.notus.fit.R;
import com.notus.fit.ui_elements.SpinnerItem;

import java.util.ArrayList;

/**
 * Created by VBALAUD on 9/1/2015.
 */
public class DashboardSpinnerAdapter extends BaseAdapter {
    ImageView spinnerIcon;
    TextView spinnerText;
    private Context context;
    private ArrayList<SpinnerItem> objects;

    public DashboardSpinnerAdapter(Context context, ArrayList<SpinnerItem> objects) {
        this.objects = objects;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dashboard_spinner_item, null);
        this.spinnerText = (TextView) convertView.findViewById(R.id.spinner_text);
        this.spinnerIcon = (ImageView) convertView.findViewById(R.id.spinner_icon);
        convertView.setBackgroundColor(this.context.getResources().getColor(R.color.primary));
        this.spinnerText.setTextColor(this.context.getResources().getColor(R.color.grey_100));
        this.spinnerText.setText(((SpinnerItem) this.objects.get(position)).getText());
        this.spinnerIcon.setImageDrawable(this.context.getResources().getDrawable(((SpinnerItem) this.objects.get(position)).getDrawableResourceId()));
        return convertView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        super.getDropDownView(position, convertView, parent);
        convertView = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dashboard_spinner_item, null);
        this.spinnerText = (TextView) convertView.findViewById(R.id.spinner_text);
        this.spinnerIcon = (ImageView) convertView.findViewById(R.id.spinner_icon);
        this.spinnerText.setText(((SpinnerItem) this.objects.get(position)).getText());
        this.spinnerIcon.setImageDrawable(this.context.getResources().getDrawable(((SpinnerItem) this.objects.get(position)).getDrawableResourceId()));
        return convertView;
    }

    public int getCount() {
        return this.objects.size();
    }

    public Object getItem(int position) {
        return this.objects.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }
}
