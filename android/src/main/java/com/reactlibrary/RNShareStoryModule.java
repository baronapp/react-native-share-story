
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNShareStoryModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNShareStoryModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  /**
   * This is a required method for the react native bridge.
   * @return
   */
  @Override
  public String getName() {
    return "react-native-share-story";
  }

  @ReactMethod
  public void doSomething() {

  }

}