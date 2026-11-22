# GPT-6 Luna development workflow

Select **GPT-6 Luna** and **medium** in the host that runs the agent. Record the actual model and effort displayed by that host. Use a fresh context for each milestone slice. If the requested configuration is unavailable, record it and let the owner choose a replacement.

## Repeat for each slice

1. Read the current handoff, the assigned playbook subsection, the current tests and the nearest repository rules. Inspect Git status and the latest relevant Actions run.
2. Form a testable contract. List inputs and outputs, state ownership, affected callers and platforms, expected errors, and a test for each new behavior.
3. Pick one small dependency-ordered slice. Record open decisions and use an ADR when an API, file format, dependency, compatibility promise or budget changes.
4. Implement the smallest end-to-end change. Preserve user work and supported data. Cover the normal path, one meaningful boundary case and recoverable failure where applicable.
5. Inspect changed callers, tests and diff. Submit the authorized branch or PR. Follow Actions on the exact commit, diagnose failures from logs, and rerun only after an infrastructure transient or an evidence-based fix.
6. Recheck the final diff and all required jobs. Update coverage, progress, ADRs and handoff with real evidence, precise blockers and the next small task.

## Task prompt

> Use GPT-6 Luna at medium. Deliver milestone [ID], slice [ID]. Read AGENTS.md, HANDOFF.md and the linked playbook subsection. Keep the change within [scope]. Before editing, list observable acceptance criteria, invariants, affected callers and platforms. Add focused tests for [cases]. Compile and verify through GitHub Actions. Report the tested commit, run URL, per-target results, missing evidence, and the next slice. Preserve user changes and avoid speculative work.

## Review prompts

- **Correctness** · What input breaks the invariant? What happens on retry, interruption, version mismatch or simultaneous edits? Point to a test or add a focused one.
- **Portability** · Which paths, linker dependencies, target features or OS assumptions differ across the five lanes? Point to native evidence.
- **Evidence** · Did the exact code compile? Is the case using independent expectations? Could skipped prerequisites or a weak assertion produce a false pass?
- **Handoff** · Which files and contracts changed? What result remains blocked? What is the smallest authorized next slice?
