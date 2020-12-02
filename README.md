



# MediaPicker
### Kotlin Android library to pick images, videos and audios 

[![](https://jitpack.io/v/FunkyMuse/MediaPicker.svg)](https://jitpack.io/#FunkyMuse/MediaPicker) [![Kotlin](https://img.shields.io/badge/Kotlin-1.4.20-blue.svg)](https://kotlinlang.org) [![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/guide/) 
![API](https://img.shields.io/badge/Min%20API-21-green)
![API](https://img.shields.io/badge/Compiled%20API-30-green)

## Usage
1. Add JitPack to your project build.gradle

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
   }
}
```

2. Add the dependency in the application build.gradle

```gradle
dependencies {
    def pickerVersion = "1.0.0" //look-up the latest one on jitpack
    
    //the core package is a must then you include the one you need
    implementation "com.github.FunkyMuse.MediaPicker:core:$pickerVersion"
    
    //images
    implementation "com.github.FunkyMuse.MediaPicker:imagepicker:$pickerVersion"
    //audios
    implementation "com.github.FunkyMuse.MediaPicker:audiopicker:$pickerVersion"
    //videos
    implementation "com.github.FunkyMuse.MediaPicker:videopicker:$pickerVersion"
  }
```

3. To not run into any issues in your application build.gradle add

```gradle
   compileOptions {
        sourceCompatibility = 1.8
        targetCompatibility = 1.8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    
    viewBinding {
        enabled = true
    }
```

4. Inside gradle.properties (optional)

```gradle
org.gradle.parallel=true
```

5. How to use and screens

[Image picker](https://github.com/FunkyMuse/MediaPicker/tree/master/imagepicker)

[Video picker](https://github.com/FunkyMuse/MediaPicker/tree/master/videopicker)

[Audio picker](https://github.com/FunkyMuse/MediaPicker/tree/master/audiopicker)

[Sample app](https://github.com/FunkyMuse/MediaPicker/blob/master/app/src/main/java/com/crazylegend/mediapicker/MainActivity.kt)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0)
