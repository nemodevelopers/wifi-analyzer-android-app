package ru.nemodev.wifi.analyzer.ui.wifi;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.nemodev.wifi.analyzer.R;
import ru.nemodev.wifi.analyzer.core.device.DeviceManager;
import ru.nemodev.wifi.analyzer.core.network.api.report.WifiAnalyzeReportService;
import ru.nemodev.wifi.analyzer.core.network.dto.wifi.report.WifiAnalyzeReportDto;
import ru.nemodev.wifi.analyzer.core.report.ReportLocation;
import ru.nemodev.wifi.analyzer.core.report.WifiAnalyzeReport;
import ru.nemodev.wifi.analyzer.utils.AndroidUtils;

public class WifiFragment extends Fragment {

    private View rootView;
    @BindView(R.id.scan_btn) Button scanBtn;
    @BindView(R.id.send_report_btn) Button sendReportBtn;

    private ProgressDialog progressDialog;
    private WifiViewModel wifiViewModel;
    private RecyclerView wifiInfoList;
    private WifiRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        wifiViewModel = ViewModelProviders.of(this.getActivity()).get(WifiViewModel.class);
        rootView = inflater.inflate(R.layout.fragment_wifi, container, false);
        ButterKnife.bind(this, rootView);

        initRecyclerView();
        initProgressDialog();

        scanBtn.setOnClickListener(v -> scanWifi());
        sendReportBtn.setOnClickListener(v -> sendWifiReport());

        requestAccessCoarseLocationPermission();

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (requestCode == 123) {
            if (results[0] == PackageManager.PERMISSION_GRANTED) {
                observeScanResult();
            } else {
                AndroidUtils.showSnackBarMessageShort(this.getView(), "Без согласия не получить список WI-FI сетей");
                requestAccessCoarseLocationPermission();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, results);
        }
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Сканирование...");
    }

    private void observeScanResult() {
        wifiViewModel.getScanResult().observe(this, wifiDataList -> {

            adapter = new WifiRecyclerViewAdapter(wifiDataList);
            wifiInfoList.setAdapter(adapter);

            progressDialog.dismiss();
        });
    }

    private void scanWifi() {
        progressDialog.show();
        wifiViewModel.scanWifi();
    }

    private void sendWifiReport() {

        if (adapter.getItemCount() > 0) {
            WifiAnalyzeReport report = new WifiAnalyzeReport();

            report.setWifiAnalyzeInfoList(adapter.getItems());
            report.setDeviceInfo(DeviceManager.getDeviceInfo());
            report.setComment("test");
            report.setLocation(new ReportLocation("1862950d-dae2-4084-94dc-76da15a6a6ad", "test"));

            WifiAnalyzeReportService.getInstance().sendReport(report)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<WifiAnalyzeReportDto>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(WifiAnalyzeReportDto wifiAnalyzeReportDto) {
                            AndroidUtils.showSnackBarMessageLong(rootView,
                                    "Отчет успешно отправлен!");
                        }

                        @Override
                        public void onError(Throwable e) {
                            AndroidUtils.showSnackBarMessageLong(rootView,
                                    "Произошла ошибка попробуйте отправить отчет позже!");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            AndroidUtils.showSnackBarMessageLong(rootView, "В отчете нет данных о Wi-Fi сетях!");
        }
    }

    private void initRecyclerView() {
        wifiInfoList = rootView.findViewById(R.id.wifi_info_list);
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
        else {
            observeScanResult();
            scanWifi();
        }
    }
}