# Workflows

The client PR workflow enable to see the expected diff on **Github** interface 
and gives a quick idea on how the markdown will be rendered.

Furthermore, tests are run in context of the branch it won't; 
it doesn't impact client repository till the changes reach the `main` branch.

> **Todo** _There might be way to notify the `generator` repository if some tests failed__


## Jobs

> see [update-client-pr.yml](./update-client-pr.yml)

### `get_available_profiles`

Retrieve the list of available `profiles` from `.github/actions/get-profiles/profiles.json`.
This list could be configured in any other way. Or even inferred from the root `pom.xml`.

### `update_client_pr`

When a PR is created on **generator** targeting the `main` branch,
it generates a **client** for each *profiles* referenced in the *profile* `matrix`.

Then for each client :
1. Checkout **client** repository.
2. Generate **client**.
3. Create equivalent `draft` *PR* on **client** repository.

### `merge_client_pr` and `close_client_pr`

When the **generator** PR is closed, depending on the reason,
it will either `close` or `merge` the equivalent **client** PR.