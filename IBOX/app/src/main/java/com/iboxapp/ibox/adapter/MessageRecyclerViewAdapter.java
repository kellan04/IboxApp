package com.iboxapp.ibox.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iboxapp.ibox.R;
import com.iboxapp.ibox.tool.Bean;

import java.util.List;

/**
 * Created by gongchen on 2016/3/16.
 */
public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    public List<Bean> beans;

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

        public SystemViewHoler(View view) {
            super(view);
            this.textView_name = (TextView) view.findViewById(R.id.message_text_username);
            /*this.textView_content = (TextView) view.findViewById(R.id.message_text_content);
            this.textView_time = (TextView) view.findViewById(R.id.message_text_time);
            this.textView_amount = (TextView) view.findViewById(R.id.message_text_amount);*/

            this.Imageview = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.myiamge);
//            this.button = (Button) view.findViewById(R.id.mybutton);
        }
    }

    /**
     * UsersViewHoler
     *
     * @author edsheng
     *
     */
    public class UsersViewHoler extends RecyclerView.ViewHolder {
        public TextView textView_name;
        /*public TextView textView_content;
        public TextView textView_time;
        public TextView textView_amount;*/
        public de.hdodenhof.circleimageview.CircleImageView Imageview;
        public Button button;

        public UsersViewHoler(View view) {
            super(view);
            this.textView_name = (TextView) view.findViewById(R.id.message_text_username);
            /*this.textView_content = (TextView) view.findViewById(R.id.message_text_content);
            this.textView_time = (TextView) view.findViewById(R.id.message_text_time);
            this.textView_amount = (TextView) view.findViewById(R.id.message_text_amount);*/
            this.Imageview = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.myiamge);
//            this.button = (Button) view.findViewById(R.id.mybutton);
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
                        R.layout.message_item, null);
                holer = new SystemViewHoler(v);
                break;
            case Bean.Y_TYPE:
                v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.message_item, null);
                holer = new UsersViewHoler(v);
                break;
            case Bean.Z_TYPE:
                v = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.message_item, null);

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
                SystemViewHoler holer = (SystemViewHoler) holder;
                holer.textView_name.setText(beans.get(position).getText());
                /*holer.textView_content.setText("new messages");
                holer.textView_time.setText("8:39");
                holer.textView_amount.setText("1");*/
//                holer.button.setText(beans.get(position).getText());
                holer.Imageview.setImageResource(R.mipmap.ic_launcher);
                break;
            case Bean.Y_TYPE:
//                ButtonHolder buttonHolder = (ButtonHolder) holder;
//                buttonHolder.button.setText(beans.get(position).getText());
                break;
            case Bean.Z_TYPE:
//                ImageHoler imageHoler = (ImageHoler) holder;
//                imageHoler.Imageview.setImageResource(android.R.drawable.checkbox_on_background);
                break;
        }
    }
}
