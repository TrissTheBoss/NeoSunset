# Development and release

GitHub Actions compiles the mod with Java 25 and Gradle 9.6.0, runs the policy tests, checks the JAR metadata and uploads the production artifact. Separate software-rendered client jobs verify the applied hooks, the Video Settings control, persistence, and a finite-cap fallback on every loader listed for publication. Get the JAR from the build for the exact commit. The repository uses Minecraft's official 26.3 dependencies at build time and packages project classes, metadata, translations and the license.

Use the client jobs and policy tests to qualify an alpha. Record software-renderer limits in its release notes. Before a stable release, verify native presentation, device changes and representative third-party profiles using [presentation QA](PRESENTATION.md#manual-verification). Record loader versions, platform, driver, backend and artifact digest. Update the workflow's Modrinth loader list only after runtime verification.

Set `modVersion` in `gradle.properties`, then add `releases/v<version>.md` on `main` with the scope and test results. A push that adds that file triggers publication for the matching version. A version tag such as `v0.1.0-alpha.1` is also a release trigger. Use one trigger per version. GitHub verifies the version and publishes the JAR from that run on the alpha, beta, or stable channel according to the version suffix.

## Record a native-client check

1. Open the [Build and release workflow](https://github.com/TrissTheBoss/NeoSunset/actions/workflows/ci.yml), select the successful run for the candidate commit, and download `NeoSunset-jar` from **Artifacts**. Extract the mod JAR and record its SHA-256 (`Get-FileHash -Algorithm SHA256 <jar>` in PowerShell or `shasum -a 256 <jar>` where available). Hash the extracted JAR, not the artifact ZIP. Artifacts are retained for 14 days; if the artifact expired, run the workflow again for that commit and record the new run URL.
2. Start with a clean Minecraft 26.3 profile on Java 25 and install only the loader and candidate JAR. On a hardware-rendered client, compare the same scene with the option off and on using Unlimited FPS and VSync off. With the option still on, set a finite cap and check that normal pacing resumes. Use the [manual verification table](PRESENTATION.md#manual-verification) for additional cases.
3. Record one entry per loader/backend combination tested. Mark untested cases as **not run**. If you only verified that the client launched and the toggle worked, report that separately; the FPS counter alone does not verify changes to swap cadence or the Vulkan present mode.

```text
Candidate commit and Actions run URL:
JAR SHA-256:
Minecraft, Java, loader and versions:
OS/architecture, GPU, driver and display refresh rate:
Backend (OpenGL or Vulkan):
Case, expected behavior, observed behavior and result (pass/fail/not run):
Evidence (logs or capture) and limitations:
```

## Publication credentials

The publish job uses the repository secrets `modrinth_pat` (Modrinth PAT with Create versions permission) and `modrinth_id` (project ID). Only the publishing job receives them. Every published version declares the Modrinth environment as Client only. The [Modrinth Publish action](https://github.com/marketplace/actions/modrinth-publish) reports missing permissions or invalid project configuration in its job result. Before retrying a failed upload, check the project for an already-created version.
