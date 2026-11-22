# Security and asset provenance

Use original work for source, UI, textures, sound, fonts, fixtures and level designs. Maintain a provenance record per third-party item with source URL, author, version/digest, license, modifications, approved use and required notice. Keep ownership and usage rights attached to generated or commissioned assets as well. Require review before admitting material with uncertain rights. Package notices with each release.

Use distributable synthetic fixtures in public CI. Keep private user worlds, screenshots, diagnostics and credentials out of Git and artifacts. Strip account IDs, absolute paths and secrets from reports at collection. Store any future credentials via established platform mechanisms and protect session state against unauthorized access. Disable telemetry by default.

Bound parser sizes, nesting, memory, queues, decompression and timeouts. Authenticate control endpoints and bind local harness traffic to loopback. Review external dependencies, native/unsafe boundaries and versioned update paths. Fuzz project-owned codecs, archives, resources and save parsers using generated data. Cover denial, cancellation, invalid input and cleanup.

Build untrusted branches only in ordinary GitHub-hosted CI, with read-only access and public fixtures. Keep release credentials and signing in approved protected environments. See [reporting](../SECURITY.md), [pack isolation](06-Modding-Packs.md) and [release](17-Release.md).
