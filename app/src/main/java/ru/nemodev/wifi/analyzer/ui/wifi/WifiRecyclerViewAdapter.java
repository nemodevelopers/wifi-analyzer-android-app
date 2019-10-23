package ru.nemodev.wifi.analyzer.ui.wifi;

import android.content.Context;
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

import java.util.List;

import ru.nemodev.wifi.analyzer.R;

public class WifiRecyclerViewAdapter extends RecyclerView.Adapter<WifiRecyclerViewAdapter.WifiViewHolder> {

    private List<WifiData> wifiDataList;

    private static final String BSSID_LABEL = "BSSID: ";
    private static final String RSSI_LABEL = "RSSI: ";
    private static final String FREQUENCY_LABEL = "FREQUENCY: ";

    public WifiRecyclerViewAdapter(List<WifiData> wifiDataList) {
        this.wifiDataList = wifiDataList;
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

        WifiData wifiData = getItem(position);

        holder.ssid.setText(wifiData.getSSID());
        holder.bssid.setText(makeOutputText(BSSID_LABEL, wifiData.getBSSID()));
        holder.frequency.setText(makeOutputText(FREQUENCY_LABEL, String.valueOf(wifiData.getFrequency())));
        holder.rssi.setText(makeOutputText(RSSI_LABEL, ""));

        String rssiValue = wifiData.getRSSI() + " " + WifiConstants.RSSI_UNITS;
        holder.rssiChip.setText(rssiValue);
        holder.rssiChip.setChipBackgroundColor(Color.parseColor(wifiData.getRssiLevel().getColor()));
    }

    @Override
    public int getItemCount() {
        return wifiDataList.size();
    }

    public WifiData getItem(int position) {
        return wifiDataList.get(position);
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
