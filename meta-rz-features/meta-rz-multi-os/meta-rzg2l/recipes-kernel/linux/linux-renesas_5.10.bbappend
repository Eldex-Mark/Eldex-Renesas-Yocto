FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

ENABLE_REMOTEPROC = "1"

# List of kernel patches
SRC_URI += " \
    file://0001-Add-rzg2l-smarc-multi-os-dtsi-for-supporting-OpenAMP.patch \
    file://0002-arm64-dts-renesas-rzg2l-smarc-Disable-OSTM2.patch \
    file://0003-Add-rzg2lc-smarc-multi-os-dtsi-for-supporting-OpenAM.patch \
    file://0004-Add-rzg2ul-smarc-multi-os-dtsi-for-supporting-OpenAM.patch \
    file://0005-arm64-dts-renesas-rzg2lc-smarc-Disable-SCIF1-OSTM2.patch \
    file://0006-clk-renesas-r9a07g044-Set-SCIF1-SCIF2-OSTM2.patch \
    file://0007-arm64-dts-renesas-rzg2ul-smarc-Disable-OSTM2.patch \
    file://0008-clk-renesas-r9a07g043-Set-OSTM2.patch \
"

SRC_URI += "${@'file://0009-clk-renesas-r9a07g044-Add-I2-clock.patch \
    file://0010-clk-renesas-r9a07g043-Add-I2-clock.patch \
    file://0011-clk-renesas-r9a07g044-Add-CM33-clock-and-reset-signal.patch \
    file://0012-clk-renesas-r9a07g043-Add-CM33-clock-and-reset-signal.patch \
    file://0013-arm64-dts-renesas-rzg2l-smarc-Add-rproc-node.patch \
    file://0014-arm64-dts-renesas-rzg2lc-smarc-Add-rproc-node.patch \
    file://0015-arm64-dts-renesas-rzg2ul-smarc-Add-rproc-node.patch \
    file://0016-Add-bindings-for-RZ-remoteproc-driver.patch \
    file://0017-Add-RZ-remoteproc-driver.patch' if '${ENABLE_REMOTEPROC}' == '1' else ''}"

# Kernel configuration update
SRC_URI += "file://uio.cfg"
SRC_URI += "${@'file://remoteproc.cfg' if '${ENABLE_REMOTEPROC}' == '1' else ''}"
