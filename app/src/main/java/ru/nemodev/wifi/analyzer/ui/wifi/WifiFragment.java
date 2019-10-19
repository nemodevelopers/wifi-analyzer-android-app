package ru.nemodev.wifi.analyzer.ui.wifi;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import ru.nemodev.wifi.analyzer.R;

public class WifiFragment extends Fragment {

    private WifiViewModel wifiViewModel;
    private ListView wifiInfoList;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        wifiViewModel = ViewModelProviders.of(this).get(WifiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_wifi, container, false);

        wifiInfoList = root.findViewById(R.id.wifi_info_list);
        progressBar = root.findViewById(R.id.progressbar);

        Button scanBtn = root.findViewById(R.id.scan_btn);
        scanBtn.setOnClickListener(v -> getWifi());

        String location = android.Manifest.permission.ACCESS_COARSE_LOCATION;
        if (ActivityCompat.checkSelfPermission(getContext(), location) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] { location }, 123);
        }

        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (requestCode == 123) {
            if (results[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                // user rejected permission request
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, results);
        }
    }

    private void getWifi() {
        progressBar.setVisibility(View.VISIBLE);

        wifiViewModel.getWifi(getContext()).observe(this, wifiDataList -> {

            // TODO adapter
            ArrayList<String> wifiList = new ArrayList<>();
            for (WifiData wifiData: wifiDataList) {

                String sb = "SSID:" + wifiData.getSSID() + "\n" +
                        "BSSID:" + wifiData.getBSSID() + "\n" +
                        "RSSI:" + wifiData.getRSSI() + "\n" +
                        "Frequency:" + wifiData.getFrequency();

                wifiList.add(sb);
            }

            progressBar.setVisibility(View.GONE);
            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, wifiList.toArray());
            wifiInfoList.setAdapter(arrayAdapter);
        });
    }
}