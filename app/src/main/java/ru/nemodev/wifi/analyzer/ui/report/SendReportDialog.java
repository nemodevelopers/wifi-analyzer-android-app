package ru.nemodev.wifi.analyzer.ui.report;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.nemodev.wifi.analyzer.R;
import ru.nemodev.wifi.analyzer.core.device.DeviceManager;
import ru.nemodev.wifi.analyzer.core.network.api.report.WifiAnalyzeReportService;
import ru.nemodev.wifi.analyzer.core.network.dto.wifi.report.WifiAnalyzeReportDto;
import ru.nemodev.wifi.analyzer.core.report.ReportLocation;
import ru.nemodev.wifi.analyzer.core.report.WifiAnalyzeReport;
import ru.nemodev.wifi.analyzer.core.service.location.LocationService;
import ru.nemodev.wifi.analyzer.core.wifi.WifiAnalyzeInfo;
import ru.nemodev.wifi.analyzer.utils.AndroidUtils;

public class SendReportDialog extends BottomSheetDialogFragment {

    private Context context;
    private List<WifiAnalyzeInfo> wifiAnalyzeInfoList;

    private View rootView;
    private RelativeLayout reportDialogContent;
    private Spinner locationSpinner;
    private EditText commentEditText;
    private Button closeDialogBtn;
    private Button sendReportBtn;
    private ProgressBar progressBar;

    private SendReportDialog(Context context, View rootView, List<WifiAnalyzeInfo> wifiAnalyzeInfoList) {
        this.context = context;
        this.rootView = rootView;
        this.wifiAnalyzeInfoList = wifiAnalyzeInfoList;
        this.setCancelable(false);
    }

    public static SendReportDialog init(Context context, View rootView, List<WifiAnalyzeInfo> wifiAnalyzeInfoList) {
        return new SendReportDialog(context, rootView, wifiAnalyzeInfoList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reportDialogContent = view.findViewById(R.id.report_dialog_content);

        progressBar = view.findViewById(R.id.report_dialog_progress_bar);

        locationSpinner = view.findViewById(R.id.location_spinner);

        commentEditText = view.findViewById(R.id.report_comment);

        closeDialogBtn = view.findViewById(R.id.close_report_dialog_btn);
        sendReportBtn = view.findViewById(R.id.send_report_btn);

        closeDialogBtn.setOnClickListener(v -> dismiss());
        sendReportBtn.setOnClickListener(v -> sendReport());

        progressBar.setVisibility(View.VISIBLE);
        reportDialogContent.setVisibility(View.GONE);

        initLocationSpinner();
    }


    private void initLocationSpinner() {
        LocationService locationService = new LocationService();
        Observable<List<ReportLocation>> locationsObservable = locationService.getList();

        locationsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ReportLocation>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ReportLocation> reportLocations) {
                        LocationSpinnerAdapter spinnerAdapter = new LocationSpinnerAdapter(context, reportLocations);
                        locationSpinner.setAdapter(spinnerAdapter);

                        progressBar.setVisibility(View.GONE);
                        reportDialogContent.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void sendReport() {
        WifiAnalyzeReport report = new WifiAnalyzeReport();

        report.setWifiAnalyzeInfoList(wifiAnalyzeInfoList);
        report.setDeviceInfo(DeviceManager.getDeviceInfo());
        report.setComment(commentEditText.getText().toString());
        report.setLocation((ReportLocation) locationSpinner.getSelectedItem());

        WifiAnalyzeReportService.getInstance().sendReport(report);


        WifiAnalyzeReportService.getInstance().sendReport(report)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WifiAnalyzeReportDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WifiAnalyzeReportDto wifiAnalyzeReportDto) {
                        AndroidUtils.showSnackBarMessageLong(rootView,
                                "Отчет отправлен");
                    }

                    @Override
                    public void onError(Throwable e) {
                        AndroidUtils.showSnackBarMessageLong(rootView,
                                "Произошла ошибка при отправке отчета");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        dismiss();
    }
}
