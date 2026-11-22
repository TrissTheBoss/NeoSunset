# Risk register

| ID | Risk | Priority | Evidence and mitigation |
| --- | --- | --- | --- |
| R-01 | Required runner image changes or retires | High | M0 verifies native host, monitors image announcements and keeps all five lanes visible |
| R-02 | GPU or OS floor unsupported | High | M2 backend feasibility and M7 tested certification matrix |
| R-03 | Deterministic state diverges with worker count | High | Snapshot isolation, prescribed commit phases and canonical digest tests |
| R-04 | Interrupted save or migration loses data | High | Fault injection, backup recovery and version compatibility fixtures |
| R-05 | Pack or dependency distribution rights unclear | High | Scope provenance before admission and audit before release |
| R-06 | Content schedule expands without acceptance coverage | Medium | Owner-approved slice inventory and evidence-first review |
| R-07 | Hosted toolchain success mistaken for user experience | Medium | Separate CI, real-hardware and clean-install evidence |

All entries are open. Set an owner and review date when work enters the corresponding milestone.
