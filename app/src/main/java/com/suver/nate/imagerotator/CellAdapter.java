package com.suver.nate.imagerotator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by nates on 11/1/2017.  Derived from the tutorial from https://developer.android.com/guide/topics/ui/layout/gridview.html
 */

public class CellAdapter extends BaseAdapter {
    private Context mContext;
    private int[] mCellIds;
    private float[] mRotations;
    public CellAdapter(Context c, int[] imageResourceIds, float[] rotations) {
        mContext = c;
        mCellIds = imageResourceIds;
        mRotations = rotations;
    }

    public int getCount() {
        return mCellIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mCellIds[position]);
        imageView.setRotation(mRotations[position]);
        return imageView;
    }



}