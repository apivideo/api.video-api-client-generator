cp -R ../../templates/java/statics/.github ./
cp -R ../../templates/java/statics/* ./
cp ../../templates/common-resources/test-assets/* src/test/resources/assets/
cp ../../templates/common-resources/CoC.md ./
mvn package -D skipTests
