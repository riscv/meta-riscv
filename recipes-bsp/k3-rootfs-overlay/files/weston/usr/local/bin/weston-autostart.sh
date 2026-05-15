#!/bin/sh
# Auto-detect which DRM card has a display connected.
for card in /sys/class/drm/card[1-9]*; do
    if grep -ql "^connected" "$card"-*/status 2>/dev/null; then
        card_name=$(basename "$card")
        exec /usr/bin/weston --modules=systemd-notify.so --drm-device="$card_name"
    fi
done
