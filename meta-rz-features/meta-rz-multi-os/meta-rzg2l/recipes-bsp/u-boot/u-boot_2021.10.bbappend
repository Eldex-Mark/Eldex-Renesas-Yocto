FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://0001-disable-reserved-area-check.patch \
    file://0002-cmd-Add-cm33-command.patch \
    file://0003-configs-smarc-rzg2l_defconfig-Enable-CM33-command.patch \
    file://0004-configs-smarc-rzg2lc_defconfig-Enable-CM33-command.patch \
    file://0005-configs-smarc-rzg2ul_defconfig-Enable-CM33-command.patch \
"
