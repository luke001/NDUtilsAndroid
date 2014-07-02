package com.nelepovds.ndutils;

import android.content.SharedPreferences;

import com.activeandroid.app.Application;
import com.nelepovds.ndutils.common.Cache;

/**
 * Created by dmitrynelepov on 08.06.14.
 */
public class NDUtilsApplication extends Application {

    public SharedPreferences sPref;

    public Cache cache;


    @Override
    public void onCreate() {
        super.onCreate();
        this.sPref = this.getSharedPreferences(getPreferenceName(), MODE_PRIVATE);
        this.cache = new Cache(getExternalCacheDir());
        this.loadPreferences();
    }

    public void loadPreferences() {
    }

    public String getPreferenceName(){
        return this.getClass().getSimpleName()+".set";
    }



}

