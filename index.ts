
import { NativeModules, Platform } from 'react-native';
const { RNShareStory, RNShareStoryModule } = NativeModules;


const IG_SHARE_FAIL: string = 'GENERAL_ERROR'
const NO_INSTALL_ERROR: string = 'NOT_INSTALLED_ERROR'
const UNSUPPORTED_VERSION: string = 'UNSUPPORTED_IOS_VERSION'


/**
 * Method to share a video from a url to instagram.
 * This method currently only works for ios. If called
 * on andriod it will return a rejected Promise.
 *
 * @param videoUrl {String} The Video URL to share.
 * @return {Promise<void>} A promise with the action to open instagram.
 */
const shareVideoOnInstagram = (videoUrl): Promise<void> => {
  if (Platform.OS === 'ios') {
    return RNShareStory.shareVideoOnInstagram(videoUrl);
  } else {
    Promise.reject('Feature not currrently implemented for Android.')
    // RNShareStoryModule.shareVideoOnInstagram(videoUrl);
  }
};

export default {
  IG_SHARE_FAIL,
  NO_INSTALL_ERROR,
  UNSUPPORTED_VERSION,
  shareVideoOnInstagram,
};
