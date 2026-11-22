# Tracking decisions and evidence

Each task references its milestone, requirement IDs, stable coverage IDs, dependencies, test IDs, owner and expected evidence. Use NOT STARTED, READY, IN PROGRESS, BLOCKED, IMPLEMENTED and VERIFIED. VERIFIED requires evidence for the exact commit, targets and supported scope. Keep one active milestone slice in the handoff.

The [coverage register](tracking/COVERAGE.md) starts at domain level. Expand it into contract-level cases as scope is approved. Record source fixture hash, expected comparator, tests, exact run, supported platform and discrepancy issue. Reopen affected rows when contracts or fixtures change.

Record risks with likelihood, impact, a person to own the next action and a concrete trigger or mitigation. Use [an ADR](decisions/TEMPLATE.md) for durable changes to architecture, public interfaces, format compatibility, dependencies, budgets or operating policy. Track state in the [decision index](decisions/INDEX.md).
