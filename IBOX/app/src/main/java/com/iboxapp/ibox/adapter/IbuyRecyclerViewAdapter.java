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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iboxapp.ibox.R;

import java.io.InputStream;
import java.util.List;

/**
 * Created by gongchen on 2016/3/15.
 */
public class IbuyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    public IbuyRecyclerViewAdapter(Context context, List data, List<Integer> imgs) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.datas = data;
        this.mDatasImg = imgs;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType == TYPE_ITEM) {
            View view = mLayoutInflater.inflate(R.layout.ibuy_cardview, viewGroup,
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
            ((ItemViewHolder)viewHolder).mImageView.setImageBitmap(readBitMap(mContext, mDatasImg.get(position - getHeadViewSize())));
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

                if(!((ItemViewHolder) viewHolder).mButtonOrderInfo.hasOnClickListeners()) {
                    Log.e("ListAdapter", "setOnClickListener");
                    ((ItemViewHolder) viewHolder).mButtonOrderInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = viewHolder.getPosition();
                            mOnItemClickListener.onItemButtonOrderInfoClick(v, pos);
                        }
                    });

                }

                if(!((ItemViewHolder) viewHolder).mButtonConnect.hasOnClickListeners()) {
                    Log.e("ListAdapter", "setOnClickListener");
                    ((ItemViewHolder) viewHolder).mButtonConnect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = viewHolder.getPosition();
                            mOnItemClickListener.onItemButtonConnectClick(v, pos);
                        }
                    });

                }

                if(!((ItemViewHolder) viewHolder).mButtonReceipt.hasOnClickListeners()) {
                    Log.e("ListAdapter", "setOnClickListener");
                    ((ItemViewHolder) viewHolder).mButtonReceipt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = viewHolder.getPosition();
                            ItemViewHolder vh = (ItemViewHolder) viewHolder;
                            mOnItemClickListener.onItemButtonReceiptClick(vh.mButtonReceipt, pos, vh.mTextViewStatus);
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

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public TextView mTextView;
        public ImageView mImageView;
        private Button mButtonOrderInfo;
        private Button mButtonConnect;
        private Button mButtonReceipt;
        public TextView mTextViewStatus;

        public ItemViewHolder(View view) {
            super(view);
            mCardView = (CardView) view.findViewById(R.id.cv_item_buy);
            mTextView = (TextView) view.findViewById(R.id.text_card_buy);
            mImageView = (ImageView) view.findViewById(R.id.image_card_buy);
            mButtonOrderInfo = (Button) view.findViewById(R.id.button_logistics);
            mButtonConnect = (Button) view.findViewById(R.id.button_connect);
            mButtonReceipt = (Button) view.findViewById(R.id.button_receipt);
            mTextViewStatus = (TextView) view.findViewById(R.id.text_view_status);
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
        public void onItemButtonOrderInfoClick(View view, int position);
        public void onItemButtonConnectClick(View view, int position);
        public void onItemButtonReceiptClick(Button button, int position, TextView textview);
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
