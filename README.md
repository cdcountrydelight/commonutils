
# CD Commonutils

## How to
### To get a Git project into your build:
#### Step 1. Add the JitPack repository to your build file
##### Add it in your root build.gradle at the end of repositories:

```
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```

#### Step 2. Add the dependency
```
dependencies {
        implementation 'com.github.cdcountrydelight:commonutils:1.0.0'
   	or
	implementation 'com.github.kpanjiyar:commonutils:1.0.0'
}
```

## License
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
