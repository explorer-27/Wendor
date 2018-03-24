package com.example.ashu.wendor.Utility;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    static Target t;
    Context c;
    String myImageName;
    private String imageDir = "imageDir";


    public ImageLoader(Context context) {
        c = context;
    }

    public void downloadSaveImageFromUrl(String url, String imageName) {

        myImageName = imageName;

        ContextWrapper cw = new ContextWrapper(c);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE);


        t = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, myImageName);
                        Log.i("bitmap", "" + myImageFile.getAbsolutePath());

                        //Toast.makeText(context, "" + myImageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();// Create image file
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

        Picasso.with(c).load(url).into(t);
    }

    public String getFileLocation(String path) {
        return path;
    }


}
