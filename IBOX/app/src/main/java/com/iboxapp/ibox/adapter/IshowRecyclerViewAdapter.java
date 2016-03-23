package com.iboxapp.ibox.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iboxapp.ibox.R;

/**
 * Created by gongchen on 2016/3/12.
 */
public class IshowRecyclerViewAdapter extends RecyclerView.Adapter<IshowRecyclerViewAdapter.ViewHolder>  {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private String[] datas;
    private OnItemClickListener mOnItemClickListener;

    public IshowRecyclerViewAdapter(Context context) {
        datas = context.getResources().getStringArray(R.array.datas);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.ishow_cardview,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText(datas[position]);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.mCardView.setTag(datas[position]);
        if(mOnItemClickListener != null) {
            /**
             * 这里加了判断，itemViewHolder.itemView.hasOnClickListeners()
             * 目的是减少对象的创建，如果已经为view设置了click监听事件,就不用重复设置了
             * 不然每次调用onBindViewHolder方法，都会创建两个监听事件对象，增加了内存的开销
             */
            if(!viewHolder.mCardView.hasOnClickListeners()) {
                Log.e("ListAdapter", "setOnClickListener");
                viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
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
        return datas.length;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public ImageView mImageView;

        public ViewHolder(View view){
            super(view);
            mCardView = (CardView) view.findViewById(R.id.cv_item_show);
            mTextView = (TextView) mCardView.findViewById(R.id.text_card_show);
            mImageView = (ImageView) mCardView.findViewById(R.id.image_card_show);


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
