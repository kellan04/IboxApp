package com.iboxapp.ibox.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.iboxapp.ibox.R;
import com.iboxapp.ibox.adapter.BaseRecyclerViewAdapter;
import com.iboxapp.ibox.adapter.IboxRecyclerViewAdapter;
import com.iboxapp.ibox.adapter.TopThingsRecyclerViewAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TopThingsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private TopThingsRecyclerViewAdapter mAdapter;

    private String[] title = {"top1","top2","top3","top4","top5","top6","top7","top8","top9","top10"};
    private ArrayList<String> mDatas = new ArrayList<>();
    private ArrayList<Integer> mDatasImg = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_things);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.show_selection));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view_top_things);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new TopThingsRecyclerViewAdapter(this, mDatas, mDatasImg, title);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        //添加头部
        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.top_things_recyclerview_header);
        header.attachTo(mRecyclerView, true);

        mAdapter.setOnItemClickListener(new TopThingsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(TopThingsActivity.this, MyScrollingActivity.class);
                intent.putExtra("key", 3);
                startActivity(intent);
            }
        });

        getData();
        initDatasImg();
    }

    /**
     * 获取测试数据
     */
    private void getData() {
        /*for (int i = 0; i < 12; i++) {*/
        mDatas.add("肩部开衩荷叶边");
        mDatas.add("复古单鞋平底平跟系带休闲文艺女鞋");
        mDatas.add("YSL/圣罗兰长效丝绸控油粉底液");
        mDatas.add("PIXELON孙悟空刺绣教练服");
        mDatas.add("PIXELON男士中邦靴马丁工装鞋");
        mDatas.add("Pmsix春季新款时尚长款牛皮印花钱包");
        mDatas.add("疯马皮磨砂男士针扣休闲皮带");
        mDatas.add("PIXELON复古男士必备黑色伞");
        mDatas.add("高端蓝牙耳机");
        mDatas.add("日式抹茶巧克力蛋塔");
        mDatas.add("日式抹茶巧克力蛋塔");
        mDatas.add("阳光风车");
        /*}*/
        mAdapter.notifyDataSetChanged();
    }

    public void initDatasImg() {
        for (int position = 1; position <= 12; position++)
            mDatasImg.add(getResId("ic_test_things_" + position + "_1", R.drawable.class));
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
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
