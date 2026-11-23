"""Install a pinned 26.3 loader and launch the isolated demo smoke profile."""

from hashlib import sha256
import json
from pathlib import Path
import shutil
import subprocess
import sys
from urllib.request import Request, urlopen

from minecraft_launcher_lib import command, install

GAME = "26.3"
LOADERS = {
    "fabric": (
        "fabric-loader-0.19.5-26.3",
        "https://meta.fabricmc.net/v2/versions/loader/26.3/0.19.5/profile/json",
        "b607511943b20dc1aec2549b6e3a520faa636aa79ee9acfd6041677916f9b1a5",
    ),
    "quilt": (
        "quilt-loader-0.30.2-26.3",
        "https://meta.quiltmc.org/v3/versions/loader/26.3/0.30.2/profile/json",
        "c591be48451a4e986647fcad3ca2832a14571443f8de1baf7db9998366361ae1",
    ),
    "forge": (
        "26.3-forge-66.1.5",
        "https://maven.minecraftforge.net/net/minecraftforge/forge/26.3-66.1.5/forge-26.3-66.1.5-installer.jar",
        "4ba54a1bc216ad12dde032eecb999cb3c617b1d0c92ad67add4b6531f45e58b4",
    ),
    "neoforge": (
        "neoforge-26.3.0.102",
        "https://maven.neoforged.net/releases/net/neoforged/neoforge/26.3.0.102/neoforge-26.3.0.102-installer.jar",
        "e26e42b0414da4f68840835b4d637928b971a01852b32b6424747290f84e9f1c",
    ),
}


def install_loader(loader, root):
    profile, url, expected = LOADERS[loader]
    request = Request(url, headers={"User-Agent": "NeoSunset-GitHub-CI/1.0 (+https://github.com/TrissTheBoss/NeoSunset)"})
    print(f"Retrieving {loader} installer or profile", flush=True)
    with urlopen(request, timeout=30) as response:
        data = response.read(16 * 1024 * 1024 + 1)
    if loader in {"fabric", "quilt"}:
        spec = json.loads(data)
        # These timestamps may be regenerated without changing launcher dependencies.
        content = {key: value for key, value in spec.items() if key not in {"time", "releaseTime"}}
        canonical = json.dumps(content, sort_keys=True, separators=(",", ":")).encode()
        if sha256(canonical).hexdigest() != expected:
            raise ValueError(f"{loader} launcher profile checksum mismatch")
        if spec["id"] != profile or spec["inheritsFrom"] != GAME:
            raise ValueError("Unexpected launcher profile ID or base game version")
        version_dir = root / "versions" / profile
        version_dir.mkdir(parents=True)
        (version_dir / f"{profile}.json").write_bytes(data)
    else:
        if sha256(data).hexdigest() != expected:
            raise ValueError(f"{loader} installer checksum mismatch")
        installer = root / "installer.jar"
        installer.write_bytes(data)
        (root / "launcher_profiles.json").write_text('{"profiles":{}}')
        subprocess.run(["java", "-jar", str(installer), "--installClient", str(root)],
                       cwd=root, timeout=240, check=True)

    print(f"Installing {profile}", flush=True)
    install.install_minecraft_version(profile, root)
    return profile


def main():
    loader, root_path, jar_path = sys.argv[1:]
    root = Path(root_path).resolve()
    root.mkdir(parents=True)
    print(f"Installing Minecraft {GAME}", flush=True)
    install.install_minecraft_version(GAME, root)
    profile = install_loader(loader, root)
    mods = root / "mods"
    mods.mkdir()
    shutil.copy2(jar_path, mods / "neosunset-smoke.jar")

    arguments = command.get_minecraft_command(profile, root, {
        "username": "NeoSunsetSmoke",
        "uuid": "00000000000000000000000000000000",
        "token": "0",
        "demo": True,
        "disableMultiplayer": True,
        "gameDirectory": str(root),
        "executablePath": "java",
        "jvmArguments": ["-Xmx2G"],
    })
    print(f"Launching {profile}", flush=True)
    try:
        subprocess.run(arguments, cwd=root, timeout=160, check=True)
    except subprocess.TimeoutExpired:
        raise SystemExit(f"{profile} did not exit within the 160-second client timeout") from None


if __name__ == "__main__":
    main()
