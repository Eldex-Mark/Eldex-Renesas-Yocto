# meta-rz-multi-os

This is a Multi-OS feature that provides support for the RZ/G2L Group of 64bit Arm-based MPUs and RZ/G3S of 64bit Arm-based MPU from Renesas Electronics. Currently the following boards and MPUs are supported:

- Board: RZG2L SMARC Evaluation Kit / MPU: R9A07G044L (RZ/G2L)
- Board: RZG2LC SMARC Evaluation Kit / MPU: R9A07G044C (RZ/G2LC)
- Board: RZG2UL SMARC Evaluation Kit / MPU: R9A07G043U (RZ/G2UL)
- Board: RZG3S SMARC Evaluation Kit / MPU: R9A08G045 (RZ/G3S)

## Build Instructions

Assume that $WORK is the current working directory.

Download proprietary Multi-OS package from Renesas.
To download Multi-OS package, please use the following link:

    English: https://www.renesas.com/us/en/software-tool/rzg-multi-os-package
    Japanese: https://www.renesas.com/jp/ja/software-tool/rzg-multi-os-package

Please choose correct package that matches with your MPU.
After downloading the proprietary package, please decompress them then put meta-rz-features folder at $WORK.

- To apply Multi-OS feature, please run the command below:
```bash
   $ bitbake-layers add-layer ../meta-rz-features/meta-rz-multi-os/meta-<platform>
```
\<platform\> can be selected in below table:

|Renesas MPU| platform |
|:---------:|:--------:|
|RZ/G2L     |rzg2l     |
|RZ/G2LC    |rzg2l     |
|RZ/G2UL    |rzg2l     |
|RZ/G3S     |rzg3s     |