package com.singsound.singsounddemo.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.singsound.singsounddemo.Config;
import com.singsound.singsounddemo.GridViewAdapter;
import com.singsound.singsounddemo.R;
import com.singsound.singsounddemo.activity.WordActivity;

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
    private String[] iconName;

    // 图片封装为一个数组
    private int[] icon = {R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher};

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
                String type = (String) map_position.get("type");
                Log.e("-------题型------", i + "-----" + type);

                switch (i) {
                    case 0:
                        startActivity(getActivity(), WordActivity.class, type);
                        break;
                    case 1:
                        startActivity(getActivity(), WordActivity.class, type);
                        break;
                    case 2:
                        startActivity(getActivity(), WordActivity.class, type);
                        break;
                    case 3:
                        startActivity(getActivity(), WordActivity.class, type);
                        break;
                    case 4:
                        startActivity(getActivity(), WordActivity.class, type);
                        break;
                    case 5:
                        startActivity(getActivity(), WordActivity.class, type);
                        break;
                }
            }
        });
    }


    private void initdata() {
        iconName = this.getResources().getStringArray(R.array.titles);
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            switch (i) {
                case 0:
                    map.put("type", Config.TYPE_Word);
                    break;
                case 1:
                    map.put("type", Config.TYPE_sent);
                    break;
                case 2:
                    map.put("type", Config.TYPE_Question_answer);
                    break;
                case 3:
                    map.put("type", Config.TYPE_choc);
                    break;
                case 4:
                    map.put("type", Config.TYPE_Paragraph);
                    break;
                case 5:
                    map.put("type", Config.TYPE_pic_article);
                    break;


            }
            data_list.add(map);
        }

    }

    public void startActivity(Activity from, Class<?> to, String type) {
        Intent intent = new Intent();
        intent.putExtra("TYPE", type);
        intent.setClass(from, to);
        from.startActivity(intent);
    }

}
