# Verification contracts

The source of expected behavior is the approved NeoSunset specification, small independent reference implementations, and reviewed original fixtures. Keep expected results independent from the production path. Give each contract a stable coverage ID and a version. Link test IDs, fixture digests, commit and run to that row.

| Domain | Comparator | Representative edge cases |
| --- | --- | --- |
| Coordinates, registries and saves | Exact values or normalized serialized data | Negative boundaries, unknown IDs, old and newer-format saves |
| World generation and simulation | Canonical state digest per observation tick | Order of chunk loads, differing worker counts, save/reload, tied inputs |
| Rendering | Analytic geometry plus reviewed images | UVs, seams, resize, occlusion, depth and lighting |
| Timing | Preregistered budgets on named hardware | Bursts, long tails, cold starts, soak and recovery |
| Networking | Expected messages and authoritative state | Partial frames, gaps, reconnect, rejected input, version mismatch |
| Packs and modules | Schema fixtures and explicit capability results | Atomic reload, memory bounds and stable hook order |

Document inputs, seed, relevant versions, comparator, tolerances and platform scope before acceptance. Mark results PASS, FAIL, BLOCKED or NOT APPLICABLE. Coverage advances only with evidence for the exact tested commit. Review baseline changes independently and reopen a row when the relevant implementation or assumption changes. See [coverage](tracking/COVERAGE.md) and the [test harness](07-Automated-Testing.md).
