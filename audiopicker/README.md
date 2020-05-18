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
3. How to use single pickers
```kotlin
    //single picker bottom sheet
    SingleAudioPicker.bottomSheetPicker(this, {
            setupSingleAudioPicker(singlePicker = {
                setupTitleOnly(loadingIndicatorColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)) {
                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                    textStyle = TitleTextModifier.TextStyle.BOLD
                    textColor = Color.RED
                    margin = 12 // use dp or sp this is only for demonstration purposes
                    textPadding = 5 // use dp or sp this is only for demonstration purposes
                    textSize = 16f  // use sp this is only for demonstration purposes
                    textString = "Select a song"
                }
            }, viewHolderTitle = {
                textColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)
            }, viewHolderPlaceHolder = {
                resID = R.drawable.ic_album
            })
        }, ::loadAudio)

	//single picker dialog
	 SingleAudioPicker.dialogPicker(this, {
            setupSingleAudioPicker(singlePicker = {
                setupTitleAndCloseButton(title = {
                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                    textStyle = TitleTextModifier.TextStyle.BOLD
                    textColor = Color.RED
                    margin = 12 // use dp or sp this is only for demonstration purposes
                    textPadding = 5 // use dp or sp this is only for demonstration purposes
                    textSize = 16f  // use sp this is only for demonstration purposes
                    textString = "Pick a song"
                }, closeButton = {
                    endMargin = 20  // use dp or sp this is only for demonstration purposes
                    marginBottom = 20 // use dp or sp this is only for demonstration purposes
                    padding = 10 // use dp or sp this is only for demonstration purposes
                    resID = R.drawable.ic_close
                }, loadingIndicatorColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
            }, viewHolderTitle = {
                textColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)
            }, viewHolderPlaceHolder = {
                resID = R.drawable.ic_album
            })
        }) { audioModel ->
            loadAudio(audioModel)
        }
```

4. How to use multi pickers
```kotlin
    //multi picker bottom sheet
     MultiAudioPicker.bottomSheetPicker(this, {
            setupMultiAudioPicker(
                    multiPicker = {
                        setupMultiPicker(
                                loadingIndicatorColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark),
                                gravityForIndicators = MultiPickerModifier.Gravity.BOTTOM_LEFT,
                                titleText = {
                                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                                    textStyle = TitleTextModifier.TextStyle.ITALIC
                                    textColor = Color.BLACK
                                    marginBottom = 30 // use dp or sp this is only for demonstration purposes
                                    textPadding = 5 // use dp or sp this is only for demonstration purposes
                                    textSize = 30f  // use sp this is only for demonstration purposes
                                    textString = "Pick multiple songs"
                                },
                                selectIcon = {
                                    resID = R.drawable.ic_checked
                                    tint = Color.BLACK
                                },
                                unSelectIcon = {
                                    resID = R.drawable.ic_unchecked
                                    tint = Color.BLACK
                                }
                        )
                    },
                    viewHolderTitle = {
                        textColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark)
                        textStyle = TitleTextModifier.TextStyle.BOLD
                        textPadding = 10 // use dp or sp this is only for demonstration purposes
                    },
                    viewHolderPlaceHolder = {
                        resID = R.drawable.ic_album_second
                    }
            )
        }, ::doSomethingWithAudioList)
    
	//multi picker dialog
	MultiAudioPicker.dialogPicker(this, {
            setupMultiAudioPicker(
                    multiPicker = {
                        setupMultiPicker(
                                loadingIndicatorColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary),
                                gravityForIndicators = MultiPickerModifier.Gravity.TOP_RIGHT,
                                titleText = {
                                    textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
                                    textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                                    textColor = Color.BLUE
                                    marginBottom = 30 // use dp or sp this is only for demonstration purposes
                                    textPadding = 5 // use dp or sp this is only for demonstration purposes
                                    textSize = 30f  // use sp this is only for demonstration purposes
                                    textString = "Pick multiple songs"
                                },
                                selectIcon = {
                                    resID = R.drawable.ic_checked
                                    tint = Color.LTGRAY
                                },
                                unSelectIcon = {
                                    resID = R.drawable.ic_unchecked
                                    tint = Color.LTGRAY
                                }
                        )
                    },
                    viewHolderTitle = {
                        textColor = Color.DKGRAY
                        textStyle = TitleTextModifier.TextStyle.BOLD_ITALIC
                        textPadding = 10 // use dp or sp this is only for demonstration purposes
                    },
                    viewHolderPlaceHolder = {
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
