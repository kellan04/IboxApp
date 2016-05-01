package com.iboxapp.ibox;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        mAdapter = new IboxRecyclerViewAdapter(getActivity());
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

        initfab(view);

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


}
