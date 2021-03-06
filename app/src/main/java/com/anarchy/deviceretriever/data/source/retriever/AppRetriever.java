package com.anarchy.deviceretriever.data.source.retriever;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.anarchy.deviceretriever.data.Info;
import com.anarchy.deviceretriever.data.source.retriever.utils.CommonUtils;

import java.io.File;
import java.util.List;

/**
 * Version 2.1.1
 * <p>
 * Date: 16/10/21 14:03
 * <p/>
 */
public class AppRetriever extends BasePermissionRetriever {
    private final static String TAG = AppRetriever.class.getSimpleName();

    public AppRetriever(@NonNull Context context) {
        super(context);
    }

    @Override
    public String[] checkUnGrantedPermission() {
        return new String[0];
    }

    @Override
    List<Info> doRetrieve(boolean ignorePermission) {
        //应用包名
        mResult.add(new Info("package name",mContext.getPackageName(),"应用包名"));
        //uid
        mResult.add(new Info("uid",String.valueOf(mContext.getApplicationInfo().uid),"应用 user id"));
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_SIGNATURES);
            mResult.add(new Info("version name",packageInfo.versionName,"应用版本"));
            if(packageInfo.signatures != null) {
                Signature signature = packageInfo.signatures[0];
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            X509Certificate cert = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(signature.toByteArray()));
                mResult.add(new Info("sign md5", CommonUtils.getMessageDigest(signature.toByteArray())));
            }
            mResult.add(new Info("apk md5",CommonUtils.getMd5ByFile(new File(mContext.getApplicationInfo().sourceDir))));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG,"get signature failure");
        }
        return mResult;
    }

}
