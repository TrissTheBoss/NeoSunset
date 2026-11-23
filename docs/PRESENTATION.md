# Presentation design

## OpenGL

The hook intercepts the SDL swap in RenderPearl's OpenGL surface. The renderer still blits, submits, polls events, and clears the wrapper's acquired-image state. The scheduling primitive uses a monotonic clock and keeps one deadline, with no queued frames or owned GPU resources. It rechecks the active SDL display mode and the actual OpenGL swap interval up to four times per second and invalidates that cache on display, size, focus and fullscreen changes. Unknown refresh or swap interval falls back to the normal swap.

A timer estimates refresh cadence. It cannot prove which frames the display scanned out or eliminate tearing with VSync disabled. It skips attempted swaps between expected refresh boundaries and never sleeps. Late frames advance the cadence without replaying missed slots.

## Vulkan

When eligible, request the existing RenderPearl Mailbox present mode if the device offers it and the game's choice would otherwise be Immediate. The Vulkan implementation owns image acquisition, submission, signaling and swapchain resets. Mailbox lets the presentation engine replace a queued frame. Vulkan still queues every submitted present, and a Vulkan device without Mailbox preserves the game's mode.

To verify Vulkan mode selection, check `logs/latest.log` for `Vulkan swapchain present mode:`. This diagnostic logs the initial mode and subsequent changes after successful swapchain configuration. With a focused game, Unlimited FPS and VSync off, record the mode with NeoSunset presentation off, then on. If the baseline is Immediate and Mailbox is supported, expect Mailbox when on. Leave presentation on and set a finite cap, then repeat with Unlimited FPS and VSync on. Compare each fallback against the mode with NeoSunset off under the same settings. An unchanged mode is not logged again for that surface; restart with the desired options if you need a fresh baseline.

## Compatibility policy

Match the selected Unlimited sentinel and the effective vanilla limit. The OpenGL swap additionally checks the limiter value used by the renderer for that frame. Default-off stays off. A clean loader profile is eligible. Profiles that introduce unknown mods stay usable while NeoSunset leaves presentation alone. There is no generic discovery API for mods that independently sleep, wrap native calls or change the driver. Admit a mod combination only after it has a reliable pacing signal and test evidence, including a test that changes the competing cap at runtime.

Verify Windows AMD64/ARM64, Linux ARM64, and macOS Apple Silicon/Intel where Minecraft 26.3 and the respective loader supply their required natives. These are client runtime checks. The JVM build produces the same JAR regardless of architecture.

## Manual verification

| Case | Expected result |
| --- | --- |
| Default or toggle off | Vanilla behavior, including after restart |
| Unlimited, VSync off, focused, clean profile | OpenGL skips surplus swaps; Vulkan uses Mailbox if supported |
| Finite FPS, VSync, inactivity, or unknown mod | Vanilla pacing or presentation applies |
| Alt-tab, resize, fullscreen, monitor move | Normal transition and fresh display cadence after resuming |
| Unsupported Vulkan Mailbox or unknown OpenGL refresh | Vanilla mode or swap |
| 30-minute session with repeated resize and toggling | Stable memory and clean shutdown |

Compare completed frames, swap attempts, displayed frames, p95/p99 frame time, input latency and GPU utilization on the same scene. An FPS counter can rise while the display shows the same number of frames. Review a rendering overhaul or modpack for safe fallback before declaring active support.
