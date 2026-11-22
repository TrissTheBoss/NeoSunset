# GitHub and wiki operations

Version the handbook and [wiki source](../wiki/Home.md) alongside the contracts they describe. A pull request updates affected examples, release notes, migration instructions and coverage. Use the default branch as the current handbook and release tags for previous versions.

Use narrow branches and require the stable Actions result before merging. Restrict workflow permission changes, releases and protected branch updates to maintainers. Preserve source history and user changes. Associate the release commit with the artifact digests and contract versions.

The in-repository wiki is browsable from the README. If the owner enables the separate GitHub Wiki, sync reviewed pages with relative links adjusted for the wiki renderer and confirm each navigation link after publication. Leave publication evidence in the release notes.
