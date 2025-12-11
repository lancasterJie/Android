package tk.growuphappily.work5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TaskItemArrayAdapter extends ArrayAdapter<TaskItem> {
    public TaskItemArrayAdapter(@NonNull Context context, @NonNull List<TaskItem> objects) {
        super(context, R.layout.list_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        var view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        TextView name = view.findViewById(R.id.taskName);
        TextView date = view.findViewById(R.id.dueDate);
        ImageView image = view.findViewById(R.id.star);
        var icon = view.findViewById(R.id.star);
        var item = getItem(position);
        if(item == null) {
            return view;
        }
        icon.setOnClickListener((v) -> {
            item.marked = !item.marked;
            MainActivity m = (MainActivity) getContext();
            m.adapter.notifyDataSetChanged();
        });
        name.setText(item.taskName);
        date.setText(item.dueDate);
        image.setImageResource(item.marked ? R.drawable.baseline_star_24 : R.drawable.baseline_star_outline_24);
        return view;
    }
}
