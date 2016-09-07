package com.singsound.singsounddemo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.singsound.singsounddemo.activity.Sentence_NativeActivity;
import com.singsound.singsounddemo.activity.Word_NativeActivity;
import com.singsound.singsounddemo.adapter.GridViewAdapter;
import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.activity.Word_OnlineCloudActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NativeFragment extends Fragment {

    GridView gview;
    GridViewAdapter mAdapter;

    private static final String NATIVE = "NATIVE";

    // 图片封装为一个数组
    private int[] icon = {R.mipmap.word,
            R.mipmap.sentence};
    //title
    private String[] iconName = {"单词跟读", "句子跟读"};
    private List<Map<String, Object>> data_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_native, container, false);
        gview = (GridView) view.findViewById(R.id.gview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initdata();
        mAdapter = new GridViewAdapter(getActivity(), data_list);
        gview.setAdapter(mAdapter);
        OnItemClick();
    }

    private void OnItemClick() {
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map map_position = (Map) gview.getAdapter().getItem(i);
                switch (i) {
                    case 0:
                        startActivity(getActivity(), Word_NativeActivity.class, iconName[0]);
                        break;
                    case 1:
                        startActivity(getActivity(), Sentence_NativeActivity.class, iconName[1]);
                        break;
                }
            }
        });
    }

    private void initdata() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

    }

    public void startActivity(Activity from, Class<?> to, String title) {
        Intent intent = new Intent();
        intent.putExtra("TITLE", title);
        intent.setClass(from, to);
        from.startActivity(intent);
    }

}
