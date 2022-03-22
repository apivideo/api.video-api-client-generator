cp -R ../../templates/swift5/statics/client/.github ./
cp -R ../../templates/swift5/statics/client/* ./
mkdir -p ./Tests/TestResources/assets/
cp ../../templates/common-resources/test-assets/* ./Tests/TestResources/assets/
# Do not have a *.c extension on a file, it will not be added to a Bundle
for i in a b c ; do mv Tests/TestResources/assets/10m.mp4.part.$i Tests/TestResources/assets/10m.part.$i.mp4 ; done
cp ../../templates/common-resources/CONTRIBUTING.md ./
cp ../../templates/common-resources/LICENSE ./
cp ../../templates/common-resources/.github/ISSUE_TEMPLATE/feature_request.md .github/ISSUE_TEMPLATE/