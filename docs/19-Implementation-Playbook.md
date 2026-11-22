# Implementation playbook

Run each numbered step as a small reviewable slice when it changes a separate contract. Record prereqs, acceptance cases and Actions run IDs before closing a slice. The agreed feature inventory and this playbook define scope for the chosen milestone.

## M0 · Workspace and support contract

1. Complete [M0.1](12-First-Task.md). Verify a downloaded diagnostics executable on each runner.
2. Record CI linkers, graphics candidates, OS floors and hardware certification owners. Separate build support, headless test support and interactive GPU certification.
3. Establish stable naming, dependency and asset-origin policies. Require a provenance record before importing any external work.

## M1 · Determinism and evidence

1. Specify coordinate ranges, Euclidean division across negative chunk boundaries, stable IDs and seed stream derivation. Test boundaries and overflow policy.
2. Define a versioned test case and result schema. Write a deterministic runner with isolated directories, process cleanup, time limits and explicit blocked status.
3. Add independent synthetic expected results, tick-indexed traces and canonical serialization. Inject a dropped tick, altered ID and timed-out child. Verify distinctive failures.
4. Add frame/tick/memory metrics with provenance. Record a synthetic baseline and measure instrumentation overhead. Accept only useful, reproducible results.

## M2 · Renderer and platform feasibility

1. Prove window/surface initialization and headless smoke tests on supported targets. Record adapter limits, format choices and fallback behavior in an ADR.
2. Build the CPU reference mesher for authored cubes. Test winding, all orientations, edge neighbors, UVs and negative coordinates. Build optimized meshing against the same expectations.
3. Add opaque, cutout and transparent passes with original fixtures, then camera controls and a small diagnostics overlay. Review depth, sampling, sort order and resize.
4. Test minimize, device loss, texture reload and rollback. Prototype LOD and upscaling only after correct native output. Capture real-hardware images and timings by backend.

## M3 · World, streaming and persistence

1. Define chunk snapshot ownership, registry IDs, edit ordering and deterministic scheduler boundaries. Implement seeded terrain and bounded loading radius.
2. Publish immutable render snapshots. Tag background work with generation/revision/asset epoch. Force stale completions and verify they cannot overwrite edits.
3. Specify save format, compatibility policy and file limits. Implement backup and atomic replacement with interrupted-write tests. Exercise non-ASCII paths, failed migrations and newer-format refusal.
4. Integrate edit, unload and reopen. Compare world digests on repeat runs and across worker counts. Establish time, memory and save-recovery budgets.

## M4 · First playable settlement

1. Write original rules for movement, interaction, material drops, placement actions and inventory conservation. Test collision, reach, invalid actions and save/reopen.
2. Implement harvest and construction of one shelter with an authored material set. Define consistent effects of light, weather and time.
3. Implement an original beacon network with documented link ranges, energy balance, tick ordering and clear diagnostics. Test overflow, disconnected graphs, unload and restoration.
4. Run the player scenario from a fresh world through one night and reopen the save. Compare observed rules to the approved spec and gather input/accessibility feedback.

## M5 · Content and accessibility

1. Approve an inventory of original regions, weather, organisms and building materials. Add one family at a time with a data schema and verification rows.
2. Add settings, audio and input remapping, keyboard focus, contrast, text scaling, subtitles and localization. Test focus restoration, long strings and missing devices.
3. Exercise failure paths and resource budgets during long sessions, dense settlements and mass edits. Analyze regressions with matched before/after evidence.

## M6 · Cooperative play and extensibility

1. Specify the NeoSunset protocol before external use. Establish authority, limits, handshake, version policy and authenticated sessions using established libraries.
2. Share simulation across in-process and dedicated hosting. Test delayed messages, malicious inputs, reconnect, rejected edits, resynchronization and server shutdown with synthetic peers.
3. Define native pack and module schemas, capability limits, reload transactions and compatibility rules. Open extension points only after concrete consumers exist.
4. Ship independent sample packs/modules, API reference, migration examples and a denial/budget test suite. Verify pack failures preserve the previous state.

## M7 · Integration and platform QA

1. Audit every declared requirement and its evidence. Repeat regressions against historical saves, clean installs, interrupted I/O and expected backend fallbacks.
2. Check real display, audio and input behavior per target. Record low capability tiers and known limitations. Verify minimum OS and driver claims on tested configurations.
3. Rehearse fresh install, upgrade, downgrade policy, backup recovery and clean uninstall. Run the benchmarks at matched settings and retain raw outputs.

## M8 · Release and ongoing maintenance

1. Select the exact release commit. Confirm all mandatory CI jobs and platform smoke results, dependency audit, disclosures and provenance.
2. Prepare platform artifacts with checksums, applicable signing, third-party notices, SBOM and release notes. Promote only artifacts produced in GitHub Actions for that commit.
3. Download published assets, verify hashes and test on fresh profiles. Run the rollback drill, publish documentation and reopen work for reported regressions.
