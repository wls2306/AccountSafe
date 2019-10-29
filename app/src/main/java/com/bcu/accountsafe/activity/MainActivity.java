package com.bcu.accountsafe.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.TextView;

import com.bcu.accountsafe.R;
import com.bcu.accountsafe.model.Info;
import com.bcu.accountsafe.vm.InfoViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    InfoViewModel infoViewModel;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoViewModel= ViewModelProviders.of(this).get(InfoViewModel.class);
        textView=findViewById(R.id.demo1);
        textView.setText(infoViewModel.getAndroidId());
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
