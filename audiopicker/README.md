Current jitpack version: [![](https://jitpack.io/v/FunkyMuse/MediaPicker.svg)](https://jitpack.io/#FunkyMuse/MediaPicker)

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
    
    //audios
    implementation "com.github.FunkyMuse.MediaPicker:audiopicker:$pickerVersion"
  }
```
3. How to use single picker and check out [how to customize single audio picker](https://github.com/FunkyMuse/MediaPicker/wiki/Single-audio-picker-customization)
```kotlin
    //simple usage without customization
    SingleAudioPicker.showPicker(this) {
            loadAudio(it)
        }
	
	
    //customized
    SingleAudioPicker.showPicker(this, {
            setupViewHolderTitleText {
                textColor = Color.BLACK
                textPadding = 10 // use dp or sp this is only for demonstration purposes
            }
            setupBaseModifier(
                    loadingIndicatorColor = R.color.minusColor,
                    titleTextModifications = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.ITALIC
                        textColor = Color.BLACK
                        marginBottom = 30 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 30f  // use sp this is only for demonstration purposes
                        textString = "Pick an audio"
                    },
                    placeHolderModifications = {
                        resID = R.drawable.ic_image
                    }
            )
        }, ::loadAudio)
    
```

4. How to use multi picker and check out [how to customize multi audio picker](https://github.com/FunkyMuse/MediaPicker/wiki/Multi-audio-picker-customization)
```kotlin
    //simple usage without customization
    MultiAudioPicker.showPicker(this) {
            loadAudios(it)
        }
	
	
    //customized
    MultiAudioPicker.showPicker(this, {
            setupViewHolderTitleText {
                textColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark)
                textStyle = TitleTextModifier.TextStyle.BOLD
                textPadding = 10 // use dp or sp this is only for demonstration purposes
            }
            setupBaseMultiPicker(
                    tintForLoadingProgressBar = ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark),
                    gravityForSelectAndUnSelectIndicators = BaseMultiPickerModifier.Gravity.BOTTOM_LEFT,
                    titleModifications = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.ITALIC
                        textColor = Color.BLACK
                        marginBottom = 30 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 30f  // use sp this is only for demonstration purposes
                        textString = "Pick multiple songs"
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
                        resID = R.drawable.ic_album_second
                    }
            )
        }, ::doSomethingWithAudioList)
```

If you're using Fragments to call the pickers you can leverage [set fragment result listener](https://developer.android.com/reference/androidx/fragment/app/FragmentManager#setfragmentresultlistener) to get back the result and you don't have to restore the listener nor provide a lambda for the listener, it can be as simple as
```kotlin
SingleAudioPicker.showPicker(requireContext())
```
```kotlin

setFragmentResultListener(SingleAudioPicker.SINGLE_AUDIO_REQUEST_KEY) { _, bundle ->
     val loadedModel = bundle.getParcelable<AudioModel>(SingleAudioPicker.ON_SINGLE_AUDIO_PICK_KEY)
            loadedModel?.let { loadAudio(it) }
        }
	
setFragmentResultListener(MultiAudioPicker.MULTI_AUDIO_REQUEST_KEY) { _, bundle ->
     val loadedModel = bundle.getParcelableArrayList<AudioModel>(MultiAudioPicker.ON_MULTI_AUDIO_PICK_KEY)
            loadedModel?.let { doSomethingWithAudioList(it) }
        }
```
##
If you're still not sure how to use, take a look at the [Sample app](https://github.com/FunkyMuse/MediaPicker/blob/master/app/src/main/java/com/crazylegend/mediapicker/MainActivity.kt) 

##
If you're still not sure how to use fragment listener, take a look at the [Sample app](https://github.com/FunkyMuse/MediaPicker/blob/master/app/src/main/java/com/crazylegend/mediapicker/FragmentResult.kt#L330) 

## Screens

Single picker

<img src="https://raw.githubusercontent.com/FunkyMuse/MediaPicker/master/audiopicker/screens/screen_1.png" width="15%"></img>

Multi picker

<img src="https://raw.githubusercontent.com/FunkyMuse/MediaPicker/master/audiopicker/screens/screen_3.png" width="15%"></img>
