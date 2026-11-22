# Release checklist

1. Select a reviewed release commit and version. Confirm the five native jobs and documented runtime smoke checks. Read the evidence on the release commit itself.
2. Verify supported OS versions, GPU backends, display/audio/input, clean install and first run. State known limitations per platform.
3. Rehearse save upgrades, interrupted writes and backup recovery. Record downgrade boundaries and rollback behavior.
4. Review dependency advisories and license obligations. Audit asset provenance and distribution rights. Include a license inventory and SBOM.
5. Package and sign where required in GitHub Actions using protected environments approved for publication. Verify SHAs and signatures and record the exact source and toolchain. Keep the draft offline from public distribution until the maintainer approves.
6. Smoke-test downloaded packages on fresh profiles. Publish release notes, manual, accessibility information and version-specific support and recovery guidance. Never promote build artifacts with missing evidence.
7. Record exact release URLs and digests, preserve the previous known-good release and verify the rollback instructions with a fresh profile.

Use [GitHub Actions](18-GitHub-Actions.md) for all compilation. Each release milestone defines the installation and signing automation needed for the declared OS targets. The initial build workflow produces CI artifacts.
