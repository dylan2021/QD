package me.iwf.photopicker.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by myc on 2016/12/14.
 * More Code on 1101255053@qq.com
 * Description:
 */
public class FileUtils {
    public static boolean fileIsExists(String path) {
        if (path == null || path.trim().length() <= 0) {
            return false;
        }
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

//    private Uri getUri(Context mContext) {
//        File file = createImageFile();
//        Uri photoFile;
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            String authority = mContext.getApplicationInfo().packageName + ".provider";
//            photoFile = FileProvider.getUriForFile(mContext.getApplicationContext(), authority, file);
//        } else {
//            photoFile = Uri.fromFile(file);
//        }
//        return photoFile;
//    }

    public  static File saveBitmapFile(Bitmap bitmap) {
        File file = createImageFile();
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File createImageFile() {
        // Create an image file name
        String timeStamp;
        String imageFileName = null;
        File storageDir = null;
        try {
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
            imageFileName = "JPEG_" + timeStamp + ".jpg";
            storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

            if (!storageDir.exists()) {
                if (!storageDir.mkdir()) {
                    Log.e("TAG", "Throwing Errors....");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        File image = new File(storageDir, imageFileName);
        return image;
    }
}
