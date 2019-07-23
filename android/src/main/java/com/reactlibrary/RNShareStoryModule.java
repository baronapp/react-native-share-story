
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import 	android.content.Intent;
import 	android.app.Activity;
import 	android.net.Uri;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class RNShareStoryModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNShareStoryModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNShareStoryModule";
  }

  public boolean isExternalStorageWritable() {
    String state = android.os.Environment.getExternalStorageState();
    if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
      return true;
    }
    return false;
  }

  public void downloadFile(String url, File outputFile) {
    try {
      URL u = new URL(url);
      URLConnection conn = u.openConnection();
      int contentLength = conn.getContentLength();

      DataInputStream stream = new DataInputStream(u.openStream());

      byte[] buffer = new byte[contentLength];
      stream.readFully(buffer);
      stream.close();

      DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
      fos.write(buffer);
      fos.flush();
      fos.close();
    } catch(FileNotFoundException e) {
      return; // swallow a 404
    } catch (IOException e) {
      return; // swallow a 404
    }
  }


  public File savefile(String url) {
    File sampleFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath() + File.separatorChar + "sampleFile.mp4");

    try {
      sampleFile.createNewFile();
    } catch (IOException err) {
      System.out.println(err);
    }

    System.out.println(sampleFile.getPath());
    System.out.println(sampleFile.canWrite());

    try {
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
    } catch(FileNotFoundException e) {
      return null; // swallow a 404
    } catch (IOException e) {
      return null; // swallow a 404
    }

    return sampleFile;

  }

  @ReactMethod
  public void shareVideoOnInstagram(String videoUrl) {

    System.out.println("---------------------------- HERE -------------------------------------");

    File fileToShare = savefile(videoUrl);

    System.out.println(fileToShare);

    Uri backgroundAssetUri = Uri.fromFile(fileToShare);

    System.out.println(backgroundAssetUri);

    Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
    intent.setDataAndType(backgroundAssetUri, "video/mp4");
    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

    Activity activity = reactContext.getCurrentActivity();
//    if (activity.getPackageManager().resolveActivity(intent, 0) != null) {
      activity.startActivityForResult(intent, 0);
//    } else {
//      System.out.println("Unable to do the thing");
//    }
  }
}