
import { NativeModules, Platform } from 'react-native';
const { RNShareStory, RNShareStoryModule } = NativeModules;

/* ---- CONSTANTS ---- */

const IG_SHARE_FAIL: string = 'GENERAL_ERROR'
const NO_INSTALL_ERROR: string = 'NOT_INSTALLED_ERROR'
const UNSUPPORTED_VERSION: string = 'UNSUPPORTED_VERSION'

/**
 * Method to share a video from a url to instagram.
 * For IOS, this will take a video link, and open
 * it directly in instagram story share.
 *
 * For Android, this will open a general file share menu, which,
 * if instagram is installed, will have an option to share to stories.
 *
 * @param videoUrl {String} The Video URL to share.
 * @return {Promise<void>} A promise with the action to open instagram.
 */
const shareVideoOnInstagram = (videoUrl: string): Promise<boolean> => {
  if (Platform.OS === 'ios') {
    return RNShareStory.shareRemoteUrlToInstagram(videoUrl);
  } else {
    return RNShareStoryModule.shareRemoteUrl(videoUrl);
  }
};



export default {
  IG_SHARE_FAIL,
  NO_INSTALL_ERROR,
  UNSUPPORTED_VERSION,
  shareVideoOnInstagram,
};
