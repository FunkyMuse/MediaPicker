


# MediaPicker
### Kotlin Android library to pick images, videos and audios 

[![](https://jitpack.io/v/CraZyLegenD/MediaPicker.svg)](https://jitpack.io/#CraZyLegenD/MediaPicker) [![Kotlin](https://img.shields.io/badge/Kotlin-1.3.72-blue.svg)](https://kotlinlang.org) [![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/guide/) 

Minimum Android API 21
Compiled Android API 29

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
    def pickerVersion = "0.0.1" //look-up the latest one on jitpack
    
    //the core package is a must then you include the one you need
    implementation "com.github.CraZyLegenD.MediaPicker:core:$pickerVersion"
    
    //images
    implementation "com.github.CraZyLegenD.MediaPicker:imagepicker:$pickerVersion"
    //audios
    implementation "com.github.CraZyLegenD.MediaPicker:audiopicker:$pickerVersion"
    //videos
    implementation "com.github.CraZyLegenD.MediaPicker:videopicker:$pickerVersion"
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

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[GPL3.0]([https://choosealicense.com/licenses/gpl-3.0/](https://choosealicense.com/licenses/gpl-3.0/))
