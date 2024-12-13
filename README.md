# Auto Tuning Pid Controllers

### Reccommended Setup
Clone this repository and follow the tuning guide https://docs.google.com/document/d/1bymQrM0Fjh6iF8rk0LOnRxAvZ4Z7ZZ_rhb6DtUqgZno/edit?usp=sharing

### If you want to implement this into you own repository
1. Edit TeamCode build.gradle and add ```repositories{maven { url 'https://jitpack.io' }
 maven { url 'https://maven.brott.dev/' }}```
2. In the project build.gradle (name of repository - build.gradle) remove the ```repositories {
    mavenCentral()
}``` at the bottom of the file.
3. In the build.common.gradle change ```compileSdkVersion 29``` to ```compileSdkVersion 34```
## All set!
