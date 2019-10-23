package ru.nemodev.wifi.analyzer.ui.wifi;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import ru.nemodev.wifi.analyzer.R;
import ru.nemodev.wifi.analyzer.core.entity.location.Location;
import ru.nemodev.wifi.analyzer.core.service.location.LocationService;

public class WifiFragment extends Fragment {

    private WifiViewModel wifiViewModel;
    private RecyclerView wifiInfoList;
    private WifiRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        wifiViewModel = ViewModelProviders.of(this).get(WifiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_wifi, container, false);

        initRecyclerView(root);

        Button scanBtn = root.findViewById(R.id.scan_btn);
        scanBtn.setOnClickListener(v -> getWifi());

        Button sendReportBtn = root.findViewById(R.id.send_report_btn);
        sendReportBtn.setOnClickListener(v -> sendWifiReport());

        requestAccessCoarseLocationPermission();

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

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Сканирование...");
        progressDialog.show();

        wifiViewModel.getWifi(getContext()).observe(this, wifiDataList -> {

            adapter = new WifiRecyclerViewAdapter(wifiDataList);
            wifiInfoList.setAdapter(adapter);

            progressDialog.dismiss();
        });

        // TODO пример получения локаций
        LocationService.INSTANCE.getList()
                .subscribe(new Observer<List<Location>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Location> locations) {
                        Log.d("123", locations.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("123", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void sendWifiReport() {

    }

    private void initRecyclerView(View root) {
        wifiInfoList = root.findViewById(R.id.wifi_info_list);
        wifiInfoList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        wifiInfoList.setLayoutManager(llm);

        adapter = new WifiRecyclerViewAdapter();
        wifiInfoList.setAdapter(adapter);
    }

    private void requestAccessCoarseLocationPermission() {
        String location = android.Manifest.permission.ACCESS_COARSE_LOCATION;
        if (ActivityCompat.checkSelfPermission(getContext(), location) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] { location }, 123);
        }
    }
}