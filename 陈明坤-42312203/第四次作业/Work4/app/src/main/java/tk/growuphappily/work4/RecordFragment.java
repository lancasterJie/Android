package tk.growuphappily.work4;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordFragment extends Fragment {

    private MyDbHelper.Value value;

    private MyDbHelper db;

    public RecordFragment() {
        // Required empty public constructor
    }

    public static RecordFragment newInstance(MyDbHelper.Value value) {
        RecordFragment fragment = new RecordFragment();
        fragment.setArguments(value.toBundle());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            value = MyDbHelper.Value.fromBundle(getArguments());
        }
        db = new MyDbHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_record, container, false);
        TextView t = root.findViewById(R.id.noteTitle);
        TextView c = root.findViewById(R.id.content);
        TextView d = root.findViewById(R.id.date);
        t.setText(value.title);
        c.setText(value.content);
        root.setOnClickListener((view) -> {
            var intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra("Data", value.toBundle());
            startActivity(intent);
        });
        d.setText(value.time);
        root.findViewById(R.id.delete).setOnClickListener((view) -> {
            db.delete(value.id);
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            db.close();
        });
        return root;
    }
}