"""Reject incorrect release metadata or non-mod content before publishing."""

import json
import os
from pathlib import Path
import re
import tomllib
from zipfile import ZipFile

properties = dict(
    line.split("=", 1)
    for line in Path("gradle.properties").read_text().splitlines()
    if "=" in line
)
version = properties["modVersion"]
game = properties["minecraftVersion"]
format_match = re.fullmatch(r"\d+\.\d+\.\d+(?:-(alpha|beta)\.\d+)?", version)
if format_match is None:
    raise SystemExit("Expected a stable version or a numbered alpha/beta version.")
if os.environ["IS_TAG"] == "true" and os.environ["GITHUB_REF_NAME"] != f"v{version}":
    raise SystemExit("Tag must match the mod version.")

with ZipFile(Path(f"build/libs/neosunset-{version}.jar")) as jar:
    names = set(jar.namelist())
    fabric = json.loads(jar.read("fabric.mod.json"))
    mixin = json.loads(jar.read("neosunset.mixins.json"))
    assert fabric["version"] == version and fabric["depends"]["minecraft"] == f"={game}"
    assert fabric["environment"] == "client" and "LICENSE" in names
    assert len(mixin["client"]) == 4
    assert not any("ClientSmokeMixin" in name for name in names)
    for client in mixin["client"]:
        assert f"{mixin['package'].replace('.', '/')}/{client}.class" in names
    for file in ("mods.toml", "neoforge.mods.toml"):
        config = tomllib.loads(jar.read(f"META-INF/{file}").decode())
        assert config["mods"][0]["version"] == version
        assert config["dependencies"]["neosunset"][0]["versionRange"] == f"[{game}]"
    for name in names:
        if name.endswith(".class") and not name.startswith("dev/neosunset/"):
            raise SystemExit(f"Unexpected bundled class: {name}")
    assert "assets/neosunset/lang/en_us.json" in names
    assert all(not name.endswith(".jar") for name in names)

with open(os.environ["GITHUB_OUTPUT"], "a", encoding="utf-8") as output:
    print(f"version={version}", file=output)
    print(f"channel={format_match[1] or 'release'}", file=output)
