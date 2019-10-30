package com.bcu.accountsafe.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.bcu.accountsafe.R;
import com.bcu.accountsafe.model.Info;
import com.bcu.accountsafe.vm.InfoViewModel;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    InfoViewModel infoViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        infoViewModel= ViewModelProviders.of(this).get(InfoViewModel.class);
        infoViewModel.getAllInfoLive().observe(this, new Observer<List<Info>>() {
            @Override
            public void onChanged(List<Info> infos) {
                //TODO: 请在这里写Info列表的展示逻辑
                //TODO: 请在这里写Info列表的展示逻辑
                //TODO: 请在这里写Info列表的展示逻辑

            }
        });
    }
}
