

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
    
    //images
    implementation "com.github.CraZyLegenD.MediaPicker:imagepicker:$pickerVersion"
  }
```
3. How to use single pickers and check out [how to customize single image picker](https://github.com/CraZyLegenD/MediaPicker/wiki/Single-audio-video-picker-customization)
```kotlin
    //single picker
    //simple usage without customizations
    SingleImagePicker.showPicker(context = this){
            doSomethingWithImage(it)
        }
        
   
   //customized
   SingleImagePicker.showPicker(this, {
            loadingIndicatorTint = ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark)
            titleTextModifier.apply {
                textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                margin = 22 // use dp or sp this is only for demonstration purposes
                textColor = Color.BLACK
                textPadding = 5 // use dp or sp this is only for demonstration purposes
                textSize = 20f  // use sp this is only for demonstration purposes
                textString = "Select an image"
            }
            noContentTextModifier.apply {
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                margin = 22 // use dp or sp this is only for demonstration purposes
                textColor = Color.RED
                textPadding = 5 // use dp or sp this is only for demonstration purposes
                textSize = 20f  // use sp this is only for demonstration purposes
            }
            viewHolderPlaceholderModifier.apply {
                resID = R.drawable.ic_image
            }
        }, ::loadImage)
```

4. How to use multi pickers
```kotlin
    //multi picker
    //simple usage without customizations
    MultiImagePicker.showPicker(this){ doSomethingWithImageList(it) }
    
    //customized
    MultiImagePicker.showPicker(this, {
            setupBaseMultiPicker(
                    tintForLoadingProgressBar = ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark),
                    gravityForSelectAndUnSelectIndicators = BaseMultiPickerModifier.Gravity.TOP_LEFT,
                    titleModifications = {
                        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                        textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                        textColor = Color.BLACK
                        marginBottom = 30 // use dp or sp this is only for demonstration purposes
                        textPadding = 5 // use dp or sp this is only for demonstration purposes
                        textSize = 30f  // use sp this is only for demonstration purposes
                        textString = "Pick multiple images"
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
                        resID = R.drawable.ic_image
                    }
            )
        }) { list ->
            doSomethingWithImageList(list)
        }
```
##
If you're still not sure how to use, take a look at the [Sample app](https://github.com/CraZyLegenD/MediaPicker/blob/master/app/src/main/java/com/crazylegend/mediapicker/MainActivity.kt) 

## Screens

Single picker

<img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/imagepicker/screens/screen_1.png" width="15%"></img>

Multi picker

<img src="https://raw.githubusercontent.com/CraZyLegenD/MediaPicker/master/imagepicker/screens/screen_3.png" width="15%"></img>
