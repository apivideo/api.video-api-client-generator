cp -R ../../templates/java/statics/java/.github ./
cp -R ../../templates/java/statics/java/* ./
cp ../../templates/common-resources/test-assets/* src/test/resources/assets/
cp ../../templates/common-resources/CODE_OF_CONDUCT.md ./
cp ../../templates/common-resources/LICENSE ./
mvn package -D skipTests
