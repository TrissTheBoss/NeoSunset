# NeoSunset agent instructions

Default agent configuration is GPT-6 Luna with medium reasoning effort. Select this in the host UI or its documented configuration. Record the observed selection and any discrepancy in the handoff.

## Start every task

1. Read [HANDOFF.md](HANDOFF.md), the current row in [progress](docs/tracking/PROGRESS.md), and the matching section of the [implementation playbook](docs/19-Implementation-Playbook.md).
2. Inspect Git status, the target branch and the nearest applicable instructions. Preserve existing changes and inspect callers before changing an interface.
3. Write a narrow task contract tied to a requirement and test IDs. Include scope, invariants, failure cases, target-specific risks and expected GitHub Actions evidence.
4. Load the relevant domain documents and accepted decisions. Search the repository before assuming a file, command, crate, feature flag or interface exists.

## Implement and verify

- Use safe first-party Rust and original WGSL. Keep simulation independent of presentation and define data ownership, ordering, bounds and error handling at each boundary.
- Preserve previous saves and already-supported callers. Plan migration, crash recovery and rollback before changing formats. Keep fixtures synthetic and distributable.
- Use original art, audio, lore, UI and level designs. Record rights for any third-party dependency, example or asset.
- Preserve the starter's production-source comment convention. Keep design explanations in Markdown and retain mandatory license notices. Document public API use with checked examples.
- Add focused tests for behavior changes. Prefer fixed seeds, explicit ordering and injected failure to wall-clock timing. Keep assertions meaningful when fixing regressions.
- All Cargo builds, Rust tests, Clippy and Rustdoc run on GitHub Actions. Use static checks and diff review in the editor. Report the exact commit and the Actions run, plus per-platform results or blockers.
- Keep one milestone slice per PR. Update coverage, progress and handoff. Log accepted changes to architecture, compatibility, dependencies or performance budgets as ADRs.

## Stop conditions

Stop the affected slice when a prerequisite, product decision, authorization or target result is missing. Record the blocker and proceed with independent work when possible. Keep unsupported capabilities visible. Preserve owner review and Git history. See the [AI task workflow](docs/11-AI-Workflow.md).
