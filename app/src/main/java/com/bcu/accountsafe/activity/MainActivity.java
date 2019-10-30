package com.bcu.accountsafe.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bcu.accountsafe.R;
import com.bcu.accountsafe.model.Info;
import com.bcu.accountsafe.vm.InfoViewModel;

import java.security.MessageDigest;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    private AnimationDrawable fingerprint_Animation;
    private boolean canUseFingerprint=false;
    private FingerprintManagerCompat manager;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        fingerprint_Animation = (AnimationDrawable) imageView.getDrawable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //指纹检测控件
        manager = FingerprintManagerCompat.from(this);
        keyguardManager =(KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        //检查指纹检测硬件支持
        canUseFingerprint=manager.isHardwareDetected()&&manager.hasEnrolledFingerprints()&&keyguardManager.isKeyguardSecure();
    }

    @Override
    protected void onResume() {
        if(canUseFingerprint){
            fingerprint_Animation.start();
            //启动指纹检测
            manager.authenticate(null, 0, null, new FingerCallBack(), null);
        }
        super.onResume();
    }

    private String toMD5(String string){
        String slat = "&%5123***&&%%$$#@";
        try {
            string = string + slat;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(string.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}