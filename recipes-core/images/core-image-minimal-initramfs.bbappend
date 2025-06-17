# Install RCPU firmware into the initramfs which is needed for OrangePi RV2
PACKAGE_INSTALL:append:orangepi-rv2 = " firmware-rcpu-orangepi-rv2"

# Add riscv64-oe-linux as a compatible hosts
COMPATIBLE_HOST = '(x86_64.*|i.86.*|arm.*|aarch64.*|riscv64.*)-(linux.*|freebsd.*)'
