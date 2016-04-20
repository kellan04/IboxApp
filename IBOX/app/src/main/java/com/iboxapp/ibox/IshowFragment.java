package com.iboxapp.ibox;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.iboxapp.ibox.adapter.IshowRecyclerViewAdapter;
import com.iboxapp.ibox.ui.FindActivity;
import com.iboxapp.ibox.ui.MyScrollingActivity;
import com.iboxapp.ibox.ui.TopThingsActivity;
import com.iboxapp.ibox.widget.DividerItemDecoration;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IshowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IshowFragment extends Fragment {
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
        RecyclerViewHeader header = (RecyclerViewHeader) view.findViewById(R.id.show_recyclerview_header);
        header.attachTo(mRecyclerView, true);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new IshowRecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        mAdapter.setOnItemClickListener(new IshowRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), MyScrollingActivity.class);
                getActivity().startActivity(intent);
            }
        });

        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mButtonFind = (Button) view.findViewById(R.id.imageButton_find);
        mButtonSelection = (Button) view.findViewById(R.id.imageButton_selection);

        mButtonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), FindActivity.class);
                getActivity().startActivity(intent);
            }
        });

        mButtonSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), TopThingsActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

}
