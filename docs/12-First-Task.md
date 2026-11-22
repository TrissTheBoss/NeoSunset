# First assignment for GPT-6 Luna

**M0.1 · Native build contract.** Start after the owner asks for application implementation. Use the normal AI workflow and keep this PR dependency-free.

1. Read the release and Actions contracts. Inspect the existing handbook run and verify all five runner labels. Record the expected toolchain and target triples.
2. Add a root Cargo workspace and the smallest real binary package exposing the `neosunset` executable. Use the existing toolchain pin, Cargo's 2024 edition and the MIT metadata. Commit the generated lockfile. Keep all compiler, lint and test execution on GitHub Actions. See the [lockfile bootstrap](18-GitHub-Actions.md#first-lockfile-and-dependency-updates).
3. Implement a small diagnostics CLI with documented `--help`, `--version`, and a machine-readable `doctor` output containing build target and product version. Define output schema, exit behavior for invalid arguments and a test that exercises the built executable. Use no graphics, private files or external service.
4. Capture successful native Actions runs for the exact commit. Run the packaged program in each job and verify help, version and diagnostics. Inspect the five archive manifests and check each digest.
5. Update the README's current status, progress, coverage and handoff with the actual evidence. Open M0.2 for owner acceptance of target OS floors and GPU test hardware.

Exit when the five native lanes and required gate pass, artifact contents are verified, and the README accurately describes what the diagnosis program can do.
