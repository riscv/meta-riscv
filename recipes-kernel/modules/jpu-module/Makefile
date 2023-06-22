# SPDX-FileCopyrightText: 2023 Andreas Cord-Landwehr <cordlandwehr@kde.org>
# SPDX-License-Identifier: MIT

obj-m := jpu.o

SRC := $(shell pwd)

all:
	$(MAKE) -C $(KERNEL_SRC) M=$(SRC) modules

modules_install:
	$(MAKE) -C $(KERNEL_SRC) M=$(SRC) modules_install

clean:
	rm -f *.o *~ core .depend .*.cmd *.ko *.mod.c
	rm -f Module.markers Module.symvers modules.order
	rm -rf .tmp_versions Modules.symvers
