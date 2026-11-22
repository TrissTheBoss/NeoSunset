# Product requirements

NeoSunset is an original voxel sandbox about shaping a landscape and building settlements that stay habitable through a changing light and weather cycle. The proposed first loop is to explore, gather materials, build shelter, connect light beacons, and maintain the settlement overnight. The design uses original names, visuals, progression, interfaces and world rules. The owner approves content quantities and rules before each milestone.

The initial playable slice has one original region, a small authored material set, basic building and harvesting, a light network, and save/reopen. Later work adds environments, weather, organisms, exploration rewards, a cooperative server, translation and accessibility, and packs/modules. Think of the milestones as delivery gates. [The playbook](19-Implementation-Playbook.md) gives each gate concrete steps.

| ID | Contract | Required evidence |
| --- | --- | --- |
| REQ-01 | Safe Rust runtime and custom WGSL | Dependency review and tests for each owned boundary |
| REQ-02 | Deterministic world and simulation rules | Same-seed, replay, reload and worker-count cases |
| REQ-03 | Responsive frame and tick behavior | Named hardware, bounded budgets and raw baselines |
| REQ-04 | Streamed terrain and editable meshes | Boundary, cancellation, queue and stale-result tests |
| REQ-05 | Original settlement and light-network loop | Playable end-to-end scenario with meaningful tradeoffs |
| REQ-06 | Crash-safe world saves | Interruption, corruption, migration and recovery cases |
| REQ-07 | Cooperative authority | Loopback and remote session tests for the project's protocol |
| REQ-08 | Versioned packs and modules | Reload, compatibility, isolation and independent examples |
| REQ-09 | Accessible and localized presentation | Keyboard navigation, focus, scaling, subtitles and long text |
| REQ-10 | Five GitHub Actions build targets | Exact-commit build, tests and downloaded artifact evidence |
| REQ-11 | Safe distribution | Origin records, notices, SBOM, digests and rollback rehearsal |
| REQ-12 | Automated evidence | Reproducible cases, bounded suites and explicit blocked results |

Use a **Reference** render profile for stable native-resolution captures and an **Enhanced** profile for documented visual tradeoffs. Both use identical simulation inputs and rules. Modules that change gameplay use a separate profile and matching peers and saves. [Architecture](03-Architecture.md) and [verification](02-Verification.md) define the boundaries.
