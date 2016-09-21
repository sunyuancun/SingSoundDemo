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
import android.widget.Toast;

import com.singsound.singsounddemo.activity.Article_OnlineCloudActivity;
import com.singsound.singsounddemo.activity.Choice_OnlineCloudActivity;
import com.singsound.singsounddemo.activity.QuestionAnswer_OnlineCloudActivity;
import com.singsound.singsounddemo.activity.Sentence_OnlineCloudActivity;
import com.singsound.singsounddemo.adapter.GridViewAdapter;
import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.activity.Word_OnlineCloudActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CloudFragment extends Fragment {

    GridView gview;
    GridViewAdapter mAdapter;

    private static final String CLOUD = "CLOUD";

    // 图片封装为一个数组
    private int[] icon = {R.mipmap.word,
            R.mipmap.sentence,
            R.mipmap.mofang,
            R.mipmap.xuanze,
            R.mipmap.bankaifang,
            R.mipmap.article};
    //title
    private String[] iconName = {"单词跟读", "句子跟读", "模仿跟读", "选择题测评", "问答题测评", "口头作文测评"};

    private List<Map<String, Object>> data_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cloud, container, false);
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
                        startActivity(getActivity(), Word_OnlineCloudActivity.class, iconName[0]);
                        break;
                    case 1:
                        startActivity(getActivity(), Sentence_OnlineCloudActivity.class, iconName[1]);
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "正在开发...", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        startActivity(getActivity(), Choice_OnlineCloudActivity.class, iconName[3]);
                        break;
                    case 4:
                        startActivity(getActivity(), QuestionAnswer_OnlineCloudActivity.class, iconName[4]);
                        break;
                    case 5:
                        startActivity(getActivity(), Article_OnlineCloudActivity.class, iconName[5]);
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
