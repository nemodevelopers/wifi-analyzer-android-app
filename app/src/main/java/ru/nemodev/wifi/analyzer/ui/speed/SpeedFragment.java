package ru.nemodev.wifi.analyzer.ui.speed;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

import ru.nemodev.wifi.analyzer.R;

public class SpeedFragment extends Fragment {

    private SpeedTestSocket speedTestSocket;

    /**
     * socket timeout used in ms.
     */
    private final static int SOCKET_TIMEOUT = 5000;

    /**
     * spedd examples server uri.
     */
    private final static String SPEED_TEST_SERVER_URI_DL = "http://ipv4.ikoula.testdebit.info/10M.iso";

    private SpeedViewModel speedViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        speedViewModel =
                ViewModelProviders.of(this).get(SpeedViewModel.class);


        View root = inflater.inflate(R.layout.fragment_speed, container, false);
        final TextView speedTest = root.findViewById(R.id.speed_test);

        speedTestSocket = new SpeedTestSocket();

        speedTestSocket.setSocketTimeout(SOCKET_TIMEOUT);

        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

            @Override
            public void onCompletion(final SpeedTestReport report) {
                speedTest.setText(String.valueOf(report.getTransferRateBit()));
            }

            @Override
            public void onError(final SpeedTestError speedTestError, final String errorMessage) {
                speedTest.setText(errorMessage);
            }

            @Override
            public void onProgress(final float percent, final SpeedTestReport downloadReport) {
                speedTest.setText(String.valueOf(percent));
            }
        });

        Button speedTestBtn = root.findViewById(R.id.speed_test_btn);

        speedTestBtn.setOnClickListener(v -> new SpeedTestTask().execute());


        return root;
    }

    private class SpeedTestTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            speedTestSocket.startDownload(SPEED_TEST_SERVER_URI_DL);
            return null;
        }
    }
}