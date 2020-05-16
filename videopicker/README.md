
Current jitpack version: [![](https://jitpack.io/v/CraZyLegenD/MediaPicker.svg)](https://jitpack.io/#CraZyLegenD/MediaPicker)

1. You must declare the permission in your manifest
```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```
2. If you haven't included the dependencies from the previous screen now's a good time
```gradle
dependencies {
    def pickerVersion = "0.0.1" //look-up the latest one on jitpack 
    
    //the core package is a must then you include the one you need
    implementation "com.github.CraZyLegenD.MediaPicker:core:$pickerVersion"
    
    //videos
    implementation "com.github.CraZyLegenD.MediaPicker:videopicker:$pickerVersion"
  }
```
3. How to use single picker and check out [how to customize single video picker](https://github.com/CraZyLegenD/MediaPicker/wiki/Single-audio-video-picker-customization)
```kotlin
    
```

4. How to use multi pickerand check out [how to customize multi video picker](https://github.com/CraZyLegenD/MediaPicker/wiki/Single-audio-video-picker-customization)
```kotlin
    
```
##
If you're still not sure how to use, take a look at the [Sample app](https://github.com/CraZyLegenD/MediaPicker/blob/master/app/src/main/java/com/crazylegend/mediapicker/MainActivity.kt) 

## Screens

Single picker

<img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/videopicker/screens/screen_1.png" width="15%"></img>

Multi pickers

<img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/videopicker/screens/screen_3.png" width="15%"></img>
