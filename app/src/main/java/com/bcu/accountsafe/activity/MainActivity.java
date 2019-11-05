package com.bcu.accountsafe.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bcu.accountsafe.R;
import com.bcu.accountsafe.model.Info;
import com.bcu.accountsafe.vm.InfoViewModel;

import java.security.MessageDigest;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    private TextView textView;
    private ImageView imageView;
    private AnimationDrawable fingerprint_Animation;
    private boolean canUseFingerprint=false;
    private FingerprintManagerCompat manager;
    private KeyguardManager keyguardManager;
    public static Activity AActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        fingerprint_Animation = (AnimationDrawable) imageView.getDrawable();
        context=MainActivity.this;
        AActivity=this;








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
        if(!checkHasPassword(getdeviceID())){
          showSigeIn(getdeviceID());
        }

        if(canUseFingerprint){
            fingerprint_Animation.start();
            //启动指纹检测
            manager.authenticate(null, 0, null, new FingerCallBack(), null);
        }
        super.onResume();
    }

    private String toMD5(String string) {
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

    private void creatPasswordConfig(String deviceID,String password){
        SharedPreferences config;
        config=getSharedPreferences("AccountSafeConfig", MODE_PRIVATE);
        SharedPreferences.Editor edit=config.edit();
        edit.putString("deviceID", deviceID).putString("password", password);
        edit.commit();
    }

    private String readPasswordConfig(int a){
        SharedPreferences config;
        config=getSharedPreferences("AccountSafeConfig", MODE_PRIVATE);
        switch (a){
            case 0:return config.getString("deviceID","");
            case 1:return config.getString("password","");
            default:return "err";
        }
    }

    private String getdeviceID(){
        InfoViewModel infoViewModel;
        infoViewModel= ViewModelProviders.of(this).get(InfoViewModel.class);
        return infoViewModel.getAndroidId();

    }


    public void showPasswordLog(View view){
        String deviceID=getdeviceID();
        if(checkHasPassword(deviceID)){
            showLogin();
        }else {
            showSigeIn(deviceID);
        }
    }

    private void showLogin(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final EditText passwordET=new EditText(context);
        passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        dialog.setTitle("请输入密码");
        dialog.setCancelable(false);
        dialog.setView(passwordET);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String passwordR=readPasswordConfig(1);
                String passwordI=passwordET.getText().toString();
                passwordI=toMD5(passwordI);
                if(passwordR.equals(passwordI)){
                    doLog();
                }else showToast("密码错误");
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.show();
    }

    private void showSigeIn(final String deviceID){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final EditText passwordET=new EditText(context);
        passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        dialog.setTitle("请设置密码");
        dialog.setCancelable(false);
        dialog.setView(passwordET);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String passwordI=passwordET.getText().toString();
                if(passwordI.isEmpty()||passwordI.equals("")){
                    showToast("密码不能为空");
                }else {
                    passwordI=toMD5(passwordI);
                    creatPasswordConfig(deviceID,passwordI);
                }

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.show();
    }

    private boolean checkHasPassword(String deviceID){
        boolean b;
        if(readPasswordConfig(0).equals(deviceID)&&!readPasswordConfig(1).equals(""))
            b=true;
        else b=false;
        return b;
    }

    public static void showToast(String mes){
        Toast toast=Toast.makeText(context,mes,Toast.LENGTH_SHORT);
        toast.show();

    }

    public static void doLog(){
        Intent intent=new Intent(context,ListActivity.class);
        context.startActivity(intent);
        //System.exit(0);
    }

    public void finishM(){
        MainActivity.this.finish();
    }






}