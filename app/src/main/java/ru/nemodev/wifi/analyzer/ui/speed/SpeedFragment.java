package ru.nemodev.wifi.analyzer.ui.speed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.nemodev.wifi.analyzer.R;
import ru.nemodev.wifi.analyzer.core.entity.speed.SpeedTest;


public class SpeedFragment extends Fragment {

    private SpeedViewModel speedViewModel;

    View rootView;
    @BindView(R.id.speed_test_result) TextView speedTestResult;
    @BindView(R.id.speed_test_btn) Button speedTestBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_speed, container, false);
        ButterKnife.bind(this, rootView);
        speedViewModel = ViewModelProviders.of(this.getActivity()).get(SpeedViewModel.class);

        speedViewModel.getSpeedTestData().observe(this, speedTestWrapper -> {
            if (speedTestWrapper.getErrorMessage() != null) {
                speedTestResult.setText(speedTestWrapper.getErrorMessage());
            }
            else {
                // TODO перевод из бит в мб + доделать вьюшку
                SpeedTest speedTest = speedTestWrapper.getEntity();
                speedTestResult.setText(String.valueOf(speedTest.getReport().getTransferRateBit()));
            }
        });
        speedTestBtn.setOnClickListener(v -> speedViewModel.testSpeed());

        return rootView;
    }
}