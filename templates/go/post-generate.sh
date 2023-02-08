cp -R ../../templates/go/statics/.github ./
cp -R ../../templates/go/statics/* ./
mkdir test-assets
cp ../../templates/common-resources/test-assets/* test-assets
cp ../../templates/common-resources/CONTRIBUTING.md ./
cp ../../templates/common-resources/LICENSE ./
mv test-assets/10m.mp4.part.c test-assets/10m.mp4.part.d
gofmt -s -w *.go
