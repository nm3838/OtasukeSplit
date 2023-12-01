package com.example.otasukesplit;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class InputDataFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //このフラグメントが所属するアクティビティオブジェクトを取得
        Activity inputDataParentActivity = getActivity();
        //フラグメントを表示する画面をXMLファイルからインフレートする
        View view = inflater.inflate(R.layout.fragment_input_data, container, false);
        //画面レイアウトllInputDataを取得
        LinearLayout llInputData = view.findViewById(R.id.llInputData);

        return view;
    }
}