# GitHub Actions build contract

GitHub Actions is the compilation environment. The [CI workflow](../.github/workflows/ci.yml) uses a native runner for every [declared target](../README.md#build-and-download). The handbook lane checks local Markdown links and enforces the presence of the root workspace and tracked lockfile together. Each platform starts by checking the runner architecture. The handbook stage ends after that check. When Cargo metadata exists, each job validates its Rust host, formats, lints, tests, generates Rustdoc and builds the release profile against the committed lockfile. The stable **Required checks** job summarizes the handbook and all five platform results.

## First lockfile and dependency updates

Create or update the lockfile with `cargo generate-lockfile` or a targeted `cargo update -p <crate>`. Those commands resolve metadata without compiling. Run the normal verification through GitHub Actions and review the lockfile for unexpected transitive changes. If a host cannot resolve the lockfile, use a temporary GitHub-hosted maintainer job to generate and retrieve it as an artifact, then remove that job before merging. Commit the root `Cargo.toml` and `Cargo.lock` in the same change. The main package exposes `neosunset` and Windows emits `neosunset.exe`. Label a migration to different package names or build layouts in an ADR and update packaging in the same PR.

## Artifacts

A green build upload contains a target-named `.tar.gz` and its SHA-256 file. Download both files from the same run. On Linux check with `sha256sum -c <archive>.sha256`; on macOS use `shasum -a 256 -c <archive>.sha256`; on Windows use `Get-FileHash -Algorithm SHA256 <archive>` and compare to the checksum. Extract using `tar -xzf <archive>`. Artifacts expire after 14 days and are intended for verification. Promotion to a public release follows [release gates](17-Release.md).

## Failure triage

| Symptom | First check |
| --- | --- |
| Runner unavailable | Check runner status, label availability and repository Actions policy. Keep the lane visible. |
| Wrong Rust host | Check the runner's Rustup inventory and toolchain override. Confirm the native host after correction. |
| Missing lockfile or binary | Review tracked root metadata, package name and Cargo target directory. |
| Linker or dynamic library failure | Identify the OS dependency and record it in the platform support contract. |
| Rust test or lint failure | Find the earliest failing command and reproduce through a small PR with a focused case. |
| Hardware-dependent checks blocked | Record device, backend and missing evidence in the platform checklist. |

Use a branch ruleset to require **Required checks** once the initial run is green. Keep fork PR jobs on GitHub-hosted runners with read-only permissions and no private fixtures. Document verified image versions and review runner retirement announcements before changing the matrix. See [GitHub's runner reference](https://docs.github.com/en/actions/reference/runners/github-hosted-runners), [Rust target support](https://doc.rust-lang.org/rustc/platform-support.html) and the [pinned Rust release](https://blog.rust-lang.org/2026/11/12/Rust-1.100.0/).
