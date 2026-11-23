# Development and release

GitHub Actions compiles the mod with Java 25 and Gradle 9.6.0, runs the policy tests, checks the JAR metadata and uploads the production artifact. Get the JAR from the build for the exact commit. The repository uses Minecraft's official 26.3 dependencies at build time and packages project classes, metadata, translations and the license.

Before tagging an alpha, exercise the artifact in a real 26.3 client using each advertised loader. Verify the default, option persistence, finite cap, VSync, competing mod and real presentation cases in [presentation QA](PRESENTATION.md#manual-verification). Record loader versions, platform, driver, backend and artifact digest. Update the workflow's Modrinth loader list only after runtime verification.

Set `modVersion` in `gradle.properties`, then tag the reviewed commit with the matching `v` prefix. For example, version `0.1.0-alpha.1` uses tag `v0.1.0-alpha.1`. The tag build verifies the version match and automatically publishes that exact tested JAR to Modrinth as an alpha, beta, or stable release according to the version suffix.

The publish job uses the repository secrets `modrinth_pat` (Modrinth PAT with Create versions permission) and `modrinth_id` (project ID). Only the tag publishing job receives them. The [Modrinth Publish action](https://github.com/marketplace/actions/modrinth-publish) reports missing permissions or invalid project configuration in its job result. Before retrying a failed upload, check the project for an already-created version.
