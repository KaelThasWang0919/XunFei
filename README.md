# XunFei
在android Studio下运行 需要在配置文件中设置so包位置  或者将so文件放到jnilibs文件中


# Odoo_WMS系统说明文档


## 1.EMDK配置
### 0.官方ENDK文档 
[http://techdocs.zebra.com/emdk-for-android/4-0/guide/setupAndroidStudioMac/](
http://techdocs.zebra.com/emdk-for-android/4-0/guide/setupAndroidStudioMac/
)

EMDK sdk下载地址[http://techdocs.zebra.com/emdk-for-android/download/](
http://techdocs.zebra.com/emdk-for-android/download/)

### 1.Windows环境下 安装EMDK.exe,在app.gradle中添加
``provided fileTree(include: ['com.symbol.emdk.jar'], dir: '(根据本地sdk路径配置)sdk/add-ons/addon-symbol-emdk_v4.0_API-16/libs')
``
### 2.Mac环境下 下载EMDK-****-MAC.zip解压
+  将解压后的SDK文件放到sdk下的add-ons下
+  在app.gradle下添加
``provided fileTree(include: ['com.symbol.emdk.jar'], dir: '(根据本地sdk路径配置)sdk/add-ons/addon-symbol-emdk_v4.0_API-16/libs')
``
    
