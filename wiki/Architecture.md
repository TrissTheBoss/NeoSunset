# Architecture

The Rust simulation owns world state. The renderer consumes immutable snapshots and presents them through a custom WGSL pipeline. Read the [architecture](../docs/03-Architecture.md) and [renderer plan](../docs/04-Renderer.md) for proposed boundaries.
