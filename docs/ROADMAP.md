# NeoSunset milestones

Each milestone is scoped to roughly **30 minutes of hands-on work**. CI queues, hardware access and soak time are not included. Keep results tied to the exact candidate commit and JAR SHA-256 using the [release guide](RELEASE.md). If a check fails or needs more hardware, record what happened and open a follow-up instead of stretching the work block or marking an unrun check as passed.

| Milestone | Type | Done when |
| --- | --- | --- |
| 01 — HUD toggle review | Development | Review the overlay toggle and persistence checks, confirm it does not affect presentation eligibility, and prepare the branch for CI. |
| 02 — Candidate build and loader smoke | Required test | The exact candidate passes the Java 25 build, policy tests, JAR verification and Fabric, Quilt, Forge and NeoForge Linux smoke jobs. Record the run URL and JAR SHA-256. |
| 03 — Prism HUD and restart | Required test | On the Windows Prism `Testing` profile, verify both counters show by default, the toggle hides only the counters, and the choice survives restart. Confirm `F1` still hides the whole HUD. |
| 04 — Native OpenGL cadence and fallback | Required test | On a hardware-rendered client, compare one scene with presentation off and on, then verify fallback with a finite cap and with VSync enabled. Record swap-attempt evidence separately from any display measurement. |
| 05 — Native Vulkan mode and fallback | Required test | On a Mailbox-capable hardware-rendered client, verify the eligible Immediate-to-Mailbox switch and vanilla behavior when ineligible. Record present-mode evidence; mark unavailable configurations as not run. |
| 06 — Window transitions and soak | Required test | Exercise alt-tab, resize, fullscreen and moving between displays, then record the outcome of a 30-minute session with repeated toggling and a clean shutdown. Split by backend if needed. |
| 07 — Third-party fallback | Required test | Run one representative profile with an unverified pacing mod and confirm NeoSunset leaves pacing alone. Record the exact mod versions; add separate checkpoints for other profiles as needed. |
| 08 — Release readiness review | Release checkpoint | Compare evidence against the [release rules](RELEASE.md) and [manual QA matrix](PRESENTATION.md#manual-verification). Track missing loader, platform or backend checks in separate work blocks and choose the release channel based on the evidence. This milestone does not publish anything. |

Actual displayed FPS is a separate investigation: the current HUD counts render FPS and present attempts, not monitor scanout. Do not gate the HUD alpha on adding a scanout counter.
