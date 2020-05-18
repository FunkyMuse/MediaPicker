
Current jitpack version: [![](https://jitpack.io/v/CraZyLegenD/MediaPicker.svg)](https://jitpack.io/#CraZyLegenD/MediaPicker)

1. You must declare the permission in your manifest
```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```
2. If you haven't included the dependencies from the previous screen now's a good time
```gradle
dependencies {
    def pickerVersion = "0.0.1" //look-up the latest one on jitpack 
    
    //the core package is a must
    implementation "com.github.CraZyLegenD.MediaPicker:core:$pickerVersion"
    
    //videos
    implementation "com.github.CraZyLegenD.MediaPicker:videopicker:$pickerVersion"
  }
```
3. How to use single picker and check out [how to customize single video picker](https://github.com/CraZyLegenD/MediaPicker/wiki/Single--image-video-picker-customization)
```kotlin
    //simple usage without customization
    SingleVideoPicker.showPicker(context = this, onPickedVideo = ::loadVideo)
    
    //customized
    SingleVideoPicker.showPicker(this, {
            setupBaseModifier(
                    loadingIndicatorColor = R.color.minusColor,
                    titleTextModifications = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.ITALIC
                        textColor = Color.BLACK
                        marginBottom = 30 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 30f  // use sp this is only for demonstration purposes
                        textString = "Pick a video"
                    },
                    placeHolderModifications = {
                        resID = R.drawable.ic_image
                    }
            )
        }, ::loadVideo)
    
```

4. How to use multi picker and check out [how to customize multi video picker](https://github.com/CraZyLegenD/MediaPicker/wiki/Multi-image-video-picker-customization)
```kotlin
    //simple usage without customization
    MultiVideoPicker.showPicker(this) { loadVideos(it) }
    
    //customized
     MultiVideoPicker.showPicker(this, {
            setupBaseMultiPicker(
                    tintForLoadingProgressBar = ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark),
                    gravityForSelectAndUnSelectIndicators = BaseMultiPickerModifier.Gravity.TOP_RIGHT,
                    titleModifications = {
                        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                        textStyle = TitleTextModifier.TextStyle.ITALIC
                        textColor = Color.BLACK
                        marginBottom = 30 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 30f  // use sp this is only for demonstration purposes
                        textString = "Pick multiple videos"
                    },
                    selectIconModifications = {
                        resID = R.drawable.ic_checked
                        tint = Color.BLACK
                    },
                    unSelectIconModifications = {
                        resID = R.drawable.ic_unchecked
                        tint = Color.BLACK
                    },
                    viewHolderPlaceholderModifications = {
                        resID = R.drawable.ic_close
                    }
            )
        }, ::doSomethingWithVideoList)
    
```
##
If you're still not sure how to use, take a look at the [Sample app](https://github.com/CraZyLegenD/MediaPicker/blob/master/app/src/main/java/com/crazylegend/mediapicker/MainActivity.kt) 

## Screens

Single picker

<img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/videopicker/screens/screen_1.png" width="15%"></img>

Multi pickers

<img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/videopicker/screens/screen_3.png" width="15%"></img>
