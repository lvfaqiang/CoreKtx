## 项目简介

### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

### Step 2. Add the dependency
```
	dependencies {
	        implementation 'com.github.lvfaqiang:CoreKtx:Tag'
	}
```
Tag: [![](https://jitpack.io/v/lvfaqiang/CoreKtx.svg)](https://jitpack.io/#lvfaqiang/CoreKtx)

Step 3. Add the following configuration

Add it in your app build.gradle at the android block

```
android {
    ...

    packagingOptions {
        exclude 'META-INF/core-ktx_release.kotlin_module'
    }
}
```
### How To Use :
setup in your Application :
```
CoreKtxProvider.get()
    .setXXXX
    ...
    .build(context)

```