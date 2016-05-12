package com.iboxapp.ibox.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iboxapp.ibox.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongchen on 2016/3/12.
 */
public class IshowRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private OnItemClickListener mOnItemClickListener;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_EMPTY = 3;
    private View mHeaderView;
    private View mFooterView;
    private View mEmptyView;
    private List<String> datas;
    private List<Integer> mDatasImg;
    private ArrayList<String> datasCategory = new ArrayList<String>();
    private ArrayList<String> datasTitle = new ArrayList<String>();
    private List<Integer> mDatasImgUser = new ArrayList<Integer>();

    public IshowRecyclerViewAdapter(Context context, List data, List<Integer> imgs) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.datas = data;
        this.mDatasImg = imgs;
        datasTitle.add("文艺青年必备麂皮鞋~");
        datasTitle.add("潮爆了，超舒适！");
        datasCategory.add("鞋子");
        datasCategory.add("鞋子");
//        mDatasImgUser.add(R.drawable.user_img_1);
//        mDatasImgUser.add(R.drawable.user_img_2);

    }
    //创建新View，被LayoutManager所调用
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType == TYPE_ITEM) {
            View view = mLayoutInflater.inflate(R.layout.ishow_item, viewGroup,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View v = mHeaderView;
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_FOOTER) {
            View v = mFooterView;
            return new FooterViewHolder(v);
        } else if (viewType == TYPE_EMPTY) {
            View v = mEmptyView;
            return new EmptyViewHolder(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            ((ItemViewHolder)viewHolder).mTextView.setText(getItem(position));
            ((ItemViewHolder)viewHolder).mTextViewCategory.setText(getItemC(position));
            ((ItemViewHolder)viewHolder).mTextViewTitle.setText(getItemT(position));
            ((ItemViewHolder)viewHolder).mImageView.setImageBitmap(readBitMap(mContext, mDatasImg.get(position - getHeadViewSize())));
//            ((ItemViewHolder)viewHolder).mImageViewUser.setImageBitmap(readBitMap(mContext, mDatasImg.get(position - getHeadViewSize())));
            if(mOnItemClickListener != null) {
                /**
                 * 这里加了判断，itemViewHolder.itemView.hasOnClickListeners()
                 * 目的是减少对象的创建，如果已经为view设置了click监听事件,就不用重复设置了
                 * 不然每次调用onBindViewHolder方法，都会创建两个监听事件对象，增加了内存的开销
                 */
                if(!viewHolder.itemView.hasOnClickListeners()) {
                    Log.e("ListAdapter", "setOnClickListener");
                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = viewHolder.getPosition();
                            mOnItemClickListener.onItemClick(v, pos);
                        }
                    });

                }
            }
        } else if (viewHolder instanceof HeaderViewHolder) {

        } else if (viewHolder instanceof FooterViewHolder) {

        } else if (viewHolder instanceof EmptyViewHolder) {

        }
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        int count;
        int size = datas.size();
        if (size == 0 && null != mEmptyView) {
            count = 1;
        } else {
            count = getHeadViewSize() + size + getFooterViewSize();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int size = datas.size();
        if (size == 0 && null != mEmptyView) {
            return TYPE_EMPTY;
        } else if (position < getHeadViewSize()) {
            return TYPE_HEADER;
        } else if (position >= getHeadViewSize() + datas.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private int getHeadViewSize() {
        return mHeaderView == null ? 0 : 1;
    }

    private int getFooterViewSize() {
        return mFooterView == null ? 0 : 1;
    }

    private String getItem(int position) {
        return datas.get(position - getHeadViewSize());
    }

    private String getItemC(int position) {
        return datasCategory.get(position - getHeadViewSize());
    }
    private String getItemT(int position) {
        return datasTitle.get(position - getHeadViewSize());
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout mRelativeLayout;
        public TextView mTextView;
        public ImageView mImageView;
        public TextView mTextViewCategory;
        public TextView mTextViewTitle;
        public ImageView mImageViewUser;

        public ItemViewHolder(View view) {
            super(view);
            mRelativeLayout = (RelativeLayout) view.findViewById(R.id.ishow_item);
            mTextView = (TextView) view.findViewById(R.id.ishow_item_uesr_name);
            mImageView = (ImageView) view.findViewById(R.id.ishow_item_goods_image);
            mTextViewCategory = (TextView) view.findViewById(R.id.ishow_item_category);
            mTextViewTitle = (TextView) view.findViewById(R.id.ishow_item_goods_title);
            mImageViewUser = (ImageView) view.findViewById(R.id.ishow_item_profile_image);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    //add a header to the adapter
    public void addHeader(View header) {
        mHeaderView = header;
        notifyItemInserted(0);
    }

    //remove a header from the adapter
    public void removeHeader(View header) {
        notifyItemRemoved(0);
        mHeaderView = null;
    }

    //add a footer to the adapter
    public void addFooter(View footer) {
        mFooterView = footer;
        notifyItemInserted(getHeadViewSize() + datas.size());
    }

    //remove a footer from the adapter
    public void removeFooter(View footer) {
        notifyItemRemoved(getHeadViewSize() + datas.size());
        mFooterView = null;
    }

    //add data
    public void addDatas(List<String> data) {
        datas.addAll(data);
        notifyItemInserted(getHeadViewSize() + datas.size() - 1);
    }

    //add data
    public void addData(String data) {
        datas.add(data);
        notifyItemInserted(getHeadViewSize() + datas.size() - 1);
    }

    //refresh data
    public void refreshData(List<String> data){
        datas.clear();
        addDatas(data);
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        notifyItemInserted(0);
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
