cp -R ../../templates/android/statics/uploader/.github ./
cp -R ../../templates/android/statics/uploader/* ./
cp ../../templates/common-resources/* src/androidTest/assets/
mvn package -D skipTests
