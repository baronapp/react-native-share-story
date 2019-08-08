# react-native-share-story
React Native Share Story, a simple tool for sharing videos to instagram. Check out the [Facebook Docs](https://developers.facebook.com/docs/instagram/sharing-to-stories) for reference.

# Getting started
---
```
npm i --save https://github.com/baronapp/react-native-share-story-story.git
```
Currently, the master branch off this git repo is the stable version of this package. It will soon moved to a version controlled NPM package.


## Automatic Install
``` 
react-native link react-native-share-story
```

## Manual install

### iOS Pods Install (Recommended)


[Cocopoads](https://cocoapods.org/) to use react-native-share-story.

You just need to add to your Podfile the react-native-share-story dependency.

```ruby
  # react-native-share-story pod
  pod 'RNShare', :path => '../node_modules/react-native-share-story'
```

After that, just run a `pod install` or `pod udpate` to get up and running with react-native-share-story. 

Then run a `react-native link react-native-share-story`.

Btw, We also recommend reading this (amazing article)[https://shift.infinite.red/beginner-s-guide-to-using-cocoapods-with-react-native-46cb4d372995] about how pods and rn work together. =D

### Android Install


2. Open up `android/app/src/main/java/[...]/MainApplication.java`
    - Add `import cl.json.RNSharePackage;` and `import cl.json.ShareApplication;` to the imports at the top of the file
    - Add `new RNSharePackage()` to the list returned by the `getPackages()` method

3. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-share-story'
  	project(':react-native-share-story').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-share-story/android')
  	```
4. Insert the following lines inside the dependencies block in
   `android/app/build.gradle`:

    ```
      compile project(':react-native-share-story')
    ```

# Methods
---

### shareVideoOnInstagram(remoteUrl)

Share a video to instagram stories.

Returns a promise that fulfills or rejects as soon as instagram opens or fails to open.


```jsx
import RNShareStory from 'react-native-share-story'
  Story.shareVideoOnInstagram('https://www.ExampleContentProvider.com/videoUrl.mp4')
    .then((res) => { console.log(res) })
    .catch((err) => { err && console.log(err); });
```

Supported options:

| Name  | Type     | Description |
| :---- | :------: | :--- |
| remoteUrl | string   | Remote http location of mp4 content (will support local file Url in near future). |
