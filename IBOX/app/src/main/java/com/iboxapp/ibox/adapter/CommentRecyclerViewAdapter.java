package com.iboxapp.ibox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iboxapp.ibox.R;
import com.iboxapp.ibox.module.CommentInfo;

import java.util.List;

/**
 * Created by gongchen on 2016/3/29.
 */
public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {

    public List<CommentInfo> beans;
    private OnItemClickListener mOnItemClickListener;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public CommentRecyclerViewAdapter(Context context, List<CommentInfo> beans) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.beans = beans;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.comments_item,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.mTextViewUsername.setText(beans.get(position).getName());
        viewHolder.mTextViewContent.setText(beans.get(position).getComments());
        viewHolder.mTextViewTime.setText(beans.get(position).getDate());
        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.mLinearLayout.setTag(beans.get(position).getName());
        if(mOnItemClickListener != null) {
            /**
             * 这里加了判断，itemViewHolder.itemView.hasOnClickListeners()
             * 目的是减少对象的创建，如果已经为view设置了click监听事件,就不用重复设置了
             * 不然每次调用onBindViewHolder方法，都会创建两个监听事件对象，增加了内存的开销
             */
            if(!viewHolder.mLinearLayout.hasOnClickListeners()) {
                Log.e("ListAdapter", "setOnClickListener");
                viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = viewHolder.getPosition();
                        mOnItemClickListener.onItemClick(v, pos);
                    }
                });

            }
        }
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return beans.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mLinearLayout;
        public TextView mTextViewUsername;
        public TextView mTextViewContent;
        public TextView mTextViewTime;
        public ImageView mImageView;

        public ViewHolder(View view){
            super(view);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.comment_item);
            mTextViewUsername = (TextView) mLinearLayout.findViewById(R.id.comment_text_username);
            mTextViewContent = (TextView) mLinearLayout.findViewById(R.id.comment_text_content);
            mTextViewTime = (TextView) mLinearLayout.findViewById(R.id.comment_text_time);
            mImageView = (ImageView) mLinearLayout.findViewById(R.id.user_image);


        }
    }

    /**
     * 处理item的点击事件
     */
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    //添加点击事件
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
