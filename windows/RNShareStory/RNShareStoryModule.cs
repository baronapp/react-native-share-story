using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Share.Story.RNShareStory
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNShareStoryModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNShareStoryModule"/>.
        /// </summary>
        internal RNShareStoryModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNShareStory";
            }
        }
    }
}
