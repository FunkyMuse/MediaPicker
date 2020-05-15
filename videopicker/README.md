
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
3. How to use single pickers
```kotlin
    //single picker bottom sheet
    SingleVideoPicker.bottomSheetPicker(this, {
            setupTitleAndCloseButton(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary), {
                textString = "Picking some of them videos"
            }, {
                resID = R.drawable.ic_close
            })
        }, ::loadVideo)

    //single picker dialog
    SingleVideoPicker.dialogPicker(this, {
            setupTitleAndCloseButton(
                    ContextCompat.getColor(this@MainActivity, R.color.design_default_color_error),
                    {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.BOLD
                        textColor = Color.RED
                        margin = 12 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 16f  // use sp this is only for demonstration purposes
                        textString = "Pick some videos"
                    }, {
                endMargin = 20  // use dp or sp this is only for demonstration purposes
                marginBottom = 20 // use dp or sp this is only for demonstration purposes
                padding = 10 // use dp or sp this is only for demonstration purposes
                resID = R.drawable.ic_close
            })
        }, ::loadVideo)
```

4. How to use multi pickers
```kotlin
    //multi picker bottom sheet
     MultiVideoPicker.bottomSheetPicker(this, {
            setupMultiPicker(
                    gravityForIndicators = MultiPickerModifier.Gravity.BOTTOM_LEFT,
                    titleText = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.ITALIC
                        textColor = Color.BLACK
                        marginBottom = 30 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 30f  // use sp this is only for demonstration purposes
                        textString = "Pick multi videos"
                    },
                    selectIcon = {
                        tint = Color.BLACK
                    },
                    unSelectIcon = {
                        tint = Color.BLACK
                    }
            )
        }, ::doSomethingWithVideoList)
    
    //multi picker dialog
     MultiVideoPicker.dialogPicker(this, {
            setupMultiPicker(
                    gravityForIndicators = MultiPickerModifier.Gravity.TOP_LEFT,
                    titleText = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.ITALIC
                        textColor = Color.RED
                        margin = 15 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 16f  // use sp this is only for demonstration purposes
                        textString = "Pick some multi videos"
                    },
                    selectIcon = {
                        tint = Color.YELLOW
                        marginBottom = 5
                        endMargin = 5
                    },
                    unSelectIcon = {
                        tint = Color.YELLOW
                        marginBottom = 5
                        endMargin = 5
                    }
            )
        }, ::doSomethingWithVideoList)
```
##
If you're still not sure how to use, take a look at the [Sample app](https://github.com/CraZyLegenD/MediaPicker/blob/master/app/src/main/java/com/crazylegend/mediapicker/MainActivity.kt) 

## Screens

Single pickers

<img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/videopicker/screens/screen_1.png" width="15%"></img> <img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/videopicker/screens/screen_2.png" width="15%"></img> 

Multi pickers

<img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/videopicker/screens/screen_3.png" width="15%"></img> <img
src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/videopicker/screens/screen_4.png" width="15%"></img>

