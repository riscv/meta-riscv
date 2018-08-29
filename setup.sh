#!/bin/bash
# Bootstrapper for buildbot slave

DIR="build"
MACHINE="qemuriscv64"
CONFFILE="conf/auto.conf"
BITBAKEIMAGE="core-image-full-cmdline"

# clean up the output dir
echo "Cleaning build dir"
rm -rf $DIR

# make sure sstate is there
#echo "Creating sstate directory"
#mkdir -p ~/sstate/$MACHINE

# fix permissions set by buildbot
echo "Fixing permissions for buildbot"
umask -S u=rwx,g=rx,o=rx
chmod -R 755 .

# bootstrap OE
echo "Init OE"
export BASH_SOURCE="poky/oe-init-build-env"
. ./openembedded-core/oe-init-build-env $DIR

# Symlink the cache
#echo "Setup symlink for sstate"
#ln -s ~/sstate/${MACHINE} sstate-cache

# add the missing layers
echo "Adding layers"
bitbake-layers add-layer ../meta-openembedded/meta-oe
bitbake-layers add-layer ../meta-openembedded/meta-multimedia
bitbake-layers add-layer ../meta-openembedded/meta-python
bitbake-layers add-layer ../meta-openembedded/meta-networking
bitbake-layers add-layer ../meta-riscv

# fix the configuration
echo "Creating auto.conf"

if [ -e $CONFFILE ]; then
    rm -rf $CONFFILE
fi
cat <<EOF > $CONFFILE
MACHINE = "${MACHINE}"
#IMAGE_FEATURES += "tools-debug"
#IMAGE_FEATURES += "tools-tweaks"
#IMAGE_FEATURES += "dbg-pkgs"
# rootfs for debugging
#IMAGE_GEN_DEBUGFS = "1"
#IMAGE_FSTYPES_DEBUGFS = "tar.gz"
# explicitly disable x11 and enable opengl
HOSTTOOLS_NONFATAL_append = " ssh"
EOF

echo "To build an image run"
echo "-------------------------------"
echo "bitbake core-image-full-cmdline"
echo "-------------------------------"

# start build
#echo "Starting build"
#bitbake $BITBAKEIMAGE

