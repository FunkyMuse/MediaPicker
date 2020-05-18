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
    
    //audios
    implementation "com.github.CraZyLegenD.MediaPicker:audiopicker:$pickerVersion"
  }
```
3. How to use single picker
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

4. How to use multi picker
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
##
If you're still not sure how to use, take a look at the [Sample app](https://github.com/CraZyLegenD/MediaPicker/blob/master/app/src/main/java/com/crazylegend/mediapicker/MainActivity.kt) 

## Screens

Single picker

<img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/audiopicker/screens/screen_1.png" width="15%"></img>

Multi picker

<img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/audiopicker/screens/screen_3.png" width="15%"></img>
