"""Reject incorrect release metadata or non-mod content before publishing."""

import json
import os
from pathlib import Path
import re
import subprocess
from struct import unpack
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
is_tag = os.environ["IS_TAG"] == "true"
if is_tag and os.environ["GITHUB_REF_NAME"] != f"v{version}":
    raise SystemExit("Tag must match the mod version.")

notes = Path("releases") / f"v{version}.md"
publish = is_tag
if (os.environ["GITHUB_EVENT_NAME"] == "push"
        and os.environ["GITHUB_REF"] == "refs/heads/main" and notes.is_file()):
    event = json.loads(Path(os.environ["GITHUB_EVENT_PATH"]).read_text())
    changed = subprocess.check_output(
        ["git", "diff", "--name-only", "--no-renames", "--diff-filter=A", event["before"], "HEAD", "--", str(notes)],
        text=True,
    )
    publish = changed.strip() == str(notes)

with ZipFile(Path(f"build/libs/neosunset-{version}.jar")) as jar:
    names = set(jar.namelist())
    fabric = json.loads(jar.read("fabric.mod.json"))
    mixin = json.loads(jar.read("neosunset.mixins.json"))
    assert fabric["version"] == version and fabric["depends"]["minecraft"] == f"={game}"
    assert fabric["environment"] == "client" and "LICENSE" in names
    icon_path = "neosunset.png"
    icon = jar.read(icon_path)
    assert fabric["icon"] == icon_path
    assert icon == Path("src/main/resources", icon_path).read_bytes()
    assert icon.startswith(b"\x89PNG\r\n\x1a\n") and icon[12:16] == b"IHDR"
    assert unpack(">II", icon[16:24]) == (128, 128)
    expected_mixin = json.loads(Path("src/main/resources/neosunset.mixins.json").read_text(encoding="utf-8"))
    assert mixin == expected_mixin, "Packaged Mixin config differs from the production source config"
    assert not any("ClientSmokeMixin" in name for name in names)
    for client in mixin["client"]:
        assert f"{mixin['package'].replace('.', '/')}/{client}.class" in names
    for file in ("mods.toml", "neoforge.mods.toml"):
        config = tomllib.loads(jar.read(f"META-INF/{file}").decode())
        assert config["mods"][0]["version"] == version
        assert config["mods"][0]["logoFile"] == icon_path
        assert config["dependencies"]["neosunset"][0]["versionRange"] == f"[{game}]"
    for name in names:
        if name.endswith(".class") and not name.startswith("dev/neosunset/"):
            raise SystemExit(f"Unexpected bundled class: {name}")
    assert "assets/neosunset/lang/en_us.json" in names
    assert all(not name.endswith(".jar") for name in names)

with open(os.environ["GITHUB_OUTPUT"], "a", encoding="utf-8") as output:
    print(f"version={version}", file=output)
    print(f"channel={format_match[1] or 'release'}", file=output)
    print(f"publish={str(publish).lower()}", file=output)
