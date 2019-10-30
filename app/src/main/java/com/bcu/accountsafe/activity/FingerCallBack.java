package com.bcu.accountsafe.activity;

import android.util.Log;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import static android.content.ContentValues.TAG;

public class FingerCallBack extends FingerprintManagerCompat.AuthenticationCallback {
    public FingerCallBack() {
        super();
    }
    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        super.onAuthenticationError(errMsgId, errString);
        Log.d(TAG, "onAuthenticationError: " + errString);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        super.onAuthenticationHelp(helpMsgId, helpString);
        Log.d(TAG, "onAuthenticationHelp: " + helpString);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        Log.d(TAG, "onAuthenticationSucceeded: " + "验证成功");
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        Log.d(TAG, "onAuthenticationFailed: " + "验证失败");
    }
}
