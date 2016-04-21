package com.iboxapp.ibox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iboxapp.ibox.R;

import java.util.List;
import java.util.Map;

/**
 * Created by gongchen on 2016/4/21.
 */
public class LogisticListviewAdapter extends BaseAdapter {

    private Context mcontext;
    private List<Map<String,Object>> mlist;
    private LayoutInflater minflater;

    public LogisticListviewAdapter(Context context, List<Map<String,Object>> mlist) {
        super();
        this.mcontext = context;
        this.mlist = mlist;
    }


    @Override
    public  int getCount(){
        return  mlist.size();
    }

    @Override
    public Object getItem(int position){
        return  position;
    }

    @Override
    public  long getItemId(int position){
        return  position;
    }

    @Override
    public View getView(int position, View convertView , ViewGroup parent){
        ViewHolder viewHolder = null;
        String titleStr = mlist.get(position).get("title").toString();
        if (convertView == null)
        {
            minflater = LayoutInflater.from(parent.getContext());
            convertView = minflater.inflate(R.layout.logistic_info_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.title.setText(titleStr);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return  convertView;
    }

    static class ViewHolder {
        public TextView year;
        public TextView month;
        public TextView title;
    }
}
