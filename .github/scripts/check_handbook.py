"""Validate relative file links in the versioned Markdown handbook."""

import re
import subprocess
import sys
from pathlib import Path
from urllib.parse import unquote, urlsplit

ROOT = Path(__file__).resolve().parents[2]
LINK = re.compile(r"!?\[[^\]\n]*\]\(<?([^<>\s)]+)>?\)")
FENCE = re.compile(r"^ {0,3}(`{3,}|~{3,})(.*)$")


def tracked_files():
    output = subprocess.check_output(["git", "ls-files", "-z"], cwd=ROOT)
    return {Path(path) for path in output.decode("utf-8").split("\0") if path}


def link_errors(files):
    directories = {parent for path in files for parent in path.parents}
    for path in sorted(files):
        if path.suffix.lower() != ".md":
            continue
        fence = None
        for line_number, line in enumerate(
            (ROOT / path).read_text(encoding="utf-8").splitlines(), start=1
        ):
            marker = FENCE.match(line)
            if fence:
                if (marker and marker[1][0] == fence[0]
                        and len(marker[1]) >= len(fence) and not marker[2].strip()):
                    fence = None
                continue
            if marker:
                fence = marker[1]
                continue
            for destination in LINK.findall(line):
                url = urlsplit(destination)
                if url.scheme or url.netloc or not url.path:
                    continue
                target = (ROOT / path.parent / unquote(url.path)).resolve()
                if not target.is_relative_to(ROOT):
                    yield f"{path}:{line_number}: link leaves the repository: {destination}"
                elif target.relative_to(ROOT) not in files | directories:
                    yield f"{path}:{line_number}: missing tracked link target: {destination}"


if __name__ == "__main__":
    tracked = tracked_files()
    errors = list(link_errors(tracked))
    if errors:
        print("\n".join(errors), file=sys.stderr)
    else:
        print(f"Checked relative links in {sum(p.suffix == '.md' for p in tracked)} Markdown files.")
    sys.exit(bool(errors))
