SUMMARY = "A prebuilt Desktop Base image as baseline for custom work"
require ubuntu-license.inc
SECTION = "devel"

# Desktop 20.04.1 baseline
SRC_URI[md5sum] = "6c4cf15a389d6ae485d92322c97e875a"
SRC_URI[sha256sum] = "269709ecd5c506f229f10983f990c394278b202ca29f089844e2e5c2b80ad8b4"

require ubuntu-base.inc

# There are some basic differences between different Desktop versions.
# We try not to address them in the generic recipe
APTGET_EXTRA_PACKAGES += ""

APTGET_EXTRA_PACKAGES_RECONFIGURABLE += " \
    gnome-terminal \
    gnome-shell \
    gdm3 \
    adwaita-icon-theme \
    totem libtotem0 \
"

# Desktop 20 unifies things and turns some things into symlinks. We
# solve this with Yocto "usrmerge" but that isn't quite enough.
# We still need to ship the symlinks.
# We also need to remove the udev reference as apparently bitbake.conf
# isn't quite adapted to usrmerge there.
FILES_${PN}_remove = "/lib/udev"
FILES_${PN} += "/bin"
FILES_${PN} += "/sbin"

# The downside of not having the symlink destination content is that we
# are missing a few basic files that are must have for dependencies.
RPROVIDES_${PN}_ubuntu += " \
    /bin/sh \
    /bin/bash \
    /bin/dash \
"

# Extra packages that Desktop will replace and thus enables the use of 
# Yocto packages such as gstreamer to be used in Desktop without package conflitcs
YOCTO-DEPENDS-LIST = " glib-2.0 libglib-2.0-0 libglib-2.0-utils python3-core python3-dev \
                      python3-distutils python3-pickle python3-xml \
                      update-alternatives-opkg pam-plugin-unix \
                      libpam-runtime shadow-base shadow dbus polkit \
                      systemd systemd-dev iso-codes-dev \ 
                      shared-mime-info-dev bluez5 \ 
                      python3-stringold python3-numbers python3-numbers \
                      python3-shell python3-pprint python3-logging \
                      python3-datetime python3-difflib python3-typing \
                      python3-debugger python3-audio python3-codecs \
                      python3-mime python3-mmap python3-threading \
                      python3-ctypes python3-math python3-crypt \
                      python3-email python3-io python3-netclient \
                      python3-asyncio python3-unittest python3-pydoc \
                      python3-misc python3-doctest python3-multiprocessing \
                      python3-compression python3-html python3-netserver \
                      libtirpc3 python3-compile python3-json python3-unixadmin \
                      python3-plistlib python3-xmlrpc \
                     attr avahi-daemon base-files base-passwd bash-completion consolekit \
                     cracklib cryptodev-linux curl flex-libfl gobject-introspection icu \
                     lame libatomic-ops libatomic1 libattr1 libavahi-client3 libavahi-common3 \
                     libavahi-core7 libavahi-glib1 libavahi-gobject0 libblkid1 \
                     libcairo-gobject2 libcairo-script-interpreter2 libcairo2 libcap-ng0 \
                     libcap2 libcrypt2 libdaemon0 libdbus-1-3 libdbus-glib-1-2 \
                     libdrm-radeon libdrm-nouveau libdrm-omap \
                     libdrm-intel libdrm-exynos libdrm-freedreno libdrm-amdgpu libdrm-etnaviv \
                     libexpat1 libfdisk1 libffi7 libflac libflac++6 libflac8 libfontconfig1 \
                     libfreetype6 libfribidi0 libgcc1 libgdk-pixbuf-2.0-0 libgdk-pixbuf-xlib-2.0-0 \
                     libgmp10 libgmpxx4 libgnutls-openssl27 libgnutls30 libgnutlsxx28 libgudev-1.0-0 \
                     libharfbuzz0 libical libice6 libicudata64 libicui18n64 libicuio64 libicutu64 \
                     libicuuc64 libidn2-0 libltdl7 liblzma5 libmount1 libmp3lame0 \
                     libnss-db2 libogg0 liborc-0.4-0 liborc-test-0.4-0 libpam libpciaccess0 \
                     libpcre1 libpcrecpp0 libpcreposix0 libpixman-1-0 libpng16-16 libpsl5 \
                     libreadline8 libsbc1 libsm6 libsmartcols1 libsoup-2.4 libspeex1 \
                     libspeexdsp1 libsqlite3-0 libstdc++6 libsystemd0 libtag-c0 libtag1 \
                     libtheora libticw5 libtinfo5 libtool libturbojpeg0 libudev1 \
                     libunistring2 libusb-1.0-0 libuuid1 libvorbis libpulse0 \
                     libpulse-mainloop-glib0 libpulse-simple0 pulseaudio-dev \
                     libx11-6 libx11-xcb1 libxau6 libxcb-composite0 libxcb-damage0 \
                     libxcb-dpms0 libxcb-dri2-0 libxcb-dri3-0 libxcb-glx0 libxcb-present0 \
                     libxcb-randr0 libxcb-record0 libxcb-render0 libxcb-res0 libxcb-screensaver0 \
                     libxcb-shape0 libxcb-shm0 libxcb-sync1 libxcb-xf86dri0 libxcb-xfixes0 \
                     libxcb-xinerama0 libxcb-xinput0 libxcb-xkb1 libxcb-xtest0 libxcb-xv0 \
                     libxcb-xvmc0 libxcb1 libxdamage1 libxdmcp6 libxext6 libxfixes3 libxft2 \
                     libxi6 libxml2 libxrender1 libxtst6 libxv1 libz1 \
                     mozjs mpg123 nettle nspr orc linux-libc-headers-dev pango pulseaudio \
                     python3-pycairo python3-pkgutil python3-dbus python3-pygobject \
                     systemd-gpuconfig valgrind wayland attr-dev avahi-dev \
                     base-files-dev base-passwd-dev bash-completion-dev \
                     bluez5-dev bzip2-dev ca-certificates consolekit-dev \
                     cracklib-dev cryptodev-linux-dev curl-devflac-dev \
                     flex-dev glib-networking gobject-introspection-dev \
                      icu-dev lame-dev libatomic-dev libatomic-ops-dev \
                      libc6-dbg libc6-dev libcap-dbg libcap-dev libcap-ng-dev \
                      libcrypt-dbg libcrypt-dev \
                      libdaemon-dev libdbus-glib-1-dev libexpat-dbg libexpat-dev \
                      libffi-dbg libffi-dev libfontconfig-dbg libfontconfig-dev \
                      libfreetype-dbg libfreetype-dev libfribidi-dbg libfribidi-dev \
                      libgcc-s-dbg libgcc-s-dev libgdk-pixbuf-2.0-dbg libgdk-pixbuf-2.0-dev \
                      libglib-2.0-dbg libglib-2.0-dev libgmp-dbg libgmp-dev libgnutls-dev \
                      libgudev-1.0-dbg libgudev-1.0-dev libical-dev libice-dev libidn2-dbg libidn2-dev \
                      libjpeg-dbg libjpeg-dev libmp3lame-dev libnss-mdns libogg-dbg libogg-dev libpam-dev \
                      libpciaccess-dev libpcre-dbg libpcre-dev libpixman-1-dbg libpixman-1-dev \
                      libpng16-dbg libpng16-dev libpsl-dbg libpsl-dev libpthread-stubs-dev libreadline-dev \
                      libsbc-dbg libsbc-dev libsm-dev libsoup-2.4-dbg libsoup-2.4-dev \
                      libspeex-dbg libspeex-dev libspeexdsp-dev libsqlite3-dev libstdc++-dev \
                      libtag-dbg libtag-dev libtheora-dbg libtheora-dev libtool-dev \
                      libunistring-dbg libunistring-dev libusb-1.0-dbg libusb-1.0-dev \
                      libvorbis-dbg libvorbis-dev libx11-dbg libx11-dev \
                      libxau-dbg libxau-dev libxcb-dbg libxcb-dev libxdamage-dbg libxdamage-dev \
                      libxdmcp-dbg libxdmcp-dev libxext-dbg libxext-dev libxfixes-dbg libxfixes-dev \
                      libxft-dbg libxft-dev libxi-dev libxml2-dbg libxml2-dev libxrender-dbg libxrender-dev \
                      libxtst-dev libxv-dbg libxv-dev libz-dbg libz-dev mozjs-dev mpg123-dbg mpg123-dev \
                      ncurses-dev nettle-dbg nettle-dev nspr-dev openssl-dev orc-dev polkit-dev \
                      python3-dbus-dev python3-pycairo-dev python3-pygobject-dev shadow-dev \
                      util-linux-dev util-macros-dev valgrind-dev wayland-dbg wayland-dev \
                      wireless-tools xcb-proto-dev xorgproto-dev xtrans-dev \
"

RCONFLICTS_${PN} += " ${YOCTO-DEPENDS-LIST} "
RREPLACES_${PN} += " ${YOCTO-DEPENDS-LIST} "
RPROVIDES_${PN} += " ${YOCTO-DEPENDS-LIST} "

RPROVIDES_${PN}_ubuntu += " libglib-2.0 "
