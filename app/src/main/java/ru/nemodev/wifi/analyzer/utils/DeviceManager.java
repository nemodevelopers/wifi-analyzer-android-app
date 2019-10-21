package ru.nemodev.wifi.analyzer.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.format.Formatter;

import ru.nemodev.wifi.analyzer.ui.device.DeviceInfo;

public class DeviceManager
{
    public static DeviceInfo getDeviceInfo(Context context) {

        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();

        return new DeviceInfo(
                Build.DEVICE,
                Build.MODEL,
                Build.VERSION.SDK,
                Build.PRODUCT,
                info.getMacAddress(),
                Formatter.formatIpAddress(info.getIpAddress())
        );
    }

}
