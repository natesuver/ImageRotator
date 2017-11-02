package com.suver.nate.imagerotator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {
    private static final String IMAGE_INDEX = "com.suver.nate.imagerotator.imageindex";
    private static final String IMAGE_RESOURCE = "com.suver.nate.imagerotator.imageresourceid";
    private static final String IMAGE_ROTATION = "com.suver.nate.imagerotator.imagerotation";
    private float mSelectedRotation;
    private int mImageIndex;
    private int mImageResourceId;
    private ImageView mImageView;
    private ImageButton mRotateLeft;
    private ImageButton mRotateRight;
    private Button mReturn;
    public static Intent newIntent(Context packageContext, int imageIndex, float imageRotation, int imageResourceId) {
        Intent intent = new Intent(packageContext,ImageActivity.class);
        intent.putExtra(IMAGE_INDEX, imageIndex);
        intent.putExtra(IMAGE_RESOURCE, imageResourceId);
        intent.putExtra(IMAGE_ROTATION, imageRotation);
        return intent;
    }
    public static int getImageIndex(Intent result) {
        return result.getIntExtra(IMAGE_INDEX,-1);
    }
    public static float getImageRotatation(Intent result) {
        return result.getFloatExtra(IMAGE_ROTATION,0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mSelectedRotation = getIntent().getFloatExtra(IMAGE_ROTATION,-1);
        mImageIndex = getIntent().getIntExtra(IMAGE_INDEX,-1);
        mImageResourceId = getIntent().getIntExtra(IMAGE_RESOURCE,-1);

        mImageView = (ImageView) findViewById(R.id.imgView);
        mImageView.setImageResource(mImageResourceId);
        mImageView.setRotation(mSelectedRotation);

        mRotateLeft = (ImageButton) findViewById((R.id.rotate_left_button));
        mRotateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedRotation-=90;
                if (mSelectedRotation<0) mSelectedRotation = 270;
                rotateImage();
            }
        });
        mRotateRight = (ImageButton) findViewById((R.id.rotate_right_button));
        mRotateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedRotation+=90;
                if (mSelectedRotation>360) mSelectedRotation = 90;
                rotateImage();
            }
        });
        mReturn = (Button) findViewById((R.id.image_ok_button));
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //
    }

    private void setOutgoingRotation() {
        Intent data = new Intent();
        data.putExtra(IMAGE_INDEX,mImageIndex);
        data.putExtra(IMAGE_ROTATION,mSelectedRotation);
        setResult(RESULT_OK,data);
    }

    private void rotateImage() {
        mImageView.setRotation(mSelectedRotation);
    }

    @Override
    public void onBackPressed() {
        setOutgoingRotation();
        super.onBackPressed();
    }
}
