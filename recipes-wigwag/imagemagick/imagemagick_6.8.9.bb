SUMMARY = "ImageMagick is an image convertion tools"
SECTION = "console/utils"
LICENSE = "ImageMagick"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5d84c6ddd4028aa53d028b4c50f9361e"
# FIXME: There are many more checked libraries. All should be added or explicitly disabled to get consistent results.
DEPENDS = "lcms bzip2 jpeg libpng librsvg tiff zlib virtual/fftw freetype"

PATCHSET = "10"
SRC_URI = "https://code.wigwag.com/libs/ImageMagick-${PV}-${PATCHSET}.tar.xz \
"
SRC_URI[md5sum] = "d6f23aed68b766d421843a03ae038273"
SRC_URI[sha256sum] = "cac5dda165ab603a2f56110daf41811233d93c6fdd84e94a8430e057041d8e68"

S = "${WORKDIR}/ImageMagick-${PV}-${PATCHSET}"

inherit autotools pkgconfig

# xml disabled because it's using xml2-config --prefix to determine prefix which returns just /usr with our libxml2
# if someone needs xml support then fix it first
EXTRA_OECONF = "--program-prefix= --program-suffix= --without-x --without-perl --disable-openmp --without-xml --disable-opencl"

CACHED_CONFIGUREVARS = "ac_cv_sys_file_offset_bits=yes"
PACKAGECONFIG ??= ""
PACKAGECONFIG[jp2] = "--with-jp2,,jasper"
PACKAGECONFIG[lzma] = "--with-lzma,--without-lzma,xz"
PACKAGECONFIG[pango] = "--with-pango,--without-pango,pango cairo"
PACKAGECONFIG[webp] = "--with-webp,--without-webp,libwebp"
PACKAGECONFIG[wmf] = "--with-wmf,--without-wmf,libwmf"

FILES_${PN} += "${libdir}/ImageMagick-${PV}/modules-Q16/*/*.so \
                ${libdir}/ImageMagick-${PV}/modules-Q16/*/*.la \
                ${libdir}/ImageMagick-${PV}/modules-Q16/filters \
                ${libdir}/ImageMagick-${PV}/modules-Q16/coders \
                ${libdir}/ImageMagick-${PV}/config-Q16 \
                ${datadir}/ImageMagick-6 "

FILES_${PN}-dev += "${libdir}/ImageMagick-${PV}/modules-Q16/*/*.a"

FILES_${PN}-dbg += "${libdir}/ImageMagick-${PV}/modules-Q16/*/.debug/*"

BBCLASSEXTEND = "native"
