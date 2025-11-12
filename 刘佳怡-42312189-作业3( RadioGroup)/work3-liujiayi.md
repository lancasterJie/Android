# 作业3要求
 ![radio_button_selector](images3/radio_button_selector.png)
## 1. RadioGroup 控制 Fragment 切换

- 创建包含 4 个 `RadioButton` 的 `RadioGroup`
- 每个 `RadioButton` 对应一个不同的 `Fragment`
- 点击 `RadioButton` 时切换到对应的 `Fragment`
- 每个 `Fragment` 要有独特的 UI 和功能区分
- 在 `res/drawable/radio_button_selector.xml` 中定义背景选择器，用于定义 `RadioButton` 是否被选中（checked）的状态
- 在 `style` 中统一定义 `RadioButton` 的样式

## 2. Bundle 数据传输

要求实现以下三种场景：

### 场景 A: Activity → Activity

- 从 `MainActivity` 传递用户数据到 `DetailActivity`
- 传递的数据包括：
  - 用户名
  - 年龄
  - 是否学生等信息

### 场景 B: Activity ↔ Fragment

- `MainActivity` 向 `Fragment` 传递初始数据
- `Fragment` 向 `MainActivity` 返回处理结果

### 场景 C: Fragment → Fragment

- 在两个 `Fragment` 之间通过 `Activity` 中转传递数据

## 3. 屏幕旋转与状态保存 (`onSaveInstanceState`)

- 要求记录 `onSaveInstanceState` 在生命周期中的调用阶段（可通过 Log 输出观察）
- 在 `onSaveInstanceState` 中保存 `EditText` 中的内容
- 屏幕旋转后，在 `TextView` 中展示之前保存的内容
