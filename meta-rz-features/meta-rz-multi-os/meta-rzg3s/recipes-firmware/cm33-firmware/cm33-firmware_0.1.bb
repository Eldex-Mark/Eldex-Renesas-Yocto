SUMMARY = "CM33 RPMsg firmware"
LICENSE = "CLOSED" 

FIRMWARE_NAME = "*.elf" 

EXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = " \
    file://${FIRMWARE_NAME} \
" 

INSANE_SKIP_${PN} = "arch" 

do_install() {
    install -d ${D}/lib/firmware
    install -m 0644 ${WORKDIR}/${FIRMWARE_NAME} ${D}/lib/firmware/
} 

FILES_${PN} += " \
    /lib/firmware/${FIRMWARE_NAME} \
"
