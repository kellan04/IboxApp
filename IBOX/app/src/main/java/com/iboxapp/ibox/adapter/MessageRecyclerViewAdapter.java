package com.iboxapp.ibox.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iboxapp.ibox.R;
import com.iboxapp.ibox.module.Bean;

import java.util.List;

/**
 * Created by gongchen on 2016/3/16.
 */
public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    public List<Bean> beans;
    private OnItemClickListener mOnItemClickListener;

    public MessageRecyclerViewAdapter(List<Bean> beans) {
        super();
        this.beans = beans;
    }

    /**
     * 内部SystemViewHoler
     *
     * @author edsheng
     *
     */
    public class SystemViewHoler extends RecyclerView.ViewHolder {
        public TextView textView_name;
       /* public TextView textView_content;
        public TextView textView_time;
        public TextView textView_amount;*/
        public de.hdodenhof.circleimageview.CircleImageView Imageview;
        public Button button;
        public LinearLayout mLinearLayout;

        public SystemViewHoler(View view) {
            super(view);
//            this.textView_name = (TextView) view.findViewById(R.id.message_text_username);
            /*this.textView_content = (TextView) view.findViewById(R.id.message_text_content);
            this.textView_time = (TextView) view.findViewById(R.id.message_text_time);
            this.textView_amount = (TextView) view.findViewById(R.id.message_text_amount);*/

//            this.Imageview = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.myiamge);
//            this.button = (Button) view.findViewById(R.id.mybutton);
            this.mLinearLayout = (LinearLayout) view.findViewById(R.id.msg_sys_item);
        }
    }

    /**
     * UsersViewHoler
     *
     * @author edsheng
     *
     */
    public class ChatsViewHoler extends RecyclerView.ViewHolder {
        public TextView textView_name;
        /*public TextView textView_content;
        public TextView textView_time;
        public TextView textView_amount;*/
        public de.hdodenhof.circleimageview.CircleImageView Imageview;
        public Button button;
        public LinearLayout mLinearLayout;

        public ChatsViewHoler(View view) {
            super(view);
            this.textView_name = (TextView) view.findViewById(R.id.message_text_username);
            /*this.textView_content = (TextView) view.findViewById(R.id.message_text_content);
            this.textView_time = (TextView) view.findViewById(R.id.message_text_time);
            this.textView_amount = (TextView) view.findViewById(R.id.message_text_amount);*/
            this.Imageview = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.myiamge);
//            this.button = (Button) view.findViewById(R.id.mybutton);
            this.mLinearLayout = (LinearLayout) view.findViewById(R.id.msg_chat_item);
        }
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return beans.size();
    }

    /**
     * 获取消息的类型
     */
    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return beans.get(position).getType();
    }

    /**
     * 创建VIewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        // TODO Auto-generated method stub
        View v = null;
        ViewHolder holer = null;
        switch (viewtype) {
            case Bean.X_TYPE:
                v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.message_sys_item, null);
                holer = new SystemViewHoler(v);
                break;
            case Bean.Y_TYPE:
                v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.message_item, null);
                holer = new ChatsViewHoler(v);
                break;
        }

        return holer;
    }

    /**
     * 绑定viewholder
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO Auto-generated method stub
        switch (getItemViewType(position)) {
            case Bean.X_TYPE:
                initSystemViewHoler(holder);

                break;
            case Bean.Y_TYPE:
                initChatsViewHoler(holder);

                break;
        }

    }

    private void initSystemViewHoler(ViewHolder holder) {
        final SystemViewHoler holer = (SystemViewHoler) holder;
//                holer.textView_name.setText(beans.get(position).getText());
                /*holer.textView_content.setText("new messages");
                holer.textView_time.setText("8:39");
                holer.textView_amount.setText("1");*/
//                holer.button.setText(beans.get(position).getText());
//                holer.Imageview.setImageResource(R.mipmap.ic_launcher);
        if(mOnItemClickListener != null) {
            /**
             * 这里加了判断，itemViewHolder.itemView.hasOnClickListeners()
             * 目的是减少对象的创建，如果已经为view设置了click监听事件,就不用重复设置了
             * 不然每次调用onBindViewHolder方法，都会创建两个监听事件对象，增加了内存的开销
             */
            if(!holer.mLinearLayout.hasOnClickListeners()) {
                Log.e("ListAdapter", "setOnClickListener");
                holer.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holer.getPosition();
                        mOnItemClickListener.onItemClick(v, pos);
                    }
                });

            }
        }
    }
    private void initChatsViewHoler(ViewHolder holder) {
        final ChatsViewHoler holer = (ChatsViewHoler) holder;
        //                ButtonHolder buttonHolder = (ButtonHolder) holder;
//                buttonHolder.button.setText(beans.get(position).getText());
        if(mOnItemClickListener != null) {
            /**
             * 这里加了判断，itemViewHolder.itemView.hasOnClickListeners()
             * 目的是减少对象的创建，如果已经为view设置了click监听事件,就不用重复设置了
             * 不然每次调用onBindViewHolder方法，都会创建两个监听事件对象，增加了内存的开销
             */
            if(!holer.mLinearLayout.hasOnClickListeners()) {
                Log.e("ListAdapter", "setOnClickListener");
                holer.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holer.getPosition();
                        mOnItemClickListener.onItemClick(v, pos);
                    }
                });

            }
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
