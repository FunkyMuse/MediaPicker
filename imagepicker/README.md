

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
    
    //images
    implementation "com.github.CraZyLegenD.MediaPicker:imagepicker:$pickerVersion"
  }
```
3. How to use single pickers and check out [how to customize single image picker](https://github.com/CraZyLegenD/MediaPicker/wiki/Single-audio-video-picker-customization)
```kotlin
    //single picker bottom sheet
   SingleImagePicker.bottomSheetPicker(context = this, onPickedImage = ::loadImage)
   
   //single picker dialog
   SingleImagePicker.dialogPicker(this, {
            setupTitleAndCloseButton(ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark), {
                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                textStyle = TitleTextModifier.TextStyle.BOLD
                textColor = Color.RED
                margin = 12 // use dp or sp this is only for demonstration purposes
                textPadding = 5 // use dp or sp this is only for demonstration purposes
                textSize = 16f  // use sp this is only for demonstration purposes
                textString = "Pick some images"
            }, {
                endMargin = 20  // use dp or sp this is only for demonstration purposes
                marginBottom = 20 // use dp or sp this is only for demonstration purposes
                padding = 10 // use dp or sp this is only for demonstration purposes
                resID = R.drawable.ic_close
            })
        }, ::loadImage)
```

4. How to use multi pickers
```kotlin
    //multi picker bottom sheet
     MultiImagePicker.bottomSheetPicker(this, {
            setupMultiPicker(
                    gravityForIndicators = MultiPickerModifier.Gravity.BOTTOM_LEFT,
                    titleText = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                        textColor = Color.RED
                        margin = 12 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 16f  // use sp this is only for demonstration purposes
                        textString = "Pick some multi images"
                    },
                    doneButton = {
                        cornerRadius = 20  // use dp/sp/px this is only for demonstration purposes
                        tint = ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark)
                    },
                    selectIcon = {
                        resID = R.drawable.ic_checked
                    },
                    unSelectIcon = {
                        resID = R.drawable.ic_unchecked
                    })
        }) { list ->
            doSomethingWithImageList(list)
        }
    
    //multi picker dialog
     MultiImagePicker.dialogPicker(this, {
            setupMultiPicker(
                    gravityForIndicators = MultiPickerModifier.Gravity.TOP_RIGHT,
                    titleText = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                        textColor = Color.RED
                        margin = 12 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 16f  // use sp this is only for demonstration purposes
                        textString = "Pick some multi images"
                    },
                    doneButton = {
                        cornerRadius = 20  // use dp/sp/px this is only for demonstration purposes
                    },
                    selectIcon = {
                        margin = 15 // use dp or sp this is only for demonstration purposes
                        tint = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)
                    },
                    unSelectIcon = {
                        margin = 15 // use dp or sp this is only for demonstration purposes
                        tint = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)
                    })
        }, ::doSomethingWithImageList)
```
##
If you're still not sure how to use, take a look at the [Sample app](https://github.com/CraZyLegenD/MediaPicker/blob/master/app/src/main/java/com/crazylegend/mediapicker/MainActivity.kt) 

## Screens

Single pickers

<img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/imagepicker/screens/screen_1.png" width="15%"></img> <img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/imagepicker/screens/screen_2.png" width="15%"></img> 

Multi pickers

<img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/imagepicker/screens/screen_3.png" width="15%"></img> <img
src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/imagepicker/screens/screen_4.png" width="15%"></img>

