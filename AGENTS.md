# NeoSunset development

Work on Minecraft Java Edition 26.3 and Java 25. Use GPT-6 Luna at medium when configured in the agent host. Keep builds and tests in GitHub Actions. Inspect the affected bytecode descriptors against the official game build before modifying a Mixin, and exercise real clients before adding a loader to release metadata.

Read [presentation design](docs/PRESENTATION.md) and [release rules](docs/RELEASE.md) first. Preserve ordinary client behavior when the setting is off, the cap is finite, VSync is on, or another mod's pacing behavior is unknown. Keep frame-loop logic allocation-free when practical. Avoid background jobs, waits, locks and owned graphics resources in the presentation hooks. Add focused tests for policy and cadence changes. Record evidence for the exact packaged commit.
