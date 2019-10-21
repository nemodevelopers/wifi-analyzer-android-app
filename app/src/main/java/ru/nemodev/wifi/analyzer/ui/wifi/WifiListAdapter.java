package ru.nemodev.wifi.analyzer.ui.wifi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.nemodev.wifi.analyzer.R;

public class WifiListAdapter extends BaseAdapter {

    private final String SSID = "SSID";
    private final String BSSID = "BSSID";
    private final String RSSI = "RSSI";
    private final String Frequency = "Frequency";
    private final String delimeter = ": ";

    private Context context;
    private List<WifiData> wifiDataList;
    private LayoutInflater layoutInflater;

    public WifiListAdapter(Context context, List<WifiData> wifiDataList) {
        this.context = context;
        this.wifiDataList = wifiDataList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return wifiDataList.size();
    }

    @Override
    public WifiData getItem(int position) {
        return wifiDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.wifi_list_item, parent, false);
        }

        WifiData wifiData = getItem(position);

        LinearLayout linearLayout = view.findViewById(R.id.wifi_info_item_layout);

        for (String wifiParam: prepareWifiData(wifiData)) {
            TextView wifiParamTextView = new TextView(context);
            wifiParamTextView.setText(wifiParam);
            linearLayout.addView(wifiParamTextView);
        }

        return view;
    }

    private List<String> prepareWifiData(WifiData wifiData) {

        List<String> wifiDataPrepared = new ArrayList<>();

        wifiDataPrepared.add(SSID + delimeter + wifiData.getSSID());
        wifiDataPrepared.add(BSSID + delimeter + wifiData.getBSSID());
        wifiDataPrepared.add(RSSI + delimeter + wifiData.getRSSI());
        wifiDataPrepared.add(Frequency + delimeter + wifiData.getFrequency());

        return wifiDataPrepared;
    }
}
