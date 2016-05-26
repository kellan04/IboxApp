package com.iboxapp.ibox.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.iboxapp.ibox.R;
import com.iboxapp.ibox.adapter.LogisticListviewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogisticInfoActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ListView mListView;
    private List<String> data;
    private LogisticListviewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistic_info);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.logistic_info));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setDividerHeight(0);
        mAdapter = new LogisticListviewAdapter(this, getData());
        mListView.setAdapter(mAdapter);

    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("title",getResources().getString(R.string.logistic_info6));
        list.add(map);

        map = new HashMap<>();
        map.put("title",getResources().getString(R.string.logistic_info5));
        list.add(map);

        map = new HashMap<>();
        map.put("title", getResources().getString(R.string.logistic_info4));
        list.add(map);

        map = new HashMap<>();
        map.put("title",getResources().getString(R.string.logistic_info3));
        list.add(map);

        map = new HashMap<>();
        map.put("title", getResources().getString(R.string.logistic_info2));
        list.add(map);

        map = new HashMap<>();
        map.put("title",getResources().getString(R.string.logistic_info1));
        list.add(map);

        return list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
