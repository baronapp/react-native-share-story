
import { NativeModules } from 'react-native';

const { RNShareStory } = NativeModules;

export default {
  printMethod: () => {
    console.log('Hello World')
  }
};
