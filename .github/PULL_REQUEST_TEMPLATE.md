# Description

Summarize the changes made, your motivation, and include any linkage to issues
or other PRs here. Use "fixes #xxxx" for fixes and "connected to #xxxx" for
linked issues.

## Checklist

- [ ] I have read and understood the [Contributing Changes to a Component](https://docs.yoctoproject.org/dev/contributor-guide/submit-changes.html) and [Recipe Style Guide](https://docs.yoctoproject.org/dev/contributor-guide/recipe-style-guide.html#patch-upstream-status) sections in the Yocto Project documentation
- [ ] I have tested this change (provide brief details below)
- [ ] Documentation has been added/updated where necessary in the README and `docs/`
- [ ] If the change is to a BSP in
  [DEPRECATED.md](https://github.com/riscv/meta-riscv/blob/master/DEPRECATED.md),
  then it is maintenance-only, i.e. it does not add support for a
  previously-removed BSP (or significant features to one slated for removal)
- [ ] (For new/modified BSPs) I have added relevant information in the README [table](https://github.com/riscv/meta-riscv/tree/master?tab=readme-ov-file#available-machines)
- [ ] (For new/modified BSPs) The bitbake-setup templates in `bitbake-registry/`
  have been updated, if necessary
- [ ] (For new/modified BSPs) A `kas` file has been provided in `kas/`
- [ ] I have added my `Signed-off-by` and any other tags to each commit

# How has this been tested?

Provide a brief summary here (e.g. build only, build + boot test, something more
specific). Boot logs, images, or other artifacts are especially helpful.
