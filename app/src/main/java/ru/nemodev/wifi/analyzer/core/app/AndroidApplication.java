package ru.nemodev.wifi.analyzer.core.app;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class AndroidApplication extends android.app.Application
{
    private static AndroidApplication instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;

        //initFabricIO();
    }

    private void initFabricIO()
    {
        Fabric.with(this, new Crashlytics());
    }

    public static AndroidApplication getInstance()
    {
        return instance;
    }
}