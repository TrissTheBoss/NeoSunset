# NeoSunset

NeoSunset is a planned original voxel sandbox in Rust with a custom WGSL renderer. The design centers on exploring a procedural world, shaping terrain, building persistent settlements, and managing a network of light and energy as day turns to night. The first playable milestone is a small single-player slice built around that loop. Cooperative play, modding, and full platform certification follow in later milestones.

**Current status** · Development handbook and GitHub Actions foundation. The repository has no Cargo workspace or executable yet. The proposed interfaces, renderer features, and release targets below become available only when the corresponding milestones pass their acceptance gates.

## Start here

| Reader | Starting point |
| --- | --- |
| Project owner | [Product brief](docs/01-PRD.md), [milestones](docs/10-Roadmap.md), [current handoff](HANDOFF.md) |
| GPT-6 Luna · medium | [Repository instructions](AGENTS.md), [AI workflow](docs/11-AI-Workflow.md), [first task](docs/12-First-Task.md) |
| Contributor | [Contribution guide](CONTRIBUTING.md), [architecture](docs/03-Architecture.md), [implementation playbook](docs/19-Implementation-Playbook.md) |
| Tester or release owner | [GitHub Actions](docs/18-GitHub-Actions.md), [verification](docs/02-Verification.md), [release gates](docs/17-Release.md) |

## Build and download

Open [GitHub Actions](https://github.com/TrissTheBoss/NeoSunset/actions/workflows/ci.yml), select the run for the commit under review, and open the job for your platform. Pull requests and pushes start the workflow automatically. When the workflow is available on the default branch, maintainers can also use **Run workflow**. The foundation validates the handbook and runner architecture. Once the workspace lands, the same workflow will compile, test and build API documentation for the targets below. Release-profile Cargo output will appear under **Artifacts** for successful runs.

| Platform | Rust target | GitHub-hosted runner |
| --- | --- | --- |
| Windows AMD64 | `x86_64-pc-windows-msvc` | `windows-2025` |
| Windows ARM64 | `aarch64-pc-windows-msvc` | `windows-11-arm` |
| Linux ARM64 | `aarch64-unknown-linux-gnu` | `ubuntu-24.04-arm` |
| macOS Apple Silicon | `aarch64-apple-darwin` | `macos-15` |
| macOS Intel | `x86_64-apple-darwin` | `macos-15-intel` |

Each lane uses its target architecture and checks the native toolchain host before building. Minimum OS versions, graphics support and any distribution signing requirements are set through the [platform certification gates](docs/17-Release.md).

## Engineering handbook

| Area | Documents |
| --- | --- |
| Product and delivery | [Requirements](docs/01-PRD.md) · [Verification](docs/02-Verification.md) · [Roadmap](docs/10-Roadmap.md) · [Implementation playbook](docs/19-Implementation-Playbook.md) |
| Code and graphics | [Architecture](docs/03-Architecture.md) · [Renderer](docs/04-Renderer.md) · [Optimization](docs/05-Optimizations.md) · [Modules and packs](docs/06-Modding-Packs.md) |
| Evidence | [Testing](docs/07-Automated-Testing.md) · [Visual QA](docs/08-Visual-QA.md) · [Benchmarks](docs/09-Benchmarks.md) |
| Operations | [GitHub and Wiki](docs/13-GitHub-Wiki.md) · [Security and assets](docs/14-Security-Assets.md) · [Tracking](docs/15-Tracking-Rules.md) · [Research](docs/16-Research.md) · [Release](docs/17-Release.md) · [GitHub Actions](docs/18-GitHub-Actions.md) |
| Current records | [Progress](docs/tracking/PROGRESS.md) · [Coverage](docs/tracking/COVERAGE.md) · [Risks](docs/tracking/RISKS.md) · [Decisions](docs/decisions/INDEX.md) · [In-repository wiki](wiki/Home.md) |

## License and assets

The project is distributed under the [MIT License](LICENSE), with the copyright notice from the starter handbook preserved. Contributions must be original or carry independently documented permission and any required notices. See [asset provenance](docs/14-Security-Assets.md) and the [security reporting policy](SECURITY.md).
