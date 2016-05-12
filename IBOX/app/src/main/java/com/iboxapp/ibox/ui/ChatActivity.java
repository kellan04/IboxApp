package com.iboxapp.ibox.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.iboxapp.ibox.R;
import com.iboxapp.ibox.adapter.ChatMsgViewAdapter;
import com.iboxapp.ibox.module.ChatMsgEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private Button mBtnSend;// 发送btn
    private EditText mEditTextContent;
    private ListView mListView;
    private ChatMsgViewAdapter mAdapter;// 消息视图的Adapter
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();// 消息对象数组
    private Toolbar mToolbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.message_title_person));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();// 初始化view

        initData();// 初始化数据
        mListView.setSelection(mAdapter.getCount() - 1);
    }

    /**
     * 初始化view
     */
    public void initView() {
        mListView = (ListView) findViewById(R.id.message_listview);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
        mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
    }

    private String[] msgArray = new String[] { "可以大刀吗？", "不议价不议价", "包邮呢？", "这个可以有"};

    private String[] dataArray = new String[] { "2012-09-22 18:00:02",
            "2012-09-22 18:10:22", "2012-09-22 18:11:24",
            "2012-09-22 18:20:23", "2012-09-22 18:30:31",
            "2012-09-22 18:35:37", "2012-09-22 18:40:13",
            "2012-09-22 18:50:26", "2012-09-22 18:52:57",
            "2012-09-22 18:55:11", "2012-09-22 18:56:45",
            "2012-09-22 18:57:33", };
    private final static int COUNT = 4;// 初始化数组总数

    /**
     * 模拟加载消息历史，实际开发可以从数据库中读出
     */
    public void initData() {
        for (int i = 0; i < COUNT; i++) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setDate(dataArray[i]);
            if (i % 2 == 0) {
                entity.setName("肖B");
                entity.setMsgType(true);// 收到的消息
            } else {
                entity.setName("必败");
                entity.setMsgType(false);// 自己发送的消息
            }
            entity.setMessage(msgArray[i]);
            mDataArrays.add(entity);
        }

        mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
        mListView.setAdapter(mAdapter);
    }

    /**
     * 发送消息
     */
    private void send() {
        String contString = mEditTextContent.getText().toString();
        if (contString.length() > 0) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setName("必败");
            entity.setDate(getDate());
            entity.setMessage(contString);
            entity.setMsgType(false);

            mDataArrays.add(entity);
            mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变

            mEditTextContent.setText("");// 清空编辑框数据

            mListView.setSelection(mListView.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
        }
    }

    /**
     * 发送消息时，获取当前事件
     *
     * @return 当前时间
     */
    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }

}
