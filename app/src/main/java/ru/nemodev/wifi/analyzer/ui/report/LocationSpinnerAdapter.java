package ru.nemodev.wifi.analyzer.ui.report;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import ru.nemodev.wifi.analyzer.R;
import ru.nemodev.wifi.analyzer.core.report.ReportLocation;

public class LocationSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context context;
    private List<ReportLocation> locations;

    public LocationSpinnerAdapter(Context context, List<ReportLocation> locations) {
        this.context = context;
        this.locations = locations;
    }

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public ReportLocation getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = View.inflate(context, R.layout.location_spinner_item, null);

        TextView locationName = itemView.findViewById(R.id.location_name);
        locationName.setText(getItem(position).getName());
        
        return itemView;
    }
}
