package com.notus.fit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.notus.fit.R;
import com.notus.fit.ui_elements.SpinnerItemUrl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/3/2015 1:14 PM
 */
public class ImageTextSpinnerAdapter extends BaseAdapter {

    ImageView spinnerIcon;
    TextView spinnerText;
    private Context context;
    private ArrayList<SpinnerItemUrl> objects;

    public ImageTextSpinnerAdapter(Context context, ArrayList<SpinnerItemUrl> objects) {
        this.objects = objects;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dashboard_spinner_item, null);
        this.spinnerText = (TextView) convertView.findViewById(R.id.spinner_text);
        this.spinnerIcon = (ImageView) convertView.findViewById(R.id.spinner_icon);
        this.spinnerText.setText(this.objects.get(position).getText());
        Picasso.with(this.context).load(this.objects.get(position).getUrl()).into(this.spinnerIcon);
        return convertView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        super.getDropDownView(position, convertView, parent);
        convertView = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.dashboard_spinner_item, null);
        this.spinnerText = (TextView) convertView.findViewById(R.id.spinner_text);
        this.spinnerIcon = (ImageView) convertView.findViewById(R.id.spinner_icon);
        this.spinnerText.setText(this.objects.get(position).getText());
        Picasso.with(this.context).load(this.objects.get(position).getUrl()).into(this.spinnerIcon);
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
