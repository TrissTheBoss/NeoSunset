# Development and release

GitHub Actions compiles the mod with Java 25 and Gradle 9.6.0, runs the policy tests, checks the JAR metadata and uploads the production artifact. A separate Fabric software-rendered client run verifies the applied hooks, the Video Settings control, persistence, and a finite-cap fallback. Get the JAR from the build for the exact commit. The repository uses Minecraft's official 26.3 dependencies at build time and packages project classes, metadata, translations and the license.

Before tagging an alpha, exercise the artifact in a real 26.3 client using each advertised loader. Verify the default, option persistence, finite cap, VSync, competing mod and real presentation cases in [presentation QA](PRESENTATION.md#manual-verification). Record loader versions, platform, driver, backend and artifact digest. Update the workflow's Modrinth loader list only after runtime verification.

Set `modVersion` in `gradle.properties`, then add `releases/<version>.md` on `main` with the scope and test results. A push that adds that file triggers publication for the matching version. A version tag such as `v0.1.0-alpha.1` is also a release trigger. Use one trigger per version. GitHub verifies the version and publishes the JAR from that run on the alpha, beta, or stable channel according to the version suffix.

The publish job uses the repository secrets `modrinth_pat` (Modrinth PAT with Create versions permission) and `modrinth_id` (project ID). Only the publishing job receives them. The [Modrinth Publish action](https://github.com/marketplace/actions/modrinth-publish) reports missing permissions or invalid project configuration in its job result. Before retrying a failed upload, check the project for an already-created version.
