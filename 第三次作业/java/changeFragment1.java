package com.example.myproject3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link changeFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class changeFragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int number1;

    int number2;

    TextView tvNumber;

    Button result;



    public changeFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment changeFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static changeFragment1 newInstance(String param1, String param2) {
        changeFragment1 fragment = new changeFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        result= view.findViewById(R.id.calculate);
        tvNumber= view.findViewById(R.id.tv_numbers);

        // 从Bundle获取数据
        getDataFromActivity();

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndReturnResult();
            }
        });
    }

    private void getDataFromActivity() {
        Bundle bundle= getArguments();
        if (bundle != null) {
            number1 = bundle.getInt("number1", 0);
            number2 = bundle.getInt("number2", 0);

            String numbersText = "从Activity接收到的数字:\n" +
                    "数字1: " + number1 + "\n" +
                    "数字2: " + number2;
            tvNumber.setText(numbersText);
        }
    }

    private void calculateAndReturnResult() {
        int result = number1 + number2;

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).onCalculationResult(result);
        }
    }
}
