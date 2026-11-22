# Contributing

Read [AGENTS.md](AGENTS.md), check [the current milestone](docs/tracking/PROGRESS.md) and use the relevant section of the [implementation playbook](docs/19-Implementation-Playbook.md).

1. File a task with a requirement ID, concrete acceptance tests, dependencies and affected platforms. Record public interface or format changes in an ADR.
2. Start a focused branch from the default branch. Preserve user changes and author original code, fixtures and examples.
3. Open a pull request with the requested verification and migration details. GitHub Actions handles compilation and Rust verification.
4. Review each required result on the final commit and attach links to durable or redacted evidence. Resolve failures and record blocked work.
5. Update the affected contract, coverage, progress and handoff before owner review.

Use a bug report for reproducible public issues. Follow the [security policy](SECURITY.md) for sensitive details.
