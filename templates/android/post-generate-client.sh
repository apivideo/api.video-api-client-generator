cp -R ../../templates/android/statics/client/.github ./
cp -R ../../templates/android/statics/client/* ./
cp ../../templates/common-resources/* src/androidTest/assets/
mvn package -D skipTests
