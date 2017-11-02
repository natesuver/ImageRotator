package com.suver.nate.imagerotator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.content.Intent;
import java.util.Random;

public class PuzzleActivity extends AppCompatActivity {
    private GridView mGridview;
    private Button mJumbleButton;
    private float[] mRotations = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private int[] imageResourceIds = ImageResourceIds();
    private static final String LOG = "PuzzleActivity";
    private static final int DEFAULT_REQUEST_CODE = 0;
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if (resultCode!= Activity.RESULT_OK) {
            return;
        }
        if (requestCode==DEFAULT_REQUEST_CODE) {
            if (data==null) return;
            //obtain the index and rotation that the user specified in the image activity
            int selectedIndex = ImageActivity.getImageIndex(data);
            float selectedRotation = ImageActivity.getImageRotatation(data);
            mRotations[selectedIndex] = selectedRotation;
            setImageRotation(mGridview,selectedIndex);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null) {
            getRotationData(savedInstanceState);
        }
        setContentView(R.layout.activity_puzzle);

        mGridview = (GridView) findViewById(R.id.gridview);
        mGridview.setAdapter(new CellAdapter(this,ImageResourceIds(),mRotations));

        mGridview.setOnItemClickListener(new GridView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = ImageActivity.newIntent(PuzzleActivity.this,position,mRotations[position],imageResourceIds[position] );
                startActivityForResult(intent, DEFAULT_REQUEST_CODE);
            }
        });

        mJumbleButton = (Button) findViewById((R.id.jumble_button));
        mJumbleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffleRotation(mGridview);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(LOG,"onSaveInstanceState");
        storeRotationData(savedInstanceState);
    }

    private void storeRotationData(Bundle savedInstanceState) {
        for (int i=0;i<mRotations.length;i++) {
            savedInstanceState.putFloat("R" + i,mRotations[i]);
        }
    }
    private void getRotationData(Bundle savedInstanceState) {
        for (int i=0;i<mRotations.length;i++) {
            mRotations[i]=savedInstanceState.getFloat("R" + i);
        }
    }

    private void setImageRotation(GridView view,int index) {
        ImageView img = (ImageView)view.getChildAt(index);
        float rotation = mRotations[index];
        img.setRotation(rotation);
    }

    private void shuffleRotation(GridView view) {
        for(int i=0; i<view.getChildCount(); i++) {
            mRotations[i]=getRandomRotation();
            setImageRotation(view,i);
        }
    }

    public static final float getRandomRotation() {
        Random rn = new Random();
        int next = rn.nextInt(3) + 1;
        float degrees = 90;
        return degrees*next;
    }

    public static final int[] ImageResourceIds() {
        int[] cells = {
                R.drawable.r1c1, R.drawable.r1c2,
                R.drawable.r1c3, R.drawable.r1c4,
                R.drawable.r2c1, R.drawable.r2c2,
                R.drawable.r2c3, R.drawable.r2c4,
                R.drawable.r3c1, R.drawable.r3c2,
                R.drawable.r3c3, R.drawable.r3c4,
                R.drawable.r4c1, R.drawable.r4c2,
                R.drawable.r4c3, R.drawable.r4c4,

        };
        return cells;
    }
}
