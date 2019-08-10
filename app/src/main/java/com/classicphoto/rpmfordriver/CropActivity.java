package com.classicphoto.rpmfordriver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.classicphoto.rpmfordriver.R;
import com.edmodo.cropper.CropImageView;

import java.io.File;

public class CropActivity extends AppCompatActivity {

    CropImageView cropImageView;
    static Bitmap croppedImage;
    String pictuerPath;
    int i = 0;
    LinearLayout adContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Crop Photo");

        cropImageView = (CropImageView) findViewById(R.id.CropImageView);

        Intent getIntent = getIntent();
        pictuerPath = getIntent.getStringExtra("pictuerPath");

        File imagePath = new File(pictuerPath);

        if (imagePath.exists()) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath.getAbsolutePath());
            cropImageView.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_crop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;

            case R.id.Crop:

                croppedImage = cropImageView.getCroppedImage();

//                Intent returnIntent = new Intent();
//                returnIntent.putExtra("data", croppedImage);
//                setResult(Activity.RESULT_OK, returnIntent);
//                finish();

                onBackPressed();
//                Intent intent = new Intent(CropActivity.this, DeliveryActivity.class);
//                startActivity(intent);
//                finish();
//                overridePendingTransition(0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}
