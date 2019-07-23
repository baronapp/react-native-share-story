
import { NativeModules, Platform } from 'react-native';

const { RNShareStory, RNShareStoryModule } = NativeModules;


/**
 *
 * @param videoUrl {String} The Video URL to share.
 */
const shareVideoOnInstagram = (videoUrl) => {
  if (Platform.OS === 'ios') {
    RNShareStory.shareVideoOnInstagram(videoUrl);
  } else {
    RNShareStoryModule.shareVideoOnInstagram(videoUrl);
  }
};

export default {
  shareVideoOnInstagram,
};
