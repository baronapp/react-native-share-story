
Pod::Spec.new do |s|
  s.name         = "RNShareStory"
  s.version      = "1.0.0"
  s.summary      = "RNShareStory"
  s.description  = "Ability to share video to instagram stories"
  s.homepage     = "https://www.cameo.com"
  s.license      = "MIT"
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/baronapp/react-native-share-story.git", :branch => "master" }
  s.source_files  = "ios/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
end
