# meta-riscv Quick Start

**Note: You only need this if you do not have an existing Yocto Project build environment.**

There are three approaches to get started:

- **Path A: `bitbake-setup`** — Initialize a build environment using a JSON configuration file.
Requires a pre-installed `bitbake` checkout.
- **Path B: `repo`** — Initialize a build environment using Google's `repo` tool with a manifest file.
- **Path C: `kas`** — Build directly using the [kas](https://github.com/siemens/kas) tool.
No prior `bitbake` or `repo` setup required.

Paths A and B both produce a standard `bitbake` build environment. Once initialized,
the build and deployment steps are identical ([Build Images](#build-images),
[Running in QEMU](#running-in-qemu), [Running on hardware](#running-on-hardware)).
Path C handles initialization and build in one command.

---

## Path A: bitbake-setup

The following examples assume that you have the `bitbake` repository checked
out at `~/workspace/yocto/bitbake`, and that you are using the `nodistro`
variant. They read the config templates from URLs to minimize how much needs to
be preemptively checked out on the host system. Alternatively, you could clone
this repository and use the following to achieve the same result:

```bash
../bitbake-setup init /path/to/meta-riscv/bitbake-regsitry/your-preferred.conf.json
```

### Interactively

1. `~/workspace/yocto/bitbake/bin/bitbake-setup init https://raw.githubusercontent.com/riscv/meta-riscv/refs/heads/master/bitbake-registry/meta-riscv-oe-nodistro-master.conf.json`
2. Provide your choice of MACHINE
3. Set the workspace name
4. Confirm workspace setup

bitbake-setup should clone the minimal repository set and provide you with
further usage instructions.

### Non-Interactively

Using `MACHINE="orangepi-rv2"` as an example:

```bash
bitbake-setup init --non-interactive https://raw.githubusercontent.com/riscv/meta-riscv/refs/heads/master/bitbake-registry/meta-riscv-oe-nodistro-master.conf.json nodistro machine/orangepi-r2s
```

---

## Path B: repo

Make sure to [install the `repo` command by Google](https://gerrit.googlesource.com/git-repo#install) first.

### Create workspace

```bash
mkdir riscv-yocto && cd riscv-yocto
repo init -u https://github.com/riscv/meta-riscv -b master -m tools/manifests/riscv-yocto.xml
repo sync
repo start work --all
```

### Set up the build environment with `envsetup.sh`

```bash
. layers/meta-riscv/tools/envsetup.sh
```

Optionally override the build directory:

```bash
BUILD_DIR=<build-riscv> . ./layers/meta-riscv/tools/envsetup.sh
```

### Update existing workspace

In order to bring all the layers up to date with upstream

```bash
cd riscv-yocto
repo sync
repo rebase
```

---

## Path C: kas

```bash
git clone https://github.com/riscv/meta-riscv.git -b master
cd meta-riscv
```

- For basic `qemuriscv64` build run:

```bash
kas build kas/base-riscv.yml
```

**base-riscv.yml** will build `core-image-minimal`, you can then boot it with:

```bash
runqemu core-image-minimal nographic
```

**NOTE** `nographic` is needed for this image, because it has no graphical support for graphical Qemu run.

- For `nezha` build:

```bash
kas build kas/nezha.yml
```

- For `beaglev` build:

```bash
kas build kas/beaglev.yml
```

- For more machines check `kas` folder.

---

## Custom Project

If you have your own layer that depends on this layer, you can create a kas `yml` file in your layer with the following content (`nezha` build as an example):

```yml
header:
  version: 20
  includes:
    - repo: meta-riscv
      file: kas/nezha.yml

repos:
  meta-riscv:
    url: https://github.com/riscv/meta-riscv.git
    path: layers/meta-riscv
    branch: scarthgap

target: custom-image # Or nezha default image: riscv-nezha-image
```

For more details on `nezha`, `beaglev` and other boards steps check `doc` folder.

---

## Build Images

After completing [Path A](#path-a-bitbake-setup) or [Path B](#path-b-repo), use `bitbake` to build images for your target machine.

A console-only image for the 64-bit QEMU machine

```bash
MACHINE=qemuriscv64 bitbake core-image-full-cmdline
MACHINE=beaglev-starlight-jh7100 bitbake core-image-full-cmdline
```

To build an image to run on the HiFive Unleashed using Wayland run the following

```bash
MACHINE=freedom-u540 bitbake core-image-weston
```

To build an image to run on the BeagleV using Wayland run the following

```bash
MACHINE=beaglev-starlight-jh7100 bitbake core-image-weston
```

To build an image to run on the MangoPi MQ Pro (console only has been tested so far) run the following:

```bash
MACHINE=mangopi-mq-pro bitbake core-image-base
```

To build a full GUI equipped image running Plasma Mobile see the in-tree documentation [here](https://github.com/riscv/meta-riscv/blob/master/docs/Plasma-Mobile-on-Unleashed.md).

## Running in QEMU

Run the 64-bit machine in QEMU using the following command:

```bash
MACHINE=qemuriscv64 runqemu nographic
```

Run the 32-bit machine in QEMU using the following command:

```bash
MACHINE=qemuriscv32 runqemu nographic
```

## Running on hardware

### Setting up a TFTP server

If you would like to boot the images from a TFTP server (optional) you should set your TFTP server address in your local.conf with the following line. Change ```127.0.0.1``` to the IP address of your TFTP server and copy the uImage to the server.

```bash
TFTP_SERVER_IP = "127.0.0.1"
```

### Running with the Microsemi Expansion board

To use the Microsemi expansion board with your HiFive Unleased add the following line to your local.conf. This tells the Unleashed to use a device tree with the PCIe device described:

```bash
RISCV_SBI_FDT:freedom-u540 = "hifive-unleashed-a00-microsemi.dtb"
```

### Sparse Image Creation

The output of the build can also be written to an SD card using bmaptool, the steps to do this are below:

```bash
MACHINE=freedom-u540 wic create freedom-u540-opensbi -e core-image-minimal
bmaptool create ./freedom-u540-opensbi-201812181337-mmcblk.direct > image.bmap
sudo bmaptool copy --bmap image.bmap ./freedom-u540-opensbi-201812181337-mmcblk.direct /dev/sdX
```

### dding wic.gz

The output of a ```freedom-u540```, ```beaglev-starlight-jh7100``` or ```mangopi-mq-pro```  build will be a ```<image>.wic.gz``` file. You can write this file to an sd card using:

```bash
zcat <image>-<machine>.wic.gz | sudo dd of=/dev/sdX bs=4M iflag=fullblock oflag=direct conv=fsync status=progress
```

### Using bmaptoop to write the image

Instead of dding wic.gz image ```bmaptool``` (available in most Linux distributions and/or pip) can be used for more reliable and faster flashing. You can write this file to an sd card using:

```bash
sudo bmaptool copy <image>-<machine>.wic.gz /dev/sdX
```
