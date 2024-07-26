cp -R ../../templates/csharp/statics/.github ./
cp -R ../../templates/csharp/statics/* ./
cp -R ../../templates/common-resources/test-assets/* tests/resources/assets/
cp ../../templates/common-resources/CONTRIBUTING.md ./
cp ../../templates/common-resources/LICENSE ./
mv src/ApiVideo ApiVideo
rm -Rf src
mv ApiVideo src
cp -R ../../templates/csharp/statics/src/Model/DeepObject.cs ./src/Model/
rm .travis.yml
rm build.bat
rm build.sh
rm git_push.sh
rm mono_nunit_test.sh
rm post-generate.sh
rm src/ApiVideo.nuspec
