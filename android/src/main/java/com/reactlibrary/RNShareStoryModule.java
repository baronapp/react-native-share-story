
package com.reactlibrary;

import android.content.pm.PackageManager;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.content.Intent;
import android.app.Activity;
import android.net.Uri;
import android.os.Environment;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Instagram story sharing android module.
 * This class contains all methods necessary to share videos to instagram
 * from a react-native application.
 *
 * // WARNING: This class is not yet production ready.
 * Before deploying, all code should be reviewed, and tested appropriately.
 */
public class RNShareStoryModule extends ReactContextBaseJavaModule {

    private static final String INSTAGRAM_PACKAGE_NAME = "com.instagram.android";
    private static final String INSTAGRAM_STORIES_SHARE = "com.instagram.share.ADD_TO_STORY";
    private static final List FILE_TYPES_SUPPORTED = Arrays.asList("image/jpeg", "image/png", "video/mp4");


    private final ReactApplicationContext reactContext;

    /**
     * Default constructor
     *
     * @param reactContext The react content to be used for the instance of this class.
     */
    public RNShareStoryModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNShareStoryModule";
    }

    /**
     * Method to verify that we are able to write to external storage.
     *
     * @return True if we can write to external storage.
     */
    private boolean isExternalStorageWritable() {
        String state = android.os.Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * @return True if the instagram app is installed.
     */
    private boolean checkInstagramApp() {
        PackageManager pm = this.reactContext.getPackageManager();

        try {
            pm.getPackageInfo(INSTAGRAM_PACKAGE_NAME, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * @param url The url of a file to save locally to be able to share to instagram.
     * @return The file where the url data was saved to.
     */
    private File saveFile(String url) throws Exception {

        // TODO: Update this to be a new file name every time.
        File sampleFile = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath() +
                        File.separatorChar + "sampleFile.mp4");

        Boolean createdFile = sampleFile.createNewFile();

        if (createdFile) {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(sampleFile));
            fos.write(buffer);
            fos.flush();
            fos.close();
        } else {
            throw new IOException("Unable to create new file to write to");
        }
        return sampleFile;
    }


    @ReactMethod
    public void isAvailable(Promise promise) {
        promise.resolve(this.checkInstagramApp());
    }

    @ReactMethod
    public void shareVideoOnInstagram(String videoUrl, Promise promise) {
        File fileToShare;
        try {
            fileToShare = saveFile(videoUrl);
        } catch (Exception err) {
            promise.reject("Unable to create a file to save video to", err);
            return;
        }

        Uri backgroundAssetUri = Uri.fromFile(fileToShare);

        Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
        intent.setDataAndType(backgroundAssetUri, "video/mp4");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Activity activity = reactContext.getCurrentActivity();
        if (activity.getPackageManager().resolveActivity(intent, 0) != null) {
            activity.startActivityForResult(intent, 0);
        } else {
            promise.reject("NO-ACCESS", "Unable to find an appropriate activity event to open instagram");
        }
    }
}