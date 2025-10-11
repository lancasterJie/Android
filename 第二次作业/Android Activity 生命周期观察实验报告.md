# Android Activity 生命周期观察实验报告

## 一、实验目的



1. 掌握 Android Activity 生命周期的基本概念

2. 通过 Log 观察 Activity 在不同场景下的生命周期变化

3. 理解 Activity 跳转和返回时的生命周期调用顺序

4. 分析普通 Activity 与 Dialog Activity 在生命周期上的差异

## 二、实验步骤与结果记录

### 第一部分：基础生命周期观察

#### 1. 启动应用观察

Logcat 输出记录：oncreate-onstart-onresume



![](https://p3-flow-imagex-sign.byteimg.com/ocean-cloud-tos/pages_upload_image_3fa25923-1965-4a70-8a3f-51bfebb1f861.png~tplv-a9rns2rl98-image-qvalue.png?rcl=20251012004930B2F7D1317916750468DD\&rk3s=8e244e95\&rrcfp=121e9355\&x-expires=1791823770\&x-signature=uQC2PZ%2BZkjsENAFqho8VqJaOv%2FU%3D)

**&#x20;                                                图 1 启动应用时 Logcat 输出**

#### 2. 普通 Activity 跳转

##### （1）从 MainActivity 跳转到 SecondActivity



* MainActivity 生命周期调用顺序：onpause-onstop

* SecondActivity 生命周期调用顺序：oncreate-onstart-onresume



![](https://p3-flow-imagex-sign.byteimg.com/ocean-cloud-tos/pages_upload_image_f749d65d-1ee7-4926-8149-12e85d787d54.png~tplv-a9rns2rl98-image-qvalue.png?rcl=202510120050371667A23E5DA49E46DC34\&rk3s=8e244e95\&rrcfp=121e9355\&x-expires=1791823837\&x-signature=T%2B7IY2gjRNaOkIDMrneWL%2F5tGKk%3D)

**图 2 跳转 SecondActivity 时初始 Log 输出**



![](https://p3-flow-imagex-sign.byteimg.com/ocean-cloud-tos/pages_upload_image_d0a58bcb-5070-4130-8d33-aefa09a4998d.png~tplv-a9rns2rl98-image-qvalue.png?rcl=20251012005134D501DF995256263933FF\&rk3s=8e244e95\&rrcfp=121e9355\&x-expires=1791823894\&x-signature=rDCyfZkz6r5j2n%2BwszFrSciWTfI%3D)

**图 3 MainActivity 进入停止状态 Log 输出**

##### （2）从 SecondActivity 返回 MainActivity



* MainActivity 生命周期调用顺序：onrestart-onstart-onresume

* SecondActivity 生命周期调用顺序：onpause-onstop



![](https://p3-flow-imagex-sign.byteimg.com/ocean-cloud-tos/pages_upload_image_da9b6027-652c-45bc-9079-4cf620d4afbe.png~tplv-a9rns2rl98-image-qvalue.png?rcl=20251012005157DA8F7BFCEB023C14A35B\&rk3s=8e244e95\&rrcfp=121e9355\&x-expires=1791823918\&x-signature=oH%2BfPjMQbnGHpejfkOhJw1O1Wz8%3D)

**图 4 返回 MainActivity 时初始 Log 输出**



![](https://p3-flow-imagex-sign.byteimg.com/ocean-cloud-tos/pages_upload_image_e27bb74b-dc3d-4fe3-ad68-60bebf38bcbd.png~tplv-a9rns2rl98-image-qvalue.png?rcl=202510120052181C671B2B293786D6904B\&rk3s=8e244e95\&rrcfp=121e9355\&x-expires=1791823938\&x-signature=GGI3TIhe20XV9LJH2541mbuSsps%3D)

**图 5 SecondActivity 销毁 Log 输出**

#### 3. Dialog Activity 跳转

##### （1）从 MainActivity 跳转到 Dialog Activity



* MainActivity 生命周期调用顺序：onpause-onstop

* DialogActivity 生命周期调用顺序：oncreate-onstart-onresume



![](https://p3-flow-imagex-sign.byteimg.com/ocean-cloud-tos/pages_upload_image_c2c01a49-48bc-47fe-b0e6-ca33f7774a21.png~tplv-a9rns2rl98-image-qvalue.png?rcl=202510120052447B5476413C88F90D77AA\&rk3s=8e244e95\&rrcfp=121e9355\&x-expires=1791823964\&x-signature=Nnwb43kSeMJ4L4sjM0KPqTaDBG8%3D)

**图 6 跳转 DialogActivity 时初始 Log 输出**



![](https://p3-flow-imagex-sign.byteimg.com/ocean-cloud-tos/pages_upload_image_8f29f7f2-634e-4a4c-9f69-c8fbae4e7ce6.png~tplv-a9rns2rl98-image-qvalue.png?rcl=202510120053041CBCF8F9545087431B46\&rk3s=8e244e95\&rrcfp=121e9355\&x-expires=1791823984\&x-signature=EPjpcDv9r3ILQxXDsiCx8TWips0%3D)

**图 7 MainActivity 进入停止状态 Log 输出**

##### （2）从 Dialog Activity 返回 MainActivity



* MainActivity 生命周期调用顺序：onrestart-onstart-onresume

* DialogAcitivity 生命周期调用顺序：onpause-onstop-ondestroy



![](https://p3-flow-imagex-sign.byteimg.com/ocean-cloud-tos/pages_upload_image_451d94f8-1a84-4d27-a009-8aea6e506c93.png~tplv-a9rns2rl98-image-qvalue.png?rcl=20251012005327B2F7D13179167504C62F\&rk3s=8e244e95\&rrcfp=121e9355\&x-expires=1791824008\&x-signature=xX4pQp39e%2BD4Bmtzom6tOX688ls%3D)

**图 8 返回 MainActivity 时初始 Log 输出**



![](https://p3-flow-imagex-sign.byteimg.com/ocean-cloud-tos/pages_upload_image_49a56f62-af90-4e4a-ba3d-446b0fb7f623.png~tplv-a9rns2rl98-image-qvalue.png?rcl=202510120053497B5476413C88F90D8DC6\&rk3s=8e244e95\&rrcfp=121e9355\&x-expires=1791824029\&x-signature=SIMHGtQViq0Hcz3hDVnqOpcXCoE%3D)

**图 9 DialogActivity 销毁 Log 输出**

### 第二部分：数据记录与分析

请根据实际 Log 输出填写下表：



| 场景                     | MainActivity 生命周期顺序        | 目标 Activity 生命周期顺序        |
| ---------------------- | -------------------------- | ------------------------- |
| 应用启动                   | oncreate-onstart-onresume  | 无                         |
| Main → SecondActivity  | onpause-onstop             | oncreate-onstart-onresume |
| SecondActivity 返回      | onrestart-onstart-onresume | onpause-onstop            |
| Main → Dialog Activity | onpause-onstop             | oncreate-onstart-onresume |
| Dialog Activity 返回     | onrestart-onstart-onresume | onpause-onstop-ondestroy  |

> （注：文档部分内容可能由 AI 生成）