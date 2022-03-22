cp -R ../../templates/go/statics/.github ./
cp -R ../../templates/go/statics/* ./
mkdir test-assets
cp ../../templates/common-resources/test-assets/* test-assets
cp ../../templates/common-resources/CONTRIBUTING.md ./
cp ../../templates/common-resources/LICENSE ./
cp ../../templates/common-resources/.github/ISSUE_TEMPLATE/feature_request.md .github/ISSUE_TEMPLATE/
mv test-assets/10m.mp4.part.c test-assets/10m.mp4.part.d
gofmt -s -w *.go
