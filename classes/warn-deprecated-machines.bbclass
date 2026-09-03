# Print a warning when a build is started with a MACHINE which is slated to be
# removed as of a future Yocto Project release.

python warn_deprecated_machines() {
    deprecated_machines = {
        "ae350-ax45mp": "Yocto 6.1",
        "beaglev-starlight-jh7100": "Yocto 6.1",
        "eswin-ebc77": "Yocto 6.1",
        "milkv-megrez": "Yocto 6.1",
        "nezha-allwinner-d1": "Yocto 6.1",
        "star64": "Yocto 6.1",
        "visionfive": "Yocto 6.1",
    }

    machine = d.getVar("MACHINE")

    if machine in deprecated_machines:
        removal_release = deprecated_machines[machine]
        bb.warn(
            f"MACHINE='{machine}' is deprecated and will no longer be supported "
            f"after {removal_release}. Please consider an alternative BSP."
        )
}

addhandler warn_deprecated_machines
warn_deprecated_machines[eventmask] = "bb.event.BuildStarted"
