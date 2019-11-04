package com.bcu.accountsafe.ViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bcu.accountsafe.R;
import com.bcu.accountsafe.model.Info;

import java.util.ArrayList;
import java.util.List;

public class codeAdaputer extends BaseAdapter {
    private List<Info> mData=new ArrayList<>();
    private List<String> chL=new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public codeAdaputer(List<Info> mData,List<String> chL, Context context) {
        this.mData = mData;
        this.chL=chL;
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
        codeAdaputer.ViewHorder viewHorder=null;
        if(view==null){
            view=layoutInflater.inflate(R.layout.list_layout,null);
            viewHorder=new ViewHorder();
            viewHorder.type=view.findViewById(R.id.button);
            viewHorder.textView=view.findViewById(R.id.textView4);
            if(chL.get(i)==null) viewHorder.type.setVisibility(View.INVISIBLE);
            else viewHorder.type.setText(chL.get(i));
            viewHorder.textView.setText(mData.get(i).getTitle());
        }else {
            viewHorder=(ViewHorder) view.getTag();
        }

        return view;
    }

    static class ViewHorder{
        public Button type;
        public TextView textView;
    }
}
