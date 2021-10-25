cp -R ../../templates/go/statics/.github ./
cp -R ../../templates/go/statics/* ./
mkdir test-assets
cp ../../templates/common-resources/* test-assets
mv test-assets/10m.mp4.part.c test-assets/10m.mp4.part.d
gofmt -s -w *.go