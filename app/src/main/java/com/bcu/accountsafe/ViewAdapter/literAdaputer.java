package com.bcu.accountsafe.ViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bcu.accountsafe.R;
import com.bcu.accountsafe.model.liter;

import java.util.ArrayList;
import java.util.List;

public class literAdaputer extends BaseAdapter {
    List<liter> mData=new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public literAdaputer(List<liter> mData, Context context) {
        this.mData = mData;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHorder viewHorder=null;
        if(view==null){
            view=layoutInflater.inflate(R.layout.litter,null);
            viewHorder=new ViewHorder();
            viewHorder.type=view.findViewById(R.id.buttonLitter);
            viewHorder.type.setText(mData.get(i).getLiterL());
            if(mData.get(i).getHas()){
                viewHorder.type.setEnabled(true);
            }else {
                viewHorder.type.setEnabled(false);
            }
            view.setTag(viewHorder);
            viewHorder.type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }else {
            viewHorder=(ViewHorder) view.getTag();
        }

        return view;
    }

    static class ViewHorder{
        public Button type;
    }
}
