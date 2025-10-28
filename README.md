## 功能要求

### 1. RadioGroup 控制 Fragment 切换
**要求：**
- 创建包含 4 个 RadioButton 的 RadioGroup
- 每个 RadioButton 对应一个不同的 Fragment
- 点击 RadioButton 时切换到对应的 Fragment
- 每个 Fragment 要有独特的UI和功能区分

**建议的 Fragment 设计：**
- **Fragment 1**: 个人信息表单（姓名、年龄等）
- **Fragment 2**: 数据传输演示界面
- **Fragment 3**: 生命周期日志显示
- **Fragment 4**: 设置或关于页面

### 2. Bundle 数据传输
**要求实现三种场景：**

**场景 A: Activity → Activity**
从 MainActivity 传递用户数据到 DetailActivity，包含：用户名、年龄、是否学生等信息

**场景 B: Activity ↔ Fragment**
MainActivity 向 Fragment 传递初始数据，Fragment 向 Activity 返回处理结果

**场景 C: Fragment → Fragment**
在两个 Fragment 之间通过 Activity 中转传递数据

### 3. 屏幕旋转与状态保存
**要求：**
- 在多个位置实现状态保存
- 添加详细的日志输出
- 验证数据是否正确恢复
