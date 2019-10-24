package ru.nemodev.wifi.analyzer.ui.speed;

import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.pwittchen.reactivewifi.WifiState;
import com.robertlevonyan.views.chip.Chip;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.nemodev.wifi.analyzer.R;
import ru.nemodev.wifi.analyzer.core.entity.speed.SpeedTest;
import ru.nemodev.wifi.analyzer.core.wifi.RssiLevel;
import ru.nemodev.wifi.analyzer.core.wifi.WifiConstants;
import ru.nemodev.wifi.analyzer.utils.AndroidUtils;


public class SpeedFragment extends Fragment {

    private SpeedViewModel speedViewModel;
    private static float mbit = 8 * 1024 * 1024;

    private View rootView;

    @BindView(R.id.ssid) TextView ssid;
    @BindView(R.id.bssid) TextView bssid;
    @BindView(R.id.freq) TextView freq;
    @BindView(R.id.rssi_chip) Chip rssiChip;

    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.speed_test_result) TextView speedTestResult;
    @BindView(R.id.speed_test_btn) Button speedTestBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_speed, container, false);
        ButterKnife.bind(this, rootView);
        speedViewModel = ViewModelProviders.of(this.getActivity()).get(SpeedViewModel.class);

        speedViewModel.getWifiStatus().observe(this, wifiState -> {
            if (wifiState == WifiState.ENABLED) {
                speedTestBtn.setVisibility(View.VISIBLE);
            }
            else if (wifiState == WifiState.DISABLED || wifiState == WifiState.UNKNOWN){
                speedTestBtn.setVisibility(View.GONE);
                showInfoDialog("Для теста скорости необходимо подключиться к wifi!");
            }
        });

        speedViewModel.getActiveWifi().observe(this, wifiInfoEntityWrapper -> {

            WifiInfo wifiInfo = wifiInfoEntityWrapper.getEntity();
            ssid.setText("Активный wifi - " + wifiInfo.getSSID().replace("\"", ""));
            bssid.setText(wifiInfo.getBSSID());
            freq.setText(wifiInfo.getFrequency() < 4500 ? "2 GHz" : "5 GHz");

            String rssiValue = wifiInfo.getRssi() + " " + WifiConstants.RSSI_UNITS;
            rssiChip.setText(rssiValue);
            rssiChip.setChipBackgroundColor(Color.parseColor(
                    RssiLevel.defineRssiLevel(wifiInfo.getRssi()).getColor()));

            speedTestResult.setText("");
        });

        speedViewModel.getSpeedTestData().observe(this, speedTestWrapper -> {
            if (speedTestWrapper.getErrorMessage() != null) {
                showInfoDialog(speedTestWrapper.getErrorMessage());
                speedTestBtn.setVisibility(View.VISIBLE);
            }
            else {
                SpeedTest speedTest = speedTestWrapper.getEntity();
                progressBar.setProgress(Math.round(speedTest.getReport().getProgressPercent()));
                if (progressBar.getProgress() > 0) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                String speed = String.format("%.2f", speedTest.getReport().getTransferRateBit().floatValue() / mbit)  + " Mb/sec";
                speedTestResult.setText("Скорость " + speed);
                if (!speedTest.isProgress()) {
                    speedTestBtn.setVisibility(View.VISIBLE);
                    AndroidUtils.showSnackBarMessageShort(rootView, "Тест скорости завершен!");
                }
            }
        });

        speedTestBtn.setOnClickListener(v -> {
            speedTestBtn.setVisibility(View.GONE);
            progressBar.setProgress(0);
            progressBar.setVisibility(View.VISIBLE);
            speedViewModel.testSpeed();
        });

        return rootView;
    }

    private void showInfoDialog(String message) {
        new AlertDialog.Builder(getActivity())
            .setMessage(message)
            .setPositiveButton("OK", (dialog, id) -> { })
            .create().show();
    }
}