
import { NativeModules, Platform } from 'react-native';
const { RNShareStory, RNShareStoryModule } = NativeModules;


/* ---- CONSTANTS ---- */

const IG_SHARE_FAIL: string = 'GENERAL_ERROR'
const NO_INSTALL_ERROR: string = 'NOT_INSTALLED_ERROR'
const UNSUPPORTED_VERSION: string = 'UNSUPPORTED_IOS_VERSION'

/**
 * Method to share a video from a url to instagram.
 * This method currently only works for ios. If called
 * on android it will return a rejected Promise.
 *
 * @param videoUrl {String} The Video URL to share.
 * @return {Promise<void>} A promise with the action to open instagram.
 */
const shareVideoOnInstagram = (videoUrl: string): Promise<void> => {
  if (Platform.OS === 'ios') {
    return RNShareStory.shareVideoOnInstagram(videoUrl);
  } else {
    return Promise.reject('Feature not currently implemented for Android.')
    // RNShareStoryModule.shareVideoOnInstagram(videoUrl);
  }
};

export default {
  IG_SHARE_FAIL,
  NO_INSTALL_ERROR,
  UNSUPPORTED_VERSION,
  shareVideoOnInstagram,
};
