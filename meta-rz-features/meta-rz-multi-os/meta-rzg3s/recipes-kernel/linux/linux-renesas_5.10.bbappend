FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

ENABLE_REMOTEPROC = "0"

# List of kernel patches
SRC_URI += " \
	file://0001-Set-SCIF1-and-OSTM1-OSTM2-as-critical-clock.patch \
	file://0002-Disabled-OSTM1-OSTM2-for-the-use-on-the-target-core.patch \
	file://0003-Add-rzg3s-smarc-multi-os-dtsi-for-supporting-OpenAMP.patch \
"

SRC_URI += "${@'file://0004-clk-renesas-r9a08g045-Add-I2-I3-clock.patch \
	file://0005-clk-renesas-r9a08g045-Add-CM33-CM33_FPU-clock-and-re.patch \
	file://0006-arm64-dts-renesas-r9a08g045-Add-rproc-node-for-CM33-.patch \
	file://0007-Add-bindings-for-RZ-remoteproc-driver.patch \
	file://0008-remoteproc-Add-RZ-remoteproc-driver.patch' if '${ENABLE_REMOTEPROC}' == '1' else ''}"

# Kernel confguration update
SRC_URI += "file://uio.cfg"
SRC_URI += "${@'file://remoteproc.cfg' if '${ENABLE_REMOTEPROC}' == '1' else ''}"
