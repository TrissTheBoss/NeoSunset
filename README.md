# NeoSunset

NeoSunset is a client-side Minecraft Java Edition **26.3** mod that can skip surplus OpenGL buffer swaps or prefer Vulkan Mailbox presentation. It is off by default. Minecraft still renders the frames, and the server requires no NeoSunset installation.

## Use

1. Install the mod JAR in a Minecraft 26.3 profile with Java 25 and one of the loaders below.
2. Open **Options → Video Settings**. Set **Max Framerate** to **Unlimited** and turn **VSync** off.
3. Enable **NeoSunset: Refresh-aware presentation**. Leave it off for the game's standard presentation behavior.

The mod yields when Minecraft limits frames, the window loses focus, OpenGL reports a nonzero swap interval, or the loader reports third-party mods whose pacing behavior is unknown. Drivers and external overlays do not provide a universal limiter detection API. A rendering overhaul can still replace the hook, so test the specific profile before advertising active support.

| Loader | Minecraft 26.3 status |
| --- | --- |
| Fabric | 0.19.5 |
| Quilt | 0.30.2 |
| Forge | 66.1.5 |
| NeoForge | 66.1.5 |
| Babric | Targets beta-era Minecraft |
| LiteLoader | Targets older Minecraft releases through 1.12.2 |

The listed modern loader versions are covered by Linux software-rendered smoke tests. See the release notes for verification on a particular build.

Use [GitHub Actions](https://github.com/TrissTheBoss/NeoSunset/actions/workflows/ci.yml) for development builds. The [release checklist](docs/RELEASE.md) governs which builds are ready for Modrinth. See [design and compatibility](docs/PRESENTATION.md) for the two backends, caveats and test matrix.

The top-left in-game HUD shows **FPS** (Minecraft’s frame rate) and **Present attempts/s** (OpenGL swaps or Vulkan present calls). The latter excludes swaps skipped by NeoSunset but does not measure frames actually displayed: drivers, compositors and Vulkan Mailbox can discard or replace frames. The counters are independent of the presentation setting and hide with the HUD (`F1`).

## Troubleshooting

If enabling NeoSunset has no visible effect, start with a profile that has only NeoSunset and its loader. Keep the game focused and confirm that **Max Framerate** is **Unlimited** and **VSync** is off.

- **OpenGL:** NeoSunset leaves swaps alone if it can't determine the display refresh rate, or if the swap interval is unknown or nonzero.
- **Vulkan:** NeoSunset keeps the game's present mode unless the game chose Immediate and the device supports Mailbox.

The FPS counter alone isn't a reliable check: Minecraft still renders frames even when NeoSunset skips an OpenGL swap. See the [manual verification table](docs/PRESENTATION.md#manual-verification) for expected behavior.

NeoSunset is independent of Mojang and Microsoft. Source is under the [MIT License](LICENSE).
