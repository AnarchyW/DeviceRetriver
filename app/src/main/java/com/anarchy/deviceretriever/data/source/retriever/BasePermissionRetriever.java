package com.anarchy.deviceretriever.data.source.retriever;

import android.content.Context;
import android.support.annotation.NonNull;

import com.anarchy.deviceretriever.data.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Version 2.1.1
 * <p>
 * Date: 16/10/21 16:16
 * <p/>
 */
abstract class BasePermissionRetriever implements PermissionRetriever {
    final Context mContext;
    List<Info> mResult;

    BasePermissionRetriever(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public List<Info> retrieve() {
        return retrieve(true);
    }

    @Override
    public List<Info> retrieve(boolean ignorePermission) {
        if(mResult == null) {
            mResult = new ArrayList<>();
        }else {
            mResult.clear();
        }
        return doRetrieve(ignorePermission);
    }

    abstract List<Info> doRetrieve(boolean ignorePermission);

    String[] addPermission(@NonNull String[] source, String add) {
        int length = source.length + 1;
        String[] result = new String[length];
        System.arraycopy(source, 0, result, 0, source.length);
        result[length - 1] = add;
        return result;
    }
}
