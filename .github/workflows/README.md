# Workflows

## `update-client.yml`

This workflow currently push force to the `main` client branch.
Another strategy could be to create a PR in each client repository:
```
      - uses: actions/github-script@v3
        with:
          github-token: ${{ secrets.PAT }}
          script: |
            github.pulls.create({
              owner: '${{ env.OWNER }}',
              repo: '${{ env.REPO }}',
              head: '${{ env.BRANCH }}',
              base: '${{ env.TARGET }}',
              title: '${{ github.run_id }}'
            });
```
