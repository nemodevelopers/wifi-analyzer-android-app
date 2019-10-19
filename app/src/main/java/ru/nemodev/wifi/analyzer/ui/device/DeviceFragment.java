package ru.nemodev.wifi.analyzer.ui.device;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import ru.nemodev.wifi.analyzer.R;

public class DeviceFragment extends Fragment {

    private ListView deviceInfoListView;
    private DeviceViewModel deviceViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        deviceViewModel = ViewModelProviders.of(this).get(DeviceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_device, container, false);

        deviceInfoListView = root.findViewById(R.id.device_info_list);
        deviceViewModel.getDeviceInfo().observe(this, deviceInfo -> {
            ArrayList<String> deviceInfoList = new ArrayList<>();
            deviceInfoList.add("Device: " + deviceInfo.getDevice());
            deviceInfoList.add("Model: " + deviceInfo.getModel());
            deviceInfoList.add("Product: " + deviceInfo.getProduct());
            deviceInfoList.add("Version: " + deviceInfo.getVersion());

            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, deviceInfoList.toArray());
            deviceInfoListView.setAdapter(arrayAdapter);
        });

        return root;
    }
}