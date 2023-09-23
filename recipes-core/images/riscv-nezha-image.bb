DESCRIPTION = "Basic image for nezha machine"

require recipes-core/images/core-image-minimal.bb

# Use 'haveged' instead 'rng-tools' due to 'SIGSEGV' error during start 'rngd'
PACKAGE_EXCLUDE:append = "rng-tools"

IMAGE_INSTALL:append = "haveged"

IMAGE_FEATURES += " \
    ssh-server-openssh \
    debug-tweaks \
"