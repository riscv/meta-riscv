# Install RCPU firmware into the initramfs which is needed for K1-based OrangePis (RV2 and R2S)
PACKAGE_INSTALL:append:k1 = " firmware-rcpu-orangepi-k1"

# Add riscv64-oe-linux as a compatible hosts
COMPATIBLE_HOST = '(x86_64.*|i.86.*|arm.*|aarch64.*|riscv64.*)-(linux.*|freebsd.*)'
