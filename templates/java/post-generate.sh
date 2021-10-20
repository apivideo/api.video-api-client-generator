cp -R ../../templates/java/statics/.github ./
cp -R ../../templates/java/statics/* ./
cp ../../templates/common-resources/* src/test/resources/assets/
mvn package -D skipTests
