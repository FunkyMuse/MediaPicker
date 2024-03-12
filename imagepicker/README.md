

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
    
    //images
    implementation "com.github.FunkyMuse.MediaPicker:imagepicker:$pickerVersion"
  }
```
3. How to use single picker and check out [how to customize single image picker](https://github.com/FunkyMuse/MediaPicker/wiki/Single--image-video-picker-customization)
```kotlin
    //simple usage without customizations
    SingleImagePicker.showPicker(context = this){
            doSomethingWithImage(it)
        }
        
   
   //customized
   SingleImagePicker.showPicker(this, extensions = arrayOf(),config = Config(showFileSize = true),{
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
   // You can filter files by passing extensions like extensions = arrayOf("png","jpeg")
```

4. How to use multi picker and check out [how to customize multi image picker](https://github.com/FunkyMuse/MediaPicker/wiki/Multi-image-video-picker-customization)
```kotlin
    //simple usage without customizations
    MultiImagePicker.showPicker(this){ doSomethingWithImageList(it) }
    
    //customized
    MultiImagePicker.showPicker(this, extensions = arrayOf(), config = Config(showFileSize = true),{
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

If you're using Fragments to call the pickers you can leverage [set fragment result listener](https://developer.android.com/reference/androidx/fragment/app/FragmentManager#setfragmentresultlistener) to get back the result and you don't have to restore the listener nor provide a lambda for the listener, it can be as simple as
```kotlin
SingleImagePicker.showPicker(requireContext())
```
```kotlin

setFragmentResultListener(SingleImagePicker.SINGLE_IMAGE_REQUEST_KEY) { _, bundle ->
    val loadedModel = bundle.getParcelable<ImageModel>(SingleImagePicker.ON_SINGLE_IMAGE_PICK_KEY)
            loadedModel?.let { loadImage(it) }
        }
        
setFragmentResultListener(MultiImagePicker.MULTI_IMAGE_REQUEST_KEY) { _, bundle ->
    val loadedModel = bundle.getParcelableArrayList<ImageModel>(MultiImagePicker.ON_MULTI_IMAGE_PICK_KEY)
            loadedModel?.let { doSomethingWithImageList(it) }
        }
```

##
If you're still not sure how to use, take a look at the [Sample app](https://github.com/FunkyMuse/MediaPicker/blob/master/app/src/main/java/com/crazylegend/mediapicker/MainActivity.kt) 

##
If you're still not sure how to use fragment listener, take a look at the [Sample app](https://github.com/FunkyMuse/MediaPicker/blob/master/app/src/main/java/com/crazylegend/mediapicker/FragmentResult.kt#L310)  

## Screens

Single picker

<img src="https://raw.githubusercontent.com/FunkyMuse/MediaPicker/master/imagepicker/screens/screen_1.png" width="15%"></img>

Multi picker

<img src="https://raw.githubusercontent.com/FunkyMuse/MediaPicker/master/imagepicker/screens/screen_3.png" width="15%"></img>
