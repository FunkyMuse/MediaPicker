
Current jitpack version: [![](https://jitpack.io/v/FunkyMuse/MediaPicker.svg)](https://jitpack.io/#CraZyLegenD/MediaPicker)

1. You must declare the permission in your manifest
```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```
2. If you haven't included the dependencies from the previous screen now's a good time
```gradle
dependencies {
    def pickerVersion = "1.0.0" //look-up the latest one on jitpack 
    
    //the core package is a must
    implementation "com.github.FunkyMuse.MediaPicker:core:$pickerVersion"
    
    //videos
    implementation "com.github.FunkyMuse.MediaPicker:videopicker:$pickerVersion"
  }
```
3. How to use single picker and check out [how to customize single video picker](https://github.com/FunkyMuse/MediaPicker/wiki/Single--image-video-picker-customization)
```kotlin
    //simple usage without customization
    SingleVideoPicker.showPicker(context = this, onPickedVideo = ::loadVideo)
    
    //customized
    SingleVideoPicker.showPicker(this, extensions = arrayOf(),{
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

    // You can filter files by passing extensions like extensions = arrayOf("mp4","avi")
    
```

4. How to use multi picker and check out [how to customize multi video picker](https://github.com/FunkyMuse/MediaPicker/wiki/Multi-image-video-picker-customization)
```kotlin
    //simple usage without customization
    MultiVideoPicker.showPicker(this) { loadVideos(it) }
    
    //customized
     MultiVideoPicker.showPicker(this, extensions = arrayOf(),{
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

If you're using Fragments to call the pickers you can leverage [set fragment result listener](https://developer.android.com/reference/androidx/fragment/app/FragmentManager#setfragmentresultlistener) to get back the result and you don't have to restore the listener nor provide a lambda for the listener, it can be as simple as
```kotlin
SingleVideoPicker.showPicker(requireContext())
```
```kotlin

setFragmentResultListener(SingleVideoPicker.SINGLE_VIDEO_REQUEST_KEY) { _, bundle ->
    val loadedModel = bundle.getParcelable<VideoModel>(SingleVideoPicker.ON_SINGLE_VIDEO_PICK_KEY)
            loadedModel?.let { loadVideo(it) }
        }
        
setFragmentResultListener(MultiVideoPicker.MULTI_VIDEO_REQUEST_KEY) { _, bundle ->
    val loadedModel = bundle.getParcelableArrayList<VideoModel>(MultiVideoPicker.ON_MULTI_VIDEO_PICK_KEY)
            loadedModel?.let { doSomethingWithVideoList(it) }
        }
```

##
If you're still not sure how to use, take a look at the [Sample app](https://github.com/FunkyMuse/MediaPicker/blob/master/app/src/main/java/com/crazylegend/mediapicker/MainActivity.kt) 

##
If you're still not sure how to use fragment listener, take a look at the [Sample app](https://github.com/FunkyMuse/MediaPicker/blob/master/app/src/main/java/com/crazylegend/mediapicker/FragmentResult.kt#L320) 

## Screens

Single picker

<img src="https://raw.githubusercontent.com/FunkyMuse/MediaPicker/master/videopicker/screens/screen_1.png" width="15%"></img>

Multi pickers

<img src="https://raw.githubusercontent.com/FunkyMuse/MediaPicker/master/videopicker/screens/screen_3.png" width="15%"></img>
