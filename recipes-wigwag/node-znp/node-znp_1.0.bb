DESCRIPTION = "node module for TIs Zigbee Network Protocol HA profile"

LICENSE = "DEVICEPROTOCOL-1"
LICENSE_FLAGS = "WigWagCommericalDeviceProtocol"
LIC_FILES_CHKSUM = "file://README.md;md5=f4961c535d1089eb3dcaef5e56c61ad5"

inherit pkgconfig gitpkgv

PV = "1.0+git${SRCPV}"
PKGV = "1.0+git${GITPKGV}"
PR = "r1"

SRCREV = "${AUTOREV}"
SRC_URI="git://git@github.com/WigWagCo/node-znp.git;protocol=ssh"

S = "${WORKDIR}/git"

BBCLASSEXTEND = "native"

INHIBIT_PACKAGE_STRIP = "1"  

FILES_${PN} = "/wigwag/devicejs/core/utils/node-znp"

# do_package_qa () {
#   echo "done"
# }

do_compile() {
    cd ${S}

    export ARCH=`echo $AR | awk -F '-' '{print $1}'`
    export PLATFORM=`echo $AR | awk -F '-' '{print $3}'`
    export npm_config_arch=$ARCH
    export GYPFLAGS="-Dv8_can_use_fpu_instructions=false -Darm_version=7 -Darm_float_abi=hardfp"
    NGYP_OPTIONS="--without-snapshot --dest-cpu=arm --dest-os=linux --with-arm-float-abi=hardfp"
    CONFIG_OPTIONS="--host=arm-poky-linux-gnueabihf --target=arm-poky-linux-gnueabihf"

    npm install nan --production
    node-gyp configure
    node-gyp build
    npm install --production

}
do_package_qa() {
   echo "done"
}


do_install() {
    install -d ${D}/wigwag
    install -d ${D}/wigwag/devicejs
    install -d ${D}/wigwag/devicejs/core
    install -d ${D}/wigwag/devicejs/core/utils
    install -d ${D}/wigwag/devicejs/core/utils/node-znp
    cp -r ${S}/* ${D}/wigwag/devicejs/core/utils/node-znp

}

