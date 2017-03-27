package com.mysafe;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;

    static final int REQUEST_TAKE_PHOTO = 1;

    String mCurrentPhotoPath;

    ArrayList<String> imagePaths = new ArrayList<>();

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView = (GridView) findViewById(R.id.gridView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        getDataFromDb();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.image_preview);

                ImageView image = (ImageView) dialog.findViewById(R.id.imagePreview);

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inPurgeable = true;

                Bitmap bitmap = BitmapFactory.decodeFile(imagePaths.get(position), bmOptions);
                image.setImageBitmap(bitmap);

                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        getDataFromDb();
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;

            try{
                photoFile = createImageFile();
            } catch (IOException ex){
                // throw your error here
            }

            if(photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(this, "com.mysafe.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException{

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );


        mCurrentPhotoPath = image.getAbsolutePath();
        saveImageToDb(mCurrentPhotoPath);
        return image;
    }

    private void getDataFromDb(){


        ImageDbHelper mDbHelper = new ImageDbHelper(this);


        SQLiteDatabase db = mDbHelper.getReadableDatabase();



        Cursor cursor = db.rawQuery("SELECT * FROM " + ImageContract.ImageEntry.TABLE_NAME, null);

        try{
            int imageColumn = cursor.getColumnIndex(ImageContract.ImageEntry.COLUMN_IMAGE_PATH);

            cursor.moveToLast();

            imagePaths.clear();

            if(cursor != null && cursor.getCount() > 0){
                do{
                    String mImagePath = cursor.getString(imageColumn);
                    imagePaths.add(mImagePath);
                } while (cursor.moveToPrevious());
            }
        } finally {
            cursor.close();
        }

        gridView.setAdapter(new ImageAdapter(getApplicationContext(), imagePaths));
    }

    private void saveImageToDb(String photoPath){
        ImageDbHelper mDbHelper = new ImageDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ImageContract.ImageEntry.COLUMN_IMAGE_PATH, photoPath);

        long newRowId = db.insert(ImageContract.ImageEntry.TABLE_NAME, null, values);
    }

}
