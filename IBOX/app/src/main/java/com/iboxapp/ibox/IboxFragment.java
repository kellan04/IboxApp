package com.iboxapp.ibox;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.iboxapp.ibox.adapter.IboxRecyclerViewAdapter;
import com.iboxapp.ibox.ui.EditThingsActivity;
import com.iboxapp.ibox.ui.LoginActivity;
import com.iboxapp.ibox.ui.MyScrollingActivity;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.lang.reflect.Field;
import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IboxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IboxFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private IboxRecyclerViewAdapter mAdapter;
    private FloatingActionsMenu menuMultipleButton;
    private FloatingActionButton menuEditButton;
    private FloatingActionButton menuChooseButton;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler handler = new Handler();

    private boolean isLoading;
    private final String TAG = "Ibox";
    private ArrayList<String> mDatas = new ArrayList<>();
    private ArrayList<Integer> mDatasImg = new ArrayList<Integer>();


    public IboxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment IboxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IboxFragment newInstance(String param1) {
        IboxFragment fragment = new IboxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ibox, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_box);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new IboxRecyclerViewAdapter(getActivity(), mDatas, mDatasImg);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new IboxRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), MyScrollingActivity.class);
                intent.putExtra("key", 1);
                getActivity().startActivity(intent);
            }
        });

        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            /**
             * 当RecyclerView的滑动状态改变时触发
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);


            }

            /**
             * 当RecyclerView滑动时触发
             * 类似点击事件的MotionEvent.ACTION_MOVE
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");

                int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = mSwipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                                initDatasImg();
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.box_swipeRefreshLayout);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                Log.d(TAG, "post()");
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.clear();
                        mDatasImg.clear();
                        getData();
                        initDatasImg();
                    }
                }, 2000);
                Log.d(TAG, "onRefresh()");
            }
        });

        initfab(view);
        initData();

        return view;
    }

    private void initfab(View view) {

        menuMultipleButton = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
        menuChooseButton = (FloatingActionButton) view.findViewById(R.id.action_s);
        menuEditButton = (FloatingActionButton) view.findViewById(R.id.action_b);
        menuEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().startActivity(new Intent(getActivity(), EditThingsActivity.class));

            }
        });
        menuChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getActivity().startActivity(new Intent(getActivity(), EditThingsActivity.class));
                showDialog();
            }
        });
    }

    private void showDialog() {
        final MaterialDialog mMaterialDialog = new MaterialDialog(getActivity());
        mMaterialDialog.setTitle("筛选");
        mMaterialDialog.setContentView(R.layout.ibox_meterial_dialog);
        // mMaterialDialog.setMessage("Hello world!");
        mMaterialDialog.setPositiveButton("选择", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.show();
    }

    public void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.clear();
                mDatasImg.clear();
                getData();
                initDatasImg();
            }
        }, 1500);

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
        Log.d(TAG, "getData()");
        /*}*/
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
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

}
