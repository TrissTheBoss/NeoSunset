# Research and dependency review

Record the question, source version, retrieval date, exact claim, experiment and result. Validate dependency APIs on the pinned version in GitHub Actions. Distinguish vendor support, an observed local result and an open hypothesis.

| Source | Use |
| --- | --- |
| [Rust platform tiers](https://doc.rust-lang.org/rustc/platform-support.html) | Target and host tooling baseline |
| [GitHub-hosted runners](https://docs.github.com/en/actions/reference/runners/github-hosted-runners) | Labels, availability and hosted-machine limits |
| [Cargo lockfile guidance](https://doc.rust-lang.org/cargo/guide/cargo-toml-vs-cargo-lock.html) | Build reproducibility |
| [WebGPU specification](https://www.w3.org/TR/webgpu/) and [WGSL](https://www.w3.org/TR/WGSL/) | Shader and GPU model |
| [wgpu documentation](https://docs.rs/wgpu/) | Candidate backend implementation and API |
| [0 FPS voxel meshing](https://0fps.net/2012/06/30/meshing-in-a-minecraft-game/) | General face reduction ideas; verify material and interpolation equivalence in NeoSunset |
| [WAI WCAG 2.2](https://www.w3.org/TR/WCAG22/) | Reference for useful contrast, navigation and input design criteria |

Open questions include minimum OS and GPU feature floors, pinned graphics dependencies, architecture-specific compiler and linker prerequisites, low-end fallback budgets and module sandbox overhead. Resolve each at the assigned milestone with actual tests and an ADR when appropriate.
