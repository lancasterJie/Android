## 功能要求

### 1. RadioGroup 控制 Fragment 切换
**要求：**
- 创建包含 4 个 RadioButton 的 RadioGroup
- 每个 RadioButton 对应一个不同的 Fragment
- 点击 RadioButton 时切换到对应的 Fragment
- 每个 Fragment 要有独特的UI和功能区分
- res/drawable/radio_button_selector.xml 定义背景选择器,定义radiobutton是否check的状态
- 在style中定义radiobutton统一样式



### 2. Bundle 数据传输
**要求实现三种场景：**

**场景 A: Activity → Activity**
从 MainActivity 传递用户数据到 DetailActivity，包含：用户名、年龄、是否学生等信息

**场景 B: Activity ↔ Fragment**
MainActivity 向 Fragment 传递初始数据，Fragment 向 Activity 返回处理结果

**场景 C: Fragment → Fragment**
在两个 Fragment 之间通过 Activity 中转传递数据

### 3. 屏幕旋转与状态保存 onSaveInstanceState
**要求：**
- log onSaveInstanceState的在生命周期的哪个阶段出现
- 在onSaveInstanceState中保存edittext中内容，并在屏幕旋转后实现在textview中进行展示
