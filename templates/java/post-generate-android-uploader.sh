cp -R ../../templates/java/statics/android-uploader/.github ./
cp -R ../../templates/java/statics/android-uploader/* ./
cp ../../templates/common-resources/test-assets/* src/androidTest/assets/
cp ../../templates/common-resources/CONTRIBUTING.md ./
cp ../../templates/common-resources/LICENSE ./
cp ../../templates/common-resources/.github/ISSUE_TEMPLATE/feature_request.md .github/ISSUE_TEMPLATE/
mvn package -D skipTests
