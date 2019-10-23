package ru.nemodev.wifi.analyzer.ui.wifi;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.nemodev.wifi.analyzer.R;
import ru.nemodev.wifi.analyzer.core.device.DeviceManager;
import ru.nemodev.wifi.analyzer.core.network.api.report.WifiAnalyzeReportService;
import ru.nemodev.wifi.analyzer.core.report.ReportLocation;
import ru.nemodev.wifi.analyzer.core.report.WifiAnalyzeReport;

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

    }

    private void sendWifiReport() {

        if (adapter.getItemCount() > 0) {
            WifiAnalyzeReport report = new WifiAnalyzeReport();

            report.setWifiAnalyzeInfoList(adapter.getItems());
            report.setDeviceInfo(DeviceManager.getDeviceInfo(getContext()));
            report.setComment("test");
            report.setLocation(new ReportLocation("1862950d-dae2-4084-94dc-76da15a6a6ad", "test"));

            WifiAnalyzeReportService.getInstance(getContext()).sendReport(report);
        } else {
            Toast.makeText(getContext(), "В отчете нет данных о Wi-Fi сетях!", Toast.LENGTH_LONG).show();
        }
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