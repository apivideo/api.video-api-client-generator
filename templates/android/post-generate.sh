cp -R ../../templates/android/statics/.github ./
cp -R ../../templates/android/statics/* ./
cp ../../templates/common-resources/* src/test/resources/assets/
mvn package -D skipTests
