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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iboxapp.ibox.adapter.IboxRecyclerViewAdapter;
import com.iboxapp.ibox.adapter.IbuyRecyclerViewAdapter;
import com.iboxapp.ibox.ui.LogisticInfoActivity;
import com.iboxapp.ibox.ui.MyScrollingActivity;
import com.iboxapp.ibox.ui.OrderInfoActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IbuyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IbuyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private IbuyRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler handler = new Handler();

    private boolean isLoading;
    private final String TAG = "Ibuy";
    private ArrayList<String> mDatas = new ArrayList<>();


    public IbuyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment IbuyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IbuyFragment newInstance(String param1) {
        IbuyFragment fragment = new IbuyFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ibuy, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_buy);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new IbuyRecyclerViewAdapter(getActivity(), mDatas);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new IbuyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), OrderInfoActivity.class);
                intent.putExtra("key", 3);
                getActivity().startActivity(intent);
            }

            @Override
            public void onItemButtonOrderInfoClick(View view, int position) {
                Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), LogisticInfoActivity.class);
                intent.putExtra("key", 3);
                getActivity().startActivity(intent);
            }
        });

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
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.boy_swipeRefreshLayout);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        /*mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                Log.d(TAG, "post()");
            }


        });*/
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.clear();
                        getData();
                    }
                }, 2000);
                Log.d(TAG, "onRefresh()");
            }
        });

        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        initData();
        return view;
    }

    public void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.clear();
                getData();
            }
        }, 1500);

    }

    /**
     * 获取测试数据
     */
    private void getData() {
        for (int i = 0; i < 1; i++) {
            mDatas.add("标题");
            Log.d(TAG, "getData()");
        }
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
    }
}
