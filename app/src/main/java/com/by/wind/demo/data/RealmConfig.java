/*
package com.by.wind.demo.data;

import android.content.Context;


import com.by.wind.demo.entity.Parent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

*/
/**
 * Created by Wind on 2017/11/15.
 *//*


public class RealmConfig  {
    private  static RealmConfig mRealmConfig = null;

    public static RealmConfig getInstance() {
        if (null == mRealmConfig) {
            mRealmConfig = new RealmConfig();
        }
        return  mRealmConfig;
    }

    public void  initDataConfig(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .initialData(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                      realm.createObject(Parent.class);
                    }})
                .build();
        Realm.deleteRealm(realmConfig); // Delete Realm between app restarts.
        Realm.setDefaultConfiguration(realmConfig);
    }
}
*/
