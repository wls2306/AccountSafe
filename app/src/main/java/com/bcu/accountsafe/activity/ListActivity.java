package com.bcu.accountsafe.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.bcu.accountsafe.R;
import com.bcu.accountsafe.ViewAdapter.codeAdaputer;
import com.bcu.accountsafe.model.Info;
import com.bcu.accountsafe.vm.InfoViewModel;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    InfoViewModel infoViewModel;
    private BaseAdapter itemAdapter=null;
    List<String> hcList=new ArrayList<>();
    List<Integer> hcLocation=new ArrayList<>();
    private Context context;
    private ListView listView;
    private Toolbar toolbar;
    private MenuItem item ;
    private SearchView searchView;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_list);
        listView=findViewById(R.id.AccountListView);
        infoViewModel= ViewModelProviders.of(this).get(InfoViewModel.class);
        //菜单逻辑
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.app_bar_search:{

                        break;
                    }
                    case R.id.app_bar_add:{
//                        builder = new AlertDialog.Builder(ListActivity.this);
//                        View view = LayoutInflater.from(ListActivity.this).inflate(R.layout.info_layout, null);
//                        builder.setView(view);


                        Info info=new Info();
                        info.setTitle("百度");
                        info.setPassword("789");
                        info.setUsername("uwuw");
                        infoViewModel.insertInfo(info);
                        break;

                    }

                }
                return false;
            }
        });



        infoViewModel.getAllInfoLive().observe(this, new Observer<List<Info>>() {
            @Override
            public void onChanged(List<Info> infos) {
                //TODO: 请在这里写Info列表的展示逻辑
                //TODO: 请在这里写Info列表的展示逻辑
                //TODO: 请在这里写Info列表的展示逻辑
                hcList.clear();
                hcLocation.clear();


                //按拼音顺序排序
                Collections.sort(infos, new Comparator<Info>() {
                    @Override
                    public int compare(Info info, Info t1) {
                        String o1 = info.getTitle();
                        String o2 = t1.getTitle();
                        for (int i = 0; i < o1.length() && i < o2.length(); i++) {
                            int codePoint1 = o1.charAt(i);
                            int codePoint2 = o2.charAt(i);
                            if (Character.isSupplementaryCodePoint(codePoint1)
                                    || Character.isSupplementaryCodePoint(codePoint2)) {
                                i++;
                            }
                            if (codePoint1 != codePoint2) {
                                if (Character.isSupplementaryCodePoint(codePoint1)
                                        || Character.isSupplementaryCodePoint(codePoint2)) {
                                    return codePoint1 - codePoint2;
                                }
                                String pinyin1 = PinyinHelper.toHanyuPinyinStringArray((char) codePoint1) == null
                                        ? null : PinyinHelper.toHanyuPinyinStringArray((char) codePoint1)[0];
                                String pinyin2 = PinyinHelper.toHanyuPinyinStringArray((char) codePoint2) == null
                                        ? null : PinyinHelper.toHanyuPinyinStringArray((char) codePoint2)[0];
                                if (pinyin1 != null && pinyin2 != null) { // 两个字符都是汉字
                                    if (!pinyin1.equals(pinyin2)) {
                                        return pinyin1.compareTo(pinyin2);
                                    }
                                } else {
                                    return codePoint1 - codePoint2;
                                }
                            }
                        }
                        return o1.length() - o2.length();
                    }
                });

                //提取首字母拼音
                String hcR=null;
                for(int i=0;i<infos.size();i++){
                    String hcC=getPinYinHeadChar(infos.get(i).getTitle());
                    if(hcList.size()==0||!hcR.equals(hcC)){
                        hcR=hcC;
                        hcList.add(hcC);
                        hcLocation.add(i);
                    }else {
                       hcList.add(null);
                    }
                }
                itemAdapter=new codeAdaputer(infos,hcList,context);
                listView.setAdapter(itemAdapter);
                itemAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void onStart() {
        if(MainActivity.AActivity!=null)
            MainActivity.AActivity.finish();
        super.onStart();
    }


    @Override//设置菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_toorbar, menu);
        item = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setFocusableInTouchMode(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        return true;
    }


    //提取每个汉字的首字母
    public String getPinYinHeadChar(String str){
        String convert="" ;
        char word = str.charAt(0);
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
        if (pinyinArray != null){
            convert += pinyinArray[0].charAt(0);
        }else{
            convert += word;
        }
        return convert.toUpperCase();
    }



}
