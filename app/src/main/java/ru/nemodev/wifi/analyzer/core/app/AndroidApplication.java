package ru.nemodev.wifi.analyzer.core.app;


public class AndroidApplication extends android.app.Application
{
    private static AndroidApplication instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
    }

    public static AndroidApplication getInstance()
    {
        return instance;
    }
}