package com.example.ashu.wendor.Images;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ashu on 20/3/18.
 */

public class ImageLoader extends AppCompatActivity {
    Context c;
    private String imageDir = "imageDir";

    public ImageLoader(Context context) {
        c = context;
    }

    public void downloadSaveImageFromUrl(String url, String imageName) {
        Picasso.with(c).load(url).into(picassoImageTarget(c, imageDir, imageName));
    }


    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, imageName); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getFileLocation("" + myImageFile.getAbsolutePath());
                            }
                        });

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Toast.makeText(c, "BitmapFailed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };
    }

    public String getFileLocation(String path) {
        return path;
    }


}
