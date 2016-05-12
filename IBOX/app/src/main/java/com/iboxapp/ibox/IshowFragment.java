package com.iboxapp.ibox;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.iboxapp.ibox.adapter.IshowRecyclerViewAdapter;
import com.iboxapp.ibox.ui.FindActivity;
import com.iboxapp.ibox.ui.MyScrollingActivity;
import com.iboxapp.ibox.ui.TopThingsActivity;
import com.iboxapp.ibox.util.BitmapUtils;
import com.iboxapp.ibox.widget.DividerItemDecoration;
import com.iboxapp.ibox.widget.LocalImageHolderView;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IshowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IshowFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private IshowRecyclerViewAdapter mAdapter;
    private Button mButtonFind;
    private Button mButtonSelection;
    private LinearLayout mLinearLayout;
    private ConvenientBanner mConvenientBanner;//顶部广告栏控件
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler mHandler;

    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private ArrayList<String> mDatas = new ArrayList<String>();
    private ArrayList<Integer> mDatasImg = new ArrayList<Integer>();


    public IshowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment IshowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IshowFragment newInstance(String param1) {
        IshowFragment fragment = new IshowFragment();
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
        View view = inflater.inflate(R.layout.fragment_ishow, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_show);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //添加头部
        /*RecyclerViewHeader header = (RecyclerViewHeader) view.findViewById(R.id.show_recyclerview_header);
        header.attachTo(mRecyclerView, true);*/
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new IshowRecyclerViewAdapter(getActivity(), mDatas, mDatasImg);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        mAdapter.setOnItemClickListener(new IshowRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), MyScrollingActivity.class);
                intent.putExtra("key", 3);
                getActivity().startActivity(intent);
            }
        });

        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.ishow_refreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mLinearLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.ishow_header, null);
        mConvenientBanner = (ConvenientBanner) mLinearLayout.findViewById(R.id.ishow_convenientBanner);
        mLinearLayout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //创建属于主线程的handler
        mHandler = new Handler();
        initBanner();
        initEvents();
        initfab(view);



        return view;
    }

    private void initfab(View view) {
        mButtonFind = (Button) mLinearLayout.findViewById(R.id.imageButton_find);
        mButtonSelection = (Button) mLinearLayout.findViewById(R.id.imageButton_selection);

        mButtonFind.setBackground(new BitmapDrawable(readBitMap(getActivity(),R.drawable.bg_hat)));
//        mButtonFind.setBackground(new BitmapDrawable(BitmapUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_hat, this.mButtonFind.getWidth(), this.mButtonFind.getHeight())));
        mButtonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), FindActivity.class);
                getActivity().startActivity(intent);
            }
        });

        mButtonSelection.setBackground(new BitmapDrawable(readBitMap(getActivity(), R.drawable.bg_star)));
        mButtonSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), TopThingsActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private void initEvents() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initBanner(){
        initLocalImageLoader();
        //本地图片例子
        mConvenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
        //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
//                .setOnItemClickListener(getActivity());
        mAdapter.addHeader(mLinearLayout);
        loadTestDatas();
    }

    private void initLocalImageLoader(){
        //本地图片集合
        for (int position = 1; position < 3; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
    }

    private void loadTestDatas() {

        mDatas.add("kelly");
        mDatasImg.add(getResId("ic_test_things_2_1", R.drawable.class));
        mDatas.add("muji");
        mDatasImg.add(getResId("ic_test_things_5_1", R.drawable.class));
        mAdapter.notifyDataSetChanged();
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
    public void onPause() {
        super.onPause();
        //停止翻页
        mConvenientBanner.stopTurning();
    }

    @Override
    public void onResume() {
        super. onResume();
        //开始自动翻页
        mConvenientBanner.startTurning(5000);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(), "onRefresh", Toast.LENGTH_SHORT).show();
        new Thread(){

            @Override
            public void run() {
                try {
                    sleep(3000);
                    mAdapter.addData("user_1");
                    mAdapter.addData("user_2");
                    mAdapter.addData("user_3");
                    mAdapter.addData("user_4");
                    mHandler.post(runnableUi);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // 构建Runnable对象，在runnable中更新界面
    Runnable runnableUi = new Runnable(){
        @Override
        public void run() {
            //更新界面
            mSwipeRefreshLayout.setRefreshing(false);
        }

    };

    /**
     * 以最省内存的方式读取本地资源的图片
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId){

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}
