package com.arifcebe.samplerealm;

import android.app.Application;

import com.arifcebe.samplerealm.model.Migration;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by arifcebe on 6/13/16.
 */
public class AppManager extends Application {

    private static final int DB_VERSION = 1;
    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .schemaVersion(DB_VERSION)
                .name("sample.realm")
                .migration(new Migration())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        if(BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }
}
