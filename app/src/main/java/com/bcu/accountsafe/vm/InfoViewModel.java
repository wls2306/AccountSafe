package com.bcu.accountsafe.vm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bcu.accountsafe.MyDatabase;
import com.bcu.accountsafe.dao.InfoDao;
import com.bcu.accountsafe.model.Info;
import com.bcu.accountsafe.util.Utils;

import java.util.List;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

public class InfoViewModel extends AndroidViewModel {
    private InfoDao infoDao;
    private static String ANDROID_ID;
    public InfoViewModel(@NonNull Application application) {
        super(application);
        MyDatabase myDatabase = MyDatabase.getDatabase(application.getApplicationContext());
        infoDao= myDatabase.getInfoDao();
        ANDROID_ID = Utils.getAndroidId(application.getApplicationContext());
        System.out.println("ANDROID_ID:"+ ANDROID_ID);
    }

    /**
     * 插入过程对密码进行加密运算
     *
     * 加密原理：使用用户手机的IEMI 为秘钥进行加密
     * 需要用户进行授权
     */
    public boolean insertInfo(Info info){
        if (StrUtil.isNotBlank(info.getPassword())) {
            info.setChangeDate(Utils.getTimeStamp());
            infoDao.insert(doEncrypt(info));
            return true;
        }
        Log.e("ViewModel","插入数据失败-password为空");
        return false;
    }

    public LiveData<List<Info>> getAllInfoLive(){
        return infoDao.getAllInfoLive();
    }

    /**
     * 根据编号获取已解密的账号信息
     */
    public Info getInfoDecryptById(int id){
        if (id!=0) {
            return doDecrypt(infoDao.getInfoById(id));
        }else {
            Log.e("ViewModel","根据编号获取已解密的账号信息-参数错误");
            return null;
        }
    }

    /**
     * 根据分类获取账号信息（未解密）
     */
    public List<Info> getInfoByCategory(String category){
        if (StrUtil.isNotBlank(category)) {
            return infoDao.getByCategory(category);
        }else {
            Log.e("ViewModel","根据分类获取账号信息（未解密）-参数错误");
            return null;
        }

    }

    /**
     * 根据名称获取账号信息（未解密）
     */
    public List<Info> getInfoByTitle(String title){
        if (StrUtil.isNotBlank(title)) {
            return infoDao.getInfoByTitle(title);
        }else {
            Log.e("ViewModel","根据名称获取账号信息（未解密）-参数错误");
            return null;
        }
    }

    /**
     * 根据用户名账号信息（未解密）
     */
    public List<Info> getInfoByUsername(String username){
        if (StrUtil.isNotBlank(username)) {
            return infoDao.getInfoByUserName(username);
        }else {
            Log.e("ViewModel","根据用户名账号信息（未解密）-参数错误");
            return null;
        }
    }

    /**
     * 更新账号信息 并加密
     * （主键ID 不可为空）
     */
    public boolean updateInfo(Info i){
        if (i.getId()!=0) {
            StrUtil.isNotBlank(i.getPassword());
            i.setChangeDate(Utils.getTimeStamp());
            infoDao.updateInfo(doEncrypt(i));
            return true;
        }else {
            Log.e("ViewModel","更新账号信息-参数错误");
            return false;
        }

    }

    /**
     * 删除账号信息（通过id)
     */
    public boolean deleteInfobyId(int id){
        if (id!=0) {
            infoDao.deleteById(id);
            return true;
        }else {
            Log.e("ViewModel","删除账号信息-参数错误");
            return false;
        }
    }

    /**
     * 仅供测试使用，测试成功后请删除此项
     * @return
     */
    public String getAndroidId(){
        return ANDROID_ID;
    }


    /**
     * 解密 并 解析时间戳
     */
    private Info doDecrypt(Info i){
        byte[]  key = ANDROID_ID.getBytes();
        SymmetricCrypto aes=new SymmetricCrypto(SymmetricAlgorithm.AES,key);
        byte[] decrypt=aes.decrypt(i.getPassword());
        i.setPassword(new String(decrypt));
        i.setChangeDate(Utils.getTime(i.getChangeDate()));
        return i;
    }

    /**
     * 加密
     */
    private Info doEncrypt(Info info){
        byte[]  key = ANDROID_ID.getBytes();
        SymmetricCrypto aes=new SymmetricCrypto(SymmetricAlgorithm.AES,key);
        byte[] encrypt=aes.encrypt(info.getPassword());
        info.setPassword(new String(encrypt));
        return info;
    }








}
