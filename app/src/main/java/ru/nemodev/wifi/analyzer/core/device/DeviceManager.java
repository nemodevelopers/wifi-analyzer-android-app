package ru.nemodev.wifi.analyzer.core.device;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.format.Formatter;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import ru.nemodev.wifi.analyzer.core.app.AndroidApplication;

public class DeviceManager
{
    public static DeviceInfo getDeviceInfo() {

        WifiManager manager = (WifiManager) AndroidApplication.getInstance()
                .getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = manager.getConnectionInfo();

        return new DeviceInfo(
                Build.DEVICE,
                Build.MODEL,
                Build.VERSION.SDK,
                Build.VERSION.RELEASE,
                getMacAddr(),
                Formatter.formatIpAddress(info.getIpAddress())
        );
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
        }
        return "";
    }
}
