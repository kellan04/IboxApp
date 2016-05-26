package com.iboxapp.ibox.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iboxapp.ibox.R;

import java.io.InputStream;
import java.util.List;

/**
 * Created by gongchen on 2016/4/20.
 */
public class TopThingsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    private String[] titles;
    private List<String> datas;
    private List<Integer> mDatasImg;

    public enum ITEM_TYPE {
        ITEM1
    }

    public TopThingsRecyclerViewAdapter(Context context, List data, List<Integer> imgs, String[] titles) {
        this.titles = titles;
        this.context = context;
        this.datas = data;
        this.mDatasImg = imgs;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.top_things_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).mTextViewPrice.setText(titles[position]);
        ((ItemViewHolder) holder).mTextViewName.setText(getItem(position));
        ((ItemViewHolder) holder).mImageView.setImageBitmap(readBitMap(context, mDatasImg.get(position)));
        if(mOnItemClickListener != null) {
            /**
             * 这里加了判断，itemViewHolder.itemView.hasOnClickListeners()
             * 目的是减少对象的创建，如果已经为view设置了click监听事件,就不用重复设置了
             * 不然每次调用onBindViewHolder方法，都会创建两个监听事件对象，增加了内存的开销
             */
            if(!holder.itemView.hasOnClickListeners()) {
                Log.e("ListAdapter", "setOnClickListener");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getPosition();
                        mOnItemClickListener.onItemClick(v, pos);
//                        mOnItemClickListener.onItemLikeClick(v,pos);
                    }
                });

            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE.ITEM1.ordinal();
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.length;
    }

    private String getItem(int position) {
        return datas.get(position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewPrice;
        public TextView mTextViewName;
        public ImageView mImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTextViewPrice = (TextView) itemView.findViewById(R.id.text_card_price);
            mTextViewName = (TextView) itemView.findViewById(R.id.text_card_name);
            mImageView = (ImageView) itemView.findViewById(R.id.image_card_top10);
        }
    }

    /**
     * 处理item的点击事件
     */
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
//        public void onItemLikeClick(View view, int position);
//        public void onItemBuyClick(View view, int position);
//        public void onItemShareClick(View view, int position);
    }

    //添加点击事件
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

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
