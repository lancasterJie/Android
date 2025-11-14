# Three Android Demo

基于 RadioGroup + Fragment 的 Tab 切换示例，覆盖三种 Bundle 数据传输场景，以及旋转时状态保存与恢复。

## 功能概述
- RadioGroup 控制 4 个 Fragment 切换，使用统一样式与背景选择器。
- Bundle 传输示例：
  - 场景 A：Activity → Activity（MainActivity → DetailActivity）
  - 场景 B：Activity ↔ Fragment（MainActivity 与 DiscoverFragment 双向）
  - 场景 C：Fragment → Fragment（DiscoverFragment → Activity 中转 → ProfileFragment）
- 屏幕旋转时通过 `onSaveInstanceState` 保存 EditText 文本，并恢复后展示到 TextView，同时打印日志。

## 运行环境
- Android Studio Giraffe+
- Gradle（项目内置 Wrapper）
- 最低要求：与模板一致（Material3 模板默认配置）


## 使用说明
1. 编译运行应用，默认进入“首页”。
2. 底部 4 个 Tab 切换 Fragment：
   - 首页 Home：点击“计数 +1”按钮自增。
   - 发现 Discover：
     - 显示来自 Activity 的初始数据。
     - “向 Activity 返回结果”会更新 MainActivity 顶部 TextView。
     - “经 Activity 中转发送给 Profile”，切到“我的”可看到消息。
   - 通知 Notify：开关切换时文案变化。
   - 我的 Profile：输入用户名/年龄/是否学生，点击“跳转 DetailActivity”，在新页面查看接收结果。
3. MainActivity 顶部“Main→Detail 传递数据”按钮，用内置示例数据跳转 `DetailActivity`。
4. 在主界面 EditText 输入任意文本，旋转屏幕后 TextView 显示保存内容；Logcat 可看到 `onSaveInstanceState` 日志（TAG: `MainActivity`）。

## 关键实现要点
### 1) RadioGroup + Fragment 切换
- `activity_main.xml` 中定义底部 `RadioGroup`，每个 `RadioButton` 使用统一样式 `Widget.App.RadioTab`。
- 选择器：`res/drawable/radio_button_selector.xml`，选中为紫色圆角，未选为浅灰圆角。
- 切换逻辑：`MainActivity` 中 `setOnCheckedChangeListener`，`replaceFragment` 切换到对应 Fragment。

### 2) 三种 Bundle 传输场景
- 场景 A：`MainActivity` 使用 `Intent.putExtra` 跳转 `DetailActivity`，后者读取并展示。
- 场景 B：
  - Activity → Fragment：在切换到 `DiscoverFragment` 时使用 `setArguments` 传入 `init_msg`。
  - Fragment → Activity：`DiscoverFragment` 通过 `FragmentResult` 返回 `result_from_fragment`，`MainActivity` 监听后更新 UI。
- 场景 C：`DiscoverFragment` 发送 `to_fragment_b`，`MainActivity` 转发为 `from_activity_to_b`，`ProfileFragment` 监听并展示。

### 3) 旋转与状态保存
- 在 `MainActivity.onSaveInstanceState` 保存 EditText 文本（key: `key_input_text`）。
- `onCreate` 中读取并将值展示到顶部 TextView，同时打印日志说明调用阶段。

