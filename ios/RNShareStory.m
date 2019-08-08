
#import "RNShareStory.h"
#import <React/RCTLog.h>
#define SYSTEM_VERSION_LESS_THAN(v)                 ([[[UIDevice currentDevice] systemVersion] compare:v options:NSNumericSearch] == NSOrderedAscending)


@implementation RNShareStory

NSString *const INSTAGRAM_STORIES_SCHEME = @"instagram-stories://share";
NSString *const UNSUPPORTED_VERSION = @"UNSUPPORTED_VERSION";
NSString *const GENERAL_ERROR = @"GENERAL_ERROR";

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(shareRemoteUrlToInstagram:(nonnull NSString *)videoUrl
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{

  NSURL *urlScheme = [NSURL URLWithString:INSTAGRAM_STORIES_SCHEME];
  if (SYSTEM_VERSION_LESS_THAN(@"10.0")) {
    reject(@"ig_share_failure", UNSUPPORTED_VERSION, nil);
    return;
  }

  if (videoUrl == nil) {
    reject(@"ig_share_failure", GENERAL_ERROR, nil );
    return;
  }

  NSURL *url = [NSURL URLWithString:videoUrl];
  NSData *videoData = [NSData dataWithContentsOfURL:(NSURL *)url];

  /* Copy the video data into the clipboard */
  NSArray *pasteboardItems = @[@{@"com.instagram.sharedSticker.backgroundVideo" : videoData }];
  NSDictionary *pasteboardOptions = @{UIPasteboardOptionExpirationDate : [[NSDate date] dateByAddingTimeInterval:60 * 5]};
  // This call is iOS 10+, can use 'setItems' depending on what versions you support
  [[UIPasteboard generalPasteboard] setItems:pasteboardItems options:pasteboardOptions];

  [[UIApplication sharedApplication] openURL:urlScheme options:@{} completionHandler:^(BOOL success) {
    if (success == 1) {
      resolve(@YES);
    } else {
      resolve(@NO);
    }
  }];
}

@end

