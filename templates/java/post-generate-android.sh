cp -R ../../templates/java/statics/android/.github ./
cp -R ../../templates/java/statics/android/* ./
cp -R ../../templates/java/statics/maven-push.gradle ./
cp ../../templates/common-resources/test-assets/* src/androidTest/assets/
cp ../../templates/common-resources/CONTRIBUTING.md ./
cp ../../templates/common-resources/LICENSE ./
mvn package -D skipTests
