



# MediaPicker
### Kotlin Android library to pick images, videos and audios 

![](https://jitpack.io/v/FunkyMuse/MediaPicker.svg)

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

#### or

If your Android studio version is Arctic Fox and above then add it in your settings.gradle:

```gradle
dependencyResolutionManagement {
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
