DESCRIPTION = "Linux kernel for the RZG2 based board"

require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/:"
COMPATIBLE_MACHINE_rzg2l = "(smarc-rzg2l|rzg2l-dev|smarc-rzg2lc|rzg2lc-dev|smarc-rzg2ul|rzg2ul-dev|smarc-rzv2l|rzv2l-dev)"
COMPATIBLE_MACHINE_rzg2h = "(ek874|hihope-rzg2n|hihope-rzg2m|hihope-rzg2h)"
COMPATIBLE_MACHINE_rzfive = "(smarc-rzfive|rzfive-dev)"
COMPATIBLE_MACHINE_rzv2m = "(rzv2m)"
COMPATIBLE_MACHINE_rzv2ma = "(rzv2ma)"
COMPATIBLE_MACHINE_rzg1 = "(iwg20m-g1m|iwg20m-g1n|iwg21m|iwg22m|iwg23s)"
COMPATIBLE_MACHINE_rzg3s = "(rzg3s-dev|smarc-rzg3s)"

#KERNEL_URL = " \
#    git://github.com/renesas-rz/rz_linux-cip.git"
#BRANCH = "${@oe.utils.conditional("IS_RT_BSP", "1", "rz-5.10-cip41-rt17", "rz-5.10-cip41",d)}"
#SRCREV = "${@oe.utils.conditional("IS_RT_BSP", "1", "13d833431c8f69b225e3bb94cb72b06774fd47c0", "0030c60827fe590e8dbdfe58a9844328b9e28b9a",d)}"

#SRC_URI = "${KERNEL_URL};protocol=https;nocheckout=1;branch=${BRANCH}"
#LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
#LINUX_VERSION ?= "${@oe.utils.conditional("IS_RT_BSP", "1", "5.10.201-cip41-rt17", "5.10.201-cip41",d)}"

#SRC_URI += "\
#      git://github.com/Eldex-Mark/rz_linux-cip-rz-5.10-cip41-rt17.git;protocol=https;nocheckout=1;branch=main"
#SRCREV= "b4aeb5713716ad1943189ab744f0454e1714d0bf"

# Compile local version
SRC_URI += "\
      git:///home/mark/rz_linux-cip-rz-5.10-cip41-rt17/;protocol=file;branch=main"
LIC_FILES_CHKSUM = "file://COPYING;md5=b4aeb5713716ad1943189ab744f0454e1714d0bf"
SRCREV= "b4aeb5713716ad1943189ab744f0454e1714d0bf"

LINUX_VERSION = "5.10.201"

PV = "${LINUX_VERSION}+git${SRCPV}"
PR = "r1"

SRC_URI_append = "\
  file://touch.cfg \
"

KBUILD_DEFCONFIG = "defconfig"
KBUILD_DEFCONFIG_rzfive = "renesas_defconfig"
KCONFIG_MODE = "alldefconfig"

do_kernel_metadata_af_patch() {
	# need to recall do_kernel_metadata after do_patch for some patches applied to defconfig
	rm -f ${WORKDIR}/defconfig
	do_kernel_metadata
}

do_deploy_append() {
	for dtbf in ${KERNEL_DEVICETREE}; do
		dtb=`normalize_dtb "$dtbf"`
		dtb_ext=${dtb##*.}
		dtb_base_name=`basename $dtb .$dtb_ext`
		for type in ${KERNEL_IMAGETYPE_FOR_MAKE}; do
			ln -sf $dtb_base_name-${KERNEL_DTB_NAME}.$dtb_ext $deployDir/$type-$dtb_base_name.$dtb_ext
		done
	done
}

addtask do_kernel_metadata_af_patch after do_patch before do_kernel_configme

# Fix race condition, which can causes configs in defconfig file be ignored
do_kernel_configme[depends] += "virtual/${TARGET_PREFIX}binutils:do_populate_sysroot"
do_kernel_configme[depends] += "virtual/${TARGET_PREFIX}gcc:do_populate_sysroot"
do_kernel_configme[depends] += "bc-native:do_populate_sysroot bison-native:do_populate_sysroot"

# Fix error: openssl/bio.h: No such file or directory
DEPENDS += "openssl-native"
