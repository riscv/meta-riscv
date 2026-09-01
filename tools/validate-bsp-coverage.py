#!/usr/bin/env python3
"""Check that every BSP in conf/machine has a matching kas file and
bitbake-registry entry, and that kas/bitbake-registry don't reference a
machine that no longer exists.
"""
import json
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent

# Bare-metal targets don't build through bitbake-setup/kas.
NOT_A_BSP = {"baremetal-riscv32", "baremetal-riscv32nf", "baremetal-riscv64"}
# Stock oe-core machine used as the kas base default, not a meta-riscv BSP.
NOT_A_BSP_REVERSE = NOT_A_BSP | {"qemuriscv64"}

MACHINE_RE = re.compile(r"^machine:\s*['\"]?([^'\"\s]+)['\"]?", re.MULTILINE)


def get_bsps():
    return {
        p.stem
        for p in (ROOT / "conf/machine").glob("*.conf")
    } - NOT_A_BSP


def get_kas_machines():
    result = {}
    for p in (ROOT / "kas").glob("*.yml"):
        m = MACHINE_RE.search(p.read_text())
        if m:
            result.setdefault(m.group(1), []).append(p.name)
    return result


def get_registry_machines(path):
    data = json.loads(path.read_text())
    machines = set()
    for cfg in data["bitbake-setup"]["configurations"]:
        options = cfg.get("oe-fragments-one-of", {}).get("machine", {}).get("options", [])
        for opt in options:
            machines.add(opt["name"].removeprefix("machine/"))
    return machines


def print_table(bsps, kas_machines, bitbake_registries):
    reg_names = [p.name for p in bitbake_registries]
    headers = ["BSP", "kas file(s)"] + reg_names + ["status"]
    rows = []
    for bsp in sorted(bsps):
        has_kas = bsp in kas_machines
        kas_cell = ", ".join(kas_machines.get(bsp, [])) or "MISSING"
        reg_hits = [bsp in machines for machines in bitbake_registries.values()]
        reg_cells = ["yes" if hit else "MISSING" for hit in reg_hits]
        status = "OK" if has_kas and all(reg_hits) else "FAIL"
        rows.append([bsp, kas_cell] + reg_cells + [status])

    widths = [max(len(h), *(len(r[i]) for r in rows)) if rows else len(h) for i, h in enumerate(headers)]
    line = "  ".join(h.ljust(w) for h, w in zip(headers, widths))
    print(line)
    print("  ".join("-" * w for w in widths))
    for r in rows:
        print("  ".join(c.ljust(w) for c, w in zip(r, widths)))
    print()


def main():
    problems = []

    bsps = get_bsps()
    kas_machines = get_kas_machines()
    bitbake_registries = {p: get_registry_machines(p) for p in sorted((ROOT / "bitbake-registry").glob("*.json"))}

    if not bsps:
        print(f"No BSPs found under {ROOT / 'conf/machine'} — broken checkout?")
        return 1

    print_table(bsps, kas_machines, bitbake_registries)

    for bsp in sorted(bsps):
        if bsp not in kas_machines:
            problems.append(f"conf/machine/{bsp}.conf has no kas/*.yml with 'machine: {bsp}'")
        for path, machines in bitbake_registries.items():
            if bsp not in machines:
                problems.append(f"conf/machine/{bsp}.conf missing from {path.relative_to(ROOT)}")

    for machine, files in sorted(kas_machines.items()):
        if machine not in bsps and machine not in NOT_A_BSP_REVERSE:
            problems.append(f"{', '.join(files)} sets machine '{machine}', which has no conf/machine/{machine}.conf")

    for path, machines in bitbake_registries.items():
        for machine in sorted(machines):
            if machine not in bsps and machine not in NOT_A_BSP_REVERSE:
                problems.append(f"{path.relative_to(ROOT)} lists machine '{machine}', which has no conf/machine/{machine}.conf")

    if problems:
        print("BSP coverage check failed:\n")
        for p in problems:
            print(f"  - {p}")
        print(f"\n{len(problems)} problem(s).")
        return 1

    print(f"OK: {len(bsps)} BSPs all have kas + bitbake-registry coverage.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
