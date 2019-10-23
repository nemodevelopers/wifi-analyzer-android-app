package ru.nemodev.wifi.analyzer.ui.wifi;

import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.robertlevonyan.views.chip.Chip;

import java.util.Collections;
import java.util.List;

import ru.nemodev.wifi.analyzer.R;
import ru.nemodev.wifi.analyzer.core.wifi.WifiConstants;
import ru.nemodev.wifi.analyzer.core.wifi.WifiAnalyzeInfo;

public class WifiRecyclerViewAdapter extends RecyclerView.Adapter<WifiRecyclerViewAdapter.WifiViewHolder> {

    private List<WifiAnalyzeInfo> wifiAnalyzeInfoList;

    private static final String BSSID_LABEL = "BSSID: ";
    private static final String RSSI_LABEL = "RSSI: ";
    private static final String FREQUENCY_LABEL = "FREQUENCY: ";

    public WifiRecyclerViewAdapter() {
        this.wifiAnalyzeInfoList = Collections.emptyList();
    }

    public WifiRecyclerViewAdapter(List<WifiAnalyzeInfo> wifiAnalyzeInfoList) {
        this.wifiAnalyzeInfoList = wifiAnalyzeInfoList;
    }

    @NonNull
    @Override
    public WifiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wifi_card_view, parent, false);
        WifiViewHolder wifiViewHolder = new WifiViewHolder(view);
        return wifiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WifiViewHolder holder, int position) {

        WifiAnalyzeInfo wifiAnalyzeInfo = getItem(position);

        holder.ssid.setText(wifiAnalyzeInfo.getSSID());
        holder.bssid.setText(makeOutputText(BSSID_LABEL, wifiAnalyzeInfo.getBSSID()));
        holder.frequency.setText(makeOutputText(FREQUENCY_LABEL, String.valueOf(wifiAnalyzeInfo.getFrequency())));
        holder.rssi.setText(makeOutputText(RSSI_LABEL, ""));

        String rssiValue = wifiAnalyzeInfo.getRSSI() + " " + WifiConstants.RSSI_UNITS;
        holder.rssiChip.setText(rssiValue);
        holder.rssiChip.setChipBackgroundColor(Color.parseColor(wifiAnalyzeInfo.getRssiLevel().getColor()));
    }

    @Override
    public int getItemCount() {
        return wifiAnalyzeInfoList.size();
    }

    public WifiAnalyzeInfo getItem(int position) {
        return wifiAnalyzeInfoList.get(position);
    }

    public List<WifiAnalyzeInfo> getItems() {
        return wifiAnalyzeInfoList;
    }

    static class WifiViewHolder extends RecyclerView.ViewHolder {
        CardView wifiCard;
        TextView ssid;
        TextView bssid;
        TextView frequency;
        TextView rssi;
        Chip rssiChip;

        WifiViewHolder(View itemView) {
            super(itemView);

            wifiCard = itemView.findViewById(R.id.wifi_card);
            ssid = itemView.findViewById(R.id.ssid);
            bssid = itemView.findViewById(R.id.bssid);
            frequency = itemView.findViewById(R.id.freq);
            rssi = itemView.findViewById(R.id.rssi);
            rssiChip = itemView.findViewById(R.id.rssi_chip);
        }
    }

    private Spanned makeOutputText(String label, String data) {
        String text = "<b>" + label + "</b> " + data;
        return Html.fromHtml(text);
    }
}