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

SRC_URI += "\
      git:///home/mark/rz_linux-cip;protocol=file;branch=main"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
SRCREV= "3d7318dd71101fe511965fca8825bf96cba9cc03"

#SRC_URI += "\
#      git://github.com/Eldex-Mark/linux-cip.git;protocol=https;branch=main"
#LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
#SRCREV= "9c36bcca9ff61bcdd54af041dbbfdced72b33846"

LINUX_VERSION = "5.10.201"

#SRC_URI += "\
#      git://github.com/Eldex-Mark/myir-renesas-linux.git;protocol=https;branch=master"
#LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
#SRCREV= "382af73d63ac3954d86cf5673bff5eda30974dcf"

#LINUX_VERSION = "5.10.83"

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
