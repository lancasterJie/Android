package tk.growuphappily.work5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModalBottomSheet extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        var view = inflater.inflate(R.layout.bottom_sheet, container, false);
        TextInputEditText name = view.findViewById(R.id.nameField);
        TextInputEditText date = view.findViewById(R.id.dueDateField);
        Button button = view.findViewById(R.id.addButton);
        date.setOnTouchListener((v, e) -> {
            if(e.getAction() != MotionEvent.ACTION_DOWN) {
                return false;
            }
            var picker = MaterialDatePicker.Builder.datePicker().setTitleText("选择截止日期").build();
            picker.addOnPositiveButtonClickListener((d) -> {
                var due = new Date(d);
                var format = new SimpleDateFormat("yyyy-MM-dd");
                date.setText(format.format(due));
            });
            picker.show(getParentFragmentManager(), "date_picker");
            return true;
        });
        button.setOnClickListener((v) -> {
            var activity = (MainActivity) getActivity();
            activity.items.add(new TaskItem(name.getText().toString(), date.getText().toString()));
            activity.adapter.notifyDataSetChanged();
            ModalBottomSheet.this.dismiss();
        });
        return view;
    }
}
