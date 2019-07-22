
#import "RNShareStory.h"
#import <React/RCTLog.h>

@implementation RNShareStory

NSString *const INSTAGRAM_STORIES_SCHEME = @"instagram-stories://share";

- (BOOL)checkInstagramApp
{
    NSURL *urlScheme = [NSURL URLWithString:INSTAGRAM_STORIES_SCHEME];
    return [[UIApplication sharedApplication] canOpenURL:urlScheme];
}

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(shareVideoOnInstagram:(nonnull NSString *)videoUrl
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
    if (![self checkInstagramApp]) {
        reject(@"ig_share_failure", @"NOT_INSTALLED_ERROR", nil);
        return;
    }
    
    if (videoUrl == nil) {
        reject(@"ig_share_failure", @"GENERAL_ERROR", nil );
    }
    
//    NSURL *url = [NSURL URLWithString:@"https://starboard-media.s3.amazonaws.com/v/rv-T8ogXp195.mp4"];
    NSURL *url = [NSURL URLWithString:videoUrl];
    NSData *videoData = [NSData dataWithContentsOfURL:(NSURL *)url];
    
    // Assign background image asset and attribution link URL to pasteboard
    NSArray *pasteboardItems = @[@{@"com.instagram.sharedSticker.backgroundVideo" : videoData }];
    NSDictionary *pasteboardOptions = @{UIPasteboardOptionExpirationDate : [[NSDate date] dateByAddingTimeInterval:60 * 5]};
    // This call is iOS 10+, can use 'setItems' depending on what versions you support
    [[UIPasteboard generalPasteboard] setItems:pasteboardItems options:pasteboardOptions];
    
    [[UIApplication sharedApplication] openURL:urlScheme options:@{} completionHandler:nil];
    
    resolve(@YES);
}

@end

