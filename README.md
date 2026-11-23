# NeoSunset

NeoSunset is a client-side Minecraft Java Edition **26.3** mod that can skip surplus OpenGL buffer swaps or prefer Vulkan Mailbox presentation. It is off by default. Minecraft still renders the frames, and the server requires no NeoSunset installation.

## Use

1. Install the mod JAR in a Minecraft 26.3 Fabric profile with Java 25 and Fabric Loader 0.19.5 or newer.
2. Open **Options → Video Settings**. Set **Max Framerate** to **Unlimited** and turn **VSync** off.
3. Enable **NeoSunset: Refresh-aware presentation**. Leave it off for the game's standard presentation behavior.

The mod yields when Minecraft limits frames, the window loses focus, OpenGL reports a nonzero swap interval, or the loader reports third-party mods whose pacing behavior is unknown. Drivers and external overlays do not provide a universal limiter detection API. A rendering overhaul can still replace the hook, so test the specific profile before advertising active support.

| Loader | Minecraft 26.3 status |
| --- | --- |
| Fabric | Initial distribution target |
| Quilt | Fabric-compatible packaging candidate, requires runtime verification |
| Forge | Dedicated loader metadata included, requires runtime verification |
| NeoForge | Dedicated loader metadata included, requires runtime verification |
| Babric | Targets beta-era Minecraft |
| LiteLoader | Targets older Minecraft releases through 1.12.2 |

Use [GitHub Actions](https://github.com/TrissTheBoss/NeoSunset/actions/workflows/ci.yml) for development builds. The [release checklist](docs/RELEASE.md) governs which builds are ready for Modrinth. See [design and compatibility](docs/PRESENTATION.md) for the two backends, caveats and test matrix.

NeoSunset is independent of Mojang and Microsoft. Source is under the [MIT License](LICENSE).
