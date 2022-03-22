npm install
npm run prettier
cp ../../templates/common-resources/test-assets/* test/data
cp ../../templates/common-resources/CONTRIBUTING.md ./
cp ../../templates/common-resources/.github/ISSUE_TEMPLATE/feature_request.md .github/ISSUE_TEMPLATE/
rm post-generate.sh .openapi-generator-ignore