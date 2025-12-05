# Android 作业：高级 ListView 待办事项管理应用

## 作业目标

本次作业要求学生掌握以下内容：

- 使用 ListView 展示复杂数据
- 自定义 Adapter（包含多种 ViewType）
- 使用 convertView + ViewHolder 进行性能优化
- 实现搜索过滤（Filterable）
- 支持列表点击与长按事件
- 实现数据的本地持久化（SharedPreferences / 文件 / SQLite）

---

## 1. 主界面（MainActivity）

主界面包含以下组件：

- EditText：用于搜索任务
- Button：“Add Task” 跳转到添加任务界面
- ListView：显示任务列表

布局结构示例：

```xml
<LinearLayout>
    <EditText id="etSearch" />
    <Button id="btnAdd" text="Add Task" />
    <ListView id="listView" />
</LinearLayout>


public class TaskAdapter extends BaseAdapter implements Filterable {

    private List<Task> originalData;
    private List<Task> filteredData;
    private LayoutInflater inflater;

    public TaskAdapter(Context ctx, List<Task> data) {
        this.originalData = data;
        this.filteredData = data;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() { return filteredData.size(); }

    @Override
    public Task getItem(int position) { return filteredData.get(position); }

    @Override
    public int getViewTypeCount() { return 2; }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isImportant ? 1 : 0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        // 学生自行实现：含 ViewHolder、convertView、多布局支持
        return convertView;
    }

    @Override
    public Filter getFilter() {
        // 学生自行实现过滤逻辑
        return null;
    }
}
