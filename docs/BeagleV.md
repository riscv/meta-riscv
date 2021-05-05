BeagleV
=======

BeagleV is a low-cost RISC-V 64-bit based platform. The beta revision of the
board uses the Starfive JH7100 SoC.

How to build
============

```
$ SHELL=/bin/bash kas-container build ./meta-riscv/beaglev.yml
```

The `kas-container` script can be obtained from
[kas repository](https://github.com/siemens/kas/blob/2.4/kas-container).

Build results
=============

- core-image-minimal-beaglev-starlight-jh7100.wic.gz
- core-image-minimal-beaglev-starlight-jh7100.wic.bmap
- fw_payload.bin
- fw_payload.elf
- extlinux.conf
- Image
- starfive_vic7100_beagle_v.dtb
- u-boot.bin

Flashing
========

* Flash `core-image-minimal-beaglev-starlight-jh7100.wic.gz` onto uSD card:

```
$ sudo bmaptool copy --bmap core-image-minimal-beaglev-starlight-jh7100.wic.bmap core-image-minimal-beaglev-starlight-jh7100.wic.gz /dev/sdx
```

* Flash U-Boot:

```
$ perl -e 'print pack("l", (stat @ARGV[0])[7])' fw_payload.bin > fw_payload.bin.out
$ cat fw_payload.bin >> fw_payload.bin.out
```

* Interrupt the boot process and send the `fw_payload.bin.out`. Details can be
  found [here](https://github.com/tpetazzoni/buildroot/blob/beaglev/board/beaglev/readme.txt#L60)

```

```

Testing
=======

* The root partition is sometimes not found on time - `rootdelay=3` parameter
  was added to the cmdline

* The booting fails in following way currently:

```
on
bootloader version:210209-4547a8d
ddr 0x00000000, 1M test
ddr 0x00100000, 2M test
DDR clk 2133M,Version: 210302-5aea32f
0 crc flash: e1d5ffc3, crc ddr: e1d5ffc3
crc check PASSED

bootloader.

OpenSBI v0.9
   ____                    _____ ____ _____
  / __ \                  / ____|  _ \_   _|
 | |  | |_ __   ___ _ __ | (___ | |_) || |
 | |  | | '_ \ / _ \ '_ \ \___ \|  _ < | |
 | |__| | |_) |  __/ | | |____) | |_) || |_
  \____/| .__/ \___|_| |_|_____/|____/_____|
        | |
        |_|

Platform Name             : StarFive VIC7100
Platform Features         : timer,mfdeleg
Platform HART Count       : 2
Firmware Base             : 0x80000000
Firmware Size             : 92 KB
Runtime SBI Version       : 0.3

Domain0 Name              : root
Domain0 Boot HART         : 1
Domain0 HARTs             : 0*,1*
Domain0 Region00          : 0x0000000080000000-0x000000008001ffff ()
Domain0 Region01          : 0x0000000000000000-0x0000007fffffffff (R,W,X)
Domain0 Next Address      : 0x0000000080200000
Domain0 Next Arg1         : 0x0000000088000000
Domain0 Next Mode         : S-mode
Domain0 SysReset          : yes

Boot HART ID              : 1
Boot HART Domain          : root
Boot HART ISA             : rv64imafdcsux
Boot HART Features        : scounteren,mcounteren
Boot HART PMP Count       : 16
Boot HART PMP Granularity : 4096
Boot HART PMP Address Bits: 36
Boot HART MHPM Count      : 0
Boot HART MHPM Count      : 0
Boot HART MIDELEG         : 0x0000000000000222
Boot HART MEDELEG         : 0x000000000000b109


U-Boot 2021.04 (Apr 27 2021 - 19:25:33 +0000)

CPU:   rv64imafdc
DRAM:  8 GiB
MMC:   sdio0@10000000: 0, sdio1@10010000: 1
Net:   dwmac.10020000
Autoboot in 2 seconds
MMC CD is 0x0, force to True.
MMC CD is 0x0, force to True.
switch to partitions #0, OK
mmc0 is current device
Scanning mmc 0:1...
Found /boot/extlinux/extlinux.conf
Retrieving file: /boot/extlinux/extlinux.conf
269 bytes read in 11 ms (23.4 KiB/s)
1:	Poky (Yocto Project Reference Distro)
Retrieving file: /boot/extlinux/../../Image
13402624 bytes read in 2811 ms (4.5 MiB/s)
append: root=/dev/mmcblk0p2 earlyprintk rootwait rootdelay=3 panic=30 console=ttyS0,115200
Retrieving file: /boot/extlinux/../../starfive_vic7100_beagle_v.dtb
17419 bytes read in 15 ms (1.1 MiB/s)
Moving Image from 0x84000000 to 0x80200000, end=80f1b000
## Flattened Device Tree blob at 90000000
   Booting using the fdt blob at 0x90000000
   Using Device Tree in place at 0000000090000000, end 000000009000740a

Starting kernel ...

[    0.000000] Linux version 5.10.6-yocto-standard-starfive (oe-user@oe-host) (riscv64-poky-linux-gcc (GCC) 10.2.0, GNU ld (GNU Binutils) 2.36.1.20210209) #1 SMP Tue Apr 27 18:28:08 UTC 2021
[    0.000000] OF: fdt: Ignoring memory range 0x80000000 - 0x80200000
[    0.000000] efi: UEFI not found.
[    0.000000] Initial ramdisk at: 0x(____ptrval____) (99614720 bytes)
[    0.000000] OF: reserved mem: node linux,cma compatible matching fail
[    0.000000] Reserved memory: created DMA memory pool at 0x00000000f9000000, size 16 MiB
[    0.000000] OF: reserved mem: initialized node framebuffer@f9000000, compatible id shared-dma-pool
[    0.000000] Reserved memory: created DMA memory pool at 0x00000000fb000000, size 32 MiB
[    0.000000] OF: reserved mem: initialized node framebuffer@fb000000, compatible id shared-dma-pool
[    0.000000] Zone ranges:
[    0.000000]   DMA32    [mem 0x0000000080200000-0x00000000ffffffff]
[    0.000000]   Normal   [mem 0x0000000100000000-0x000000027fffffff]
[    0.000000] Movable zone start for each node
[    0.000000] Early memory node ranges
[    0.000000]   node   0: [mem 0x0000000080200000-0x00000000f8ffffff]
[    0.000000]   node   0: [mem 0x00000000fa000000-0x00000000faffffff]
[    0.000000]   node   0: [mem 0x00000000fd000000-0x000000027fffffff]
[    0.000000] Initmem setup node 0 [mem 0x0000000080200000-0x000000027fffffff]
[    0.000000] software IO TLB: mapped [mem 0x00000000c5000000-0x00000000c9000000] (64MB)
[    0.000000] SBI specification v0.3 detected
[    0.000000] SBI implementation ID=0x1 Version=0x9
[    0.000000] SBI v0.2 TIME extension detected
[    0.000000] SBI v0.2 IPI extension detected
[    0.000000] SBI v0.2 RFENCE extension detected
[    0.000000] SBI v0.2 HSM extension detected
[    0.000000] riscv: ISA extensions acdfim
[    0.000000] riscv: ELF capabilities acdfim
[    0.000000] percpu: Embedded 17 pages/cpu s30952 r8192 d30488 u69632
[    0.000000] Built 1 zonelists, mobility grouping on.  Total pages: 2055687
[    0.000000] Kernel command line: root=/dev/mmcblk0p2 earlyprintk rootwait rootdelay=3 panic=30 console=ttyS0,115200
[    0.000000] Dentry cache hash table entries: 1048576 (order: 11, 8388608 bytes, linear)
[    0.000000] Inode-cache hash table entries: 524288 (order: 10, 4194304 bytes, linear)
[    0.000000] Sorting __ex_table...
[    0.000000] mem auto-init: stack:off, heap alloc:off, heap free:off
[    0.000000] Memory: 7312644K/8337408K available (4485K kernel code, 4441K rwdata, 2048K rodata, 222K init, 327K bss, 1024764K reserved, 0K cma-reserved)
[    0.000000] Virtual kernel memory layout:
[    0.000000]       fixmap : 0xffffffcefee00000 - 0xffffffceff000000   (2048 kB)
[    0.000000]       pci io : 0xffffffceff000000 - 0xffffffcf00000000   (  16 MB)
[    0.000000]      vmemmap : 0xffffffcf00000000 - 0xffffffcfffffffff   (4095 MB)
[    0.000000]      vmalloc : 0xffffffd000000000 - 0xffffffdfffffffff   (65535 MB)
[    0.000000]       lowmem : 0xffffffe000000000 - 0xffffffe1ffe00000   (8190 MB)
[    0.000000] SLUB: HWalign=64, Order=0-3, MinObjects=0, CPUs=2, Nodes=1
[    0.000000] rcu: Hierarchical RCU implementation.
[    0.000000] rcu: 	RCU debug extended QS entry/exit.
[    0.000000] 	Tracing variant of Tasks RCU enabled.
[    0.000000] rcu: RCU calculated value of scheduler-enlistment delay is 10 jiffies.
[    0.000000] NR_IRQS: 64, nr_irqs: 64, preallocated irqs: 0
[    0.000000] riscv-intc: 64 local interrupts mapped
[    0.000000] plic: disable L2 cache irq 128 in plic
[    0.000000] plic: disable L2 cache irq 131 in plic
[    0.000000] plic: disable L2 cache irq 129 in plic
[    0.000000] plic: disable L2 cache irq 130 in plic
[    0.000000] plic: disable L2 cache irq 128 in plic
[    0.000000] plic: disable L2 cache irq 131 in plic
[    0.000000] plic: disable L2 cache irq 129 in plic
[    0.000000] plic: disable L2 cache irq 130 in plic
[    0.000000] plic: plic@c000000: mapped 127 interrupts with 2 handlers for 4 contexts.
[    0.000000] random: get_random_bytes called from start_kernel+0x582/0x700 with crng_init=0
[    0.000000] riscv_timer_init_dt: Registering clocksource cpuid [0] hartid [1]
[    0.000000] clocksource: riscv_clocksource: mask: 0xffffffffffffffff max_cycles: 0x171024e6b, max_idle_ns: 440795202301 ns
[    0.000008] sched_clock: 64 bits at 6MHz, resolution 160ns, wraps every 4398046511040ns
[    0.000298] Console: colour dummy device 80x25
[    0.000399] Calibrating delay loop (skipped), value calculated using timer frequency.. 12.50 BogoMIPS (lpj=62500)
[    0.000429] pid_max: default: 32768 minimum: 301
[    0.001214] Mount-cache hash table entries: 16384 (order: 5, 131072 bytes, linear)
[    0.001794] Mountpoint-cache hash table entries: 16384 (order: 5, 131072 bytes, linear)
[    0.006056] rcu: Hierarchical SRCU implementation.
[    0.006718] EFI services will not be available.
[    0.007648] smp: Bringing up secondary CPUs ...
[    0.009501] smp: Brought up 1 node, 2 CPUs
[    0.013022] devtmpfs: initialized
[    0.019471] clocksource: jiffies: mask: 0xffffffff max_cycles: 0xffffffff, max_idle_ns: 19112604462750000 ns
[    0.019525] futex hash table entries: 512 (order: 3, 32768 bytes, linear)
[    0.020931] NET: Registered protocol family 16
[    0.035960] OF: /soc/i2c@118b0000: could not find phandle
[    0.036107] OF: /soc/i2c@118c0000: could not find phandle
[    0.036206] OF: /soc/i2c@12450000: could not find phandle
[    0.039497] OF: /soc/i2c@118b0000: could not find phandle
[    0.039770] OF: /soc/i2c@118c0000: could not find phandle
[    0.040034] OF: /soc/i2c@12450000: could not find phandle
[    0.052227] HugeTLB registered 2.00 MiB page size, pre-allocated 0 pages
[    0.055991] starfive_gpio 11910000.gpio: SiFive GPIO chip registered 64 GPIOs
[    0.056968] SCSI subsystem initialized
[    0.057719] OF: /soc/i2c@118b0000: could not find phandle
[    0.057757] i2c_designware 118b0000.i2c: Using 'clocks' : 49500000 / 1000
[    0.057796] i2c_designware 118b0000.i2c: Using 'clocks' : 49500000 / 1000
[    0.057814] i2c_designware 118b0000.i2c: Using 'clocks' : 49500000 / 1000
[    0.058000] i2c_designware 118b0000.i2c: running with gpio recovery mode! scl,sda
[    0.058958] OF: /soc/i2c@118c0000: could not find phandle
[    0.058998] i2c_designware 118c0000.i2c: Using 'clocks' : 49500000 / 1000
[    0.059034] i2c_designware 118c0000.i2c: Using 'clocks' : 49500000 / 1000
[    0.059051] i2c_designware 118c0000.i2c: Using 'clocks' : 49500000 / 1000
[    0.059268] i2c_designware 118c0000.i2c: running with gpio recovery mode! scl,sda
[    0.059885] OF: /soc/i2c@12450000: could not find phandle
[    0.059922] i2c_designware 12450000.i2c: Using 'clocks' : 50000000 / 1000
[    0.059959] i2c_designware 12450000.i2c: Using 'clocks' : 50000000 / 1000
[    0.059976] i2c_designware 12450000.i2c: Using 'clocks' : 50000000 / 1000
[    0.060149] i2c_designware 12450000.i2c: running with gpio recovery mode! scl,sda
[    0.062181] clocksource: Switched to clocksource riscv_clocksource
[    0.083032] NET: Registered protocol family 2
[    0.084254] tcp_listen_portaddr_hash hash table entries: 4096 (order: 5, 163840 bytes, linear)
[    0.085093] TCP established hash table entries: 65536 (order: 7, 524288 bytes, linear)
[    0.087789] TCP bind hash table entries: 65536 (order: 9, 2097152 bytes, linear)
[    0.097890] TCP: Hash tables configured (established 65536 bind 65536)
[    0.098717] UDP hash table entries: 4096 (order: 6, 393216 bytes, linear)
[    0.100613] UDP-Lite hash table entries: 4096 (order: 6, 393216 bytes, linear)
[    0.102895] NET: Registered protocol family 1
[    0.104820] RPC: Registered named UNIX socket transport module.
[    0.104849] RPC: Registered udp transport module.
[    0.104859] RPC: Registered tcp transport module.
[    0.104868] RPC: Registered tcp NFSv4.1 backchannel transport module.
[    0.105252] Unpacking initramfs...
[    0.105294] Initramfs unpacking failed: invalid magic at start of compressed archive
[    0.544718] Freeing initrd memory: 97280K
[    0.546542] workingset: timestamp_bits=62 max_order=21 bucket_order=0
[    0.559388] NFS: Registering the id_resolver key type
[    0.559453] Key type id_resolver registered
[    0.559465] Key type id_legacy registered
[    0.559672] nfs4filelayout_init: NFSv4 File Layout Driver Registering...
[    0.559743] ntfs: driver 2.1.32 [Flags: R/W].
[    0.561207] NET: Registered protocol family 38
[    0.561318] Block layer SCSI generic (bsg) driver version 0.4 loaded (major 251)
[    0.561337] io scheduler mq-deadline registered
[    0.561348] io scheduler kyber registered
[    0.573734] dw_axi_dmac_platform 100b0000.sgdma2p: DesignWare AXI DMA Controller, 4 channels
[    0.575761] dw_axi_dmac_platform 10500000.sgdma1p: DesignWare AXI DMA Controller, 16 channels
[    0.576606] L2CACHE: No. of Banks in the cache: 2
[    0.576630] L2CACHE: No. of ways per bank: 16
[    0.576640] L2CACHE: Sets per bank: 1024
[    0.576648] L2CACHE: Bytes per cache block: 64
[    0.576657] L2CACHE: Index of the largest way enabled: 0
[    0.712085] Serial: 8250/16550 driver, 4 ports, IRQ sharing disabled
[    0.715331] printk: console [ttyS0] disabled
[    0.715470] 12440000.serial: ttyS0 at MMIO 0x12440000 (irq = 6, base_baud = 6250000) is a 16550A
[    1.533275] printk: console [ttyS0] enabled
[    1.539845] dw-apb-uart 11870000.hs_serial: detected caps 00000000 should be 00000100
[    1.547802] 11870000.hs_serial: ttyS1 at MMIO 0x11870000 (irq = 7, base_baud = 4640625) is a 16550
[    1.560581] vic-rng 118d0000.trng: Initialized
[    1.589110] loop: module loaded
[    1.593919] libphy: Fixed MDIO Bus: probed
[    1.599333] stmmaceth 10020000.gmac: IRQ eth_lpi not found
[    1.605112] stmmaceth 10020000.gmac: force_sf_dma_mode is ignored if force_thresh_dma_mode is set.
[    1.614180] stmmaceth 10020000.gmac: PTP uses main clock
[    1.619492] stmmaceth 10020000.gmac: no reset control found
[    1.625734] stmmaceth 10020000.gmac: User ID: 0x59, Synopsys ID: 0x37
[    1.632262] stmmaceth 10020000.gmac: 	DWMAC1000
[    1.636796] stmmaceth 10020000.gmac: DMA HW capability register supported
[    1.643603] stmmaceth 10020000.gmac: RX Checksum Offload Engine supported
[    1.650371] stmmaceth 10020000.gmac: COE Type 2
[    1.654919] stmmaceth 10020000.gmac: Wake-Up On Lan supported
[    1.660649] stmmaceth 10020000.gmac: Enhanced/Alternate descriptors
[    1.666938] stmmaceth 10020000.gmac: Enabled extended descriptors
[    1.673044] stmmaceth 10020000.gmac: Ring mode enabled
[    1.678171] stmmaceth 10020000.gmac: Enable RX Mitigation via HW Watchdog Timer
[    1.685499] stmmaceth 10020000.gmac: device MAC address 2c:f7:f1:1b:e3:77
[    1.702599] libphy: stmmac: probed
[    1.706035] mdio_bus stmmac-0:07: attached PHY driver [unbound] (mii_bus:phy_addr=stmmac-0:07, irq=POLL)
[    1.718565] sdhci: Secure Digital Host Controller Interface driver
[    1.724869] sdhci: Copyright(c) Pierre Ossman
[    1.729292] Synopsys Designware Multimedia Card Interface Driver
[    1.735703] sdhci-pltfm: SDHCI platform and OF driver helper
[    1.736268] dw_mmc 10000000.sdio0: IDMAC supports 32-bit address mode.
[    1.743263] NET: Registered protocol family 10
[    1.748083] dw_mmc 10000000.sdio0: Using internal DMA controller.
[    1.758441] dw_mmc 10000000.sdio0: Version ID is 290a
[    1.763248] Segment Routing with IPv6
[    1.763673] dw_mmc 10000000.sdio0: DW MMC controller at irq 30,32 bit host data width,32 deep fifo
[    1.767286] sit: IPv6, IPv4 and MPLS over IPv4 tunneling driver
[    1.776305] mmc_host mmc0: card is polling.
[    1.783228] NET: Registered protocol family 17
[    1.787025] dw_mmc 10010000.sdio1: IDMAC supports 32-bit address mode.
[    1.791007] 9pnet: Installing 9P2000 support
[    1.797248] dw_mmc 10010000.sdio1: Using internal DMA controller.
[    1.801625] Key type dns_resolver registered
[    1.807623] dw_mmc 10010000.sdio1: Version ID is 290a
[    1.812283] debug_vm_pgtable: [debug_vm_pgtable         ]: Validating architecture page table helpers
[    1.817013] dw_mmc 10010000.sdio1: DW MMC controller at irq 31,32 bit host data width,32 deep fifo
[    1.835260] mmc_host mmc1: card is non-removable.
[    1.840084] dw-apb-uart 12440000.serial: forbid DMA for kernel console
[    1.846820] Waiting 3 sec before mounting root device...
[    2.002239] mmc_host mmc0: Bus speed (slot 0) = 100000000Hz (slot req 400000Hz, actual 400000HZ div = 125)
[    2.052209] mmc_host mmc1: Bus speed (slot 0) = 100000000Hz (slot req 400000Hz, actual 400000HZ div = 125)
[    2.254564] mmc_host mmc0: Bus speed (slot 0) = 100000000Hz (slot req 10000000Hz, actual 10000000HZ div = 5)
[    2.264633] mmc0: new high speed SDHC card at address 0007
[    2.273838] mmcblk0: mmc0:0007 SL16G 14.5 GiB
[    2.300377] mmc1: queuing unknown CIS tuple 0x80 (2 bytes)
[    2.306427] GPT:Primary header thinks Alt. header is not at the end of the disk.
[    2.313893] GPT:3004859 != 30318591
[    2.317375] GPT:Alternate GPT header not at the end of the disk.
[    2.323405] GPT:3004859 != 30318591
[    2.326886] GPT: Use GNU Parted to correct GPT errors.
[    2.332167]  mmcblk0: p1 p2
[    2.337899] mmc1: queuing unknown CIS tuple 0x80 (3 bytes)
[    2.344796] mmc1: queuing unknown CIS tuple 0x80 (3 bytes)
[    2.352584] mmc1: queuing unknown CIS tuple 0x80 (7 bytes)
[    2.360859] mmc1: queuing unknown CIS tuple 0x81 (9 bytes)
[    2.397586] random: fast init done
[    2.440343] mmc_host mmc1: Bus speed (slot 0) = 100000000Hz (slot req 10000000Hz, actual 10000000HZ div = 5)
[    2.455028] mmc1: new high speed SDIO card at address 0001
[    4.914753] EXT4-fs (mmcblk0p2): mounted filesystem with ordered data mode. Opts: (null)
[    4.923024] VFS: Mounted root (ext4 filesystem) readonly on device 179:2.
[    4.932239] devtmpfs: mounted
[    4.936174] Freeing unused kernel memory: 220K
[    4.941000] Run /sbin/init as init process
INIT: version 2.99 booting
Starting udev
[    5.953613] udevd[82]: starting version 3.2.10
[    5.961108] random: udevd: uninitialized urandom read (16 bytes read)
[    5.971850] random: udevd: uninitialized urandom read (16 bytes read)
[    5.978524] random: udevd: uninitialized urandom read (16 bytes read)
[    6.059771] udevd[83]: starting eudev-3.2.10
[    6.576695] EXT4-fs (mmcblk0p2): re-mounted. Opts: (null)
hwclock: can't open '/dev/misc/rtc': No such file or directory
Fri Mar  9 12:34:56 UTC 2018
hwclock: can't open '/dev/misc/rtc': No such file or directory
[   11.179204] urandom_read: 2 callbacks suppressed
[   11.179220] random: dd: uninitialized urandom read (512 bytes read)
[   11.234746] init[1]: unhandled signal 11 code 0x1 at 0x0000000000000030 in libc-2.33.so[3ff1bcd000+fd000]
[   11.244383] CPU: 1 PID: 1 Comm: init Not tainted 5.10.6-yocto-standard-starfive #1
[   11.251930] epc: 0000003ff1c4956e ra : 0000003ff1c4affa sp : 0000003ffff313b0
[   11.259067]  gp : 0000002addd82800 tp : 0000003ff1cd9320 t0 : 0000000000000001
[   11.266313]  t1 : 000000000000016d t2 : 0000000000000000 s0 : 0000003ff1cd12d0
[   11.273563]  s1 : 0000002addd83690 a0 : 0000002addd83894 a1 : 0000000000001ffc
[   11.280761]  a2 : 0000003ff1cbc8d0 a3 : 0000000000000000 a4 : 0000000000000000
[   11.287980]  a5 : 0000003ffff314cf a6 : 0000003ff1cccc48 a7 : 0000000000000190
[   11.295199]  s2 : 0000000000001ffc s3 : 0000000000000001 s4 : 0000003ff1ccd124
[   11.302415]  s5 : 0000002addd7f410 s6 : 0000003ffff31608 s7 : 0000000000000002
[   11.309663]  s8 : 0000000000000010 s9 : 0000000000000002 s10: 0000002addd83894
[   11.316923]  s11: 0000002addd7fb30 t3 : ffffffbefedb4876 t4 : 0000000000000001
[   11.324155]  t5 : 0000000000000051 t6 : ffffffffffffff24
[   11.329453] status: 0000000200004020 badaddr: 0000000000000030 cause: 000000000000000d
[   11.337445] Kernel panic - not syncing: Attempted to kill init! exitcode=0x0000000b
[   11.345091] CPU: 1 PID: 1 Comm: init Not tainted 5.10.6-yocto-standard-starfive #1
[   11.352633] Call Trace:
[   11.355092] [<ffffffe000202332>] walk_stackframe+0x0/0x9c
[   11.360480] [<ffffffe000656ac8>] show_stack+0x34/0x3e
[   11.365524] [<ffffffe00065954e>] dump_stack+0x70/0x8e
[   11.370559] [<ffffffe000656d1c>] panic+0xfa/0x2b0
[   11.375257] [<ffffffe0002095f2>] do_exit+0x12c/0x62c
[   11.380206] [<ffffffe00020a54a>] do_group_exit+0x3a/0x7c
[   11.385508] [<ffffffe0002120f6>] get_signal+0x19c/0x4a8
[   11.390718] [<ffffffe000201bc4>] do_notify_resume+0x3e/0x2e0
[   11.396361] [<ffffffe000201142>] ret_from_exception+0x0/0xc
[   11.401914] SMP: stopping secondary CPUs
[   11.405854] Rebooting in 30 seconds..
```

Resources
=========

* [BeagleV wiki](https://wiki.seeedstudio.com/BeagleV-Getting-Started/)
* [Buildroot BeagleV README](https://github.com/tpetazzoni/buildroot/blob/beaglev/board/beaglev/readme.txt)
