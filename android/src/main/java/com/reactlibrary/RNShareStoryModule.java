
package com.reactlibrary;

import android.content.pm.PackageManager;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.content.Intent;
import android.app.Activity;
import android.net.Uri;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

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

    private static final String NO_INSTALL_ERROR = "NOT_INSTALLED_ERROR";
    private static final String UNSUPPORTED_VERSION = "UNSUPPORTED_VERSION";
    private static final String GENERAL_ERROR = "GENERAL_ERROR";



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
     * Method will take a string of a url and determine the correct MIME file type.
     *
     * @param url The url to determine the MIME file type of.
     * @return A string description of the MIME type.
     */
    private String getFileMimeType(String url) {
        String type = null;

        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }

        return type;
    }

    /**
     * This method will get an appropriate content URI for a given file
     * from the current App's {@link FileProvider}.
     *
     * @param file The file to create the Uri for.
     * @return The content Uri for sharing.
     */
    private Uri getUriFromFile(File file) {
        final String packageName = getReactApplicationContext().getPackageName();

        return FileProvider.getUriForFile(getReactApplicationContext(),
                packageName + ".provider", file);
    }

    /**
     * This method will save a video file to the private file directory of the current app.
     *
     * @param url The url of a file to save locally to be able to share to instagram.
     * @return The file where the url data was saved to.
     */
    private File saveVideoFile(String url) throws Exception {

        url = url.replace("\\", "/");
        int index = url.lastIndexOf("/");

        if (index == -1) {
            throw new Exception("Cannot find appropriate file type for: " + url);
        }

        File fileDir = reactContext.getFilesDir();
        File file = new File(fileDir, url.substring(index, url.length()));

        if (file.exists() || file.createNewFile()) {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(file));
            fos.write(buffer);
            fos.flush();
            fos.close();
        } else {
            throw new IOException("Unable to create new file to write to");
        }
        return file;
    }

    /**
     * Will take a file on local storage, and share it.
     *
     * @param fileToShare The file to share.
     * @param promise A promise to resolve or reject based on the results of the share.
     */
    private void shareFile(File fileToShare, Promise promise) {
        try {
            Uri backgroundAssetUri = getUriFromFile(fileToShare);
            String type = this.getFileMimeType(backgroundAssetUri.getPath());

            // Create the new Intent using the 'Send' action.
            Intent share = new Intent(Intent.ACTION_SEND);

            // Set the MIME type
            share.setType(type);

            // Create the URI from the media
            Uri uri = getUriFromFile(fileToShare);

            // Add the URI to the Intent.
            share.putExtra(Intent.EXTRA_STREAM, uri);

            // Broadcast the Intent.
            reactContext.getCurrentActivity().startActivity(Intent.createChooser(share, "Share to"));
            promise.resolve(true);
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject("Unable to create a file to save video to", e);
        }
    }


    /* ------ BRIDGED METHODS ------ */

    /**
     * Will determine if it is possible to open the instagram app.
     * @param promise
     */
    @ReactMethod
    public void isAvailable(Promise promise) {
        promise.resolve(this.checkInstagramApp());
    }

    /**
     * Method will share content from a remote Url.
     *
     * @param contentUrl The remote url of the content to share. Supports images (png) and video (mp4).
     * @param promise React Native promise argument.
     */
    @ReactMethod
    public void shareRemoteUrl(String contentUrl, Promise promise) {
        File fileToShare;
        try {
            if (!checkInstagramApp()) {
                promise.reject("ig_share_failure", NO_INSTALL_ERROR);
                return;
            }
            fileToShare = saveVideoFile(videoUrl);
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject("Unable to create a file to save video to", e);
            return;
        }
        shareFile(fileToShare, promise);
    }
}