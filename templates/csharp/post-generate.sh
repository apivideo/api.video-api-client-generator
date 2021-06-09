cp -R ../../templates/csharp/statics/.github ./
cp -R ../../templates/csharp/statics/* ./
mv src/ApiVideo ApiVideo
rm -Rf src
mv ApiVideo src
rm .travis.yml
rm build.bat
rm build.sh
rm git_push.sh
rm mono_nunit_test.sh
rm post-generate.sh
rm src/ApiVideo.nuspec
