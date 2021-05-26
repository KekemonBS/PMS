package ua.kpi.comsys.iv8106.ui.line;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ua.kpi.comsys.iv8106.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class LineFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_linegraph, container, false);

        LineChart lineChart = (LineChart) root.findViewById(R.id.line);
        showLineChart(lineChart);
        return root;
    }

    private void showLineChart(LineChart lineChart){

        ArrayList<Entry> lineEntries= new ArrayList<Entry>();
        for (float x = -6; x < 6; x += 0.01) {
            lineEntries.add( new Entry(x, (float)Math.pow(Math.E, x)));
        }

        LineDataSet lineDataSet  = new LineDataSet(lineEntries, "");

        LineData lineData = new LineData(lineDataSet);

        lineChart.setDrawMarkers(false);
        lineChart.getXAxis().setGranularityEnabled(false);
        lineChart.getDescription().setEnabled(false); //remove description
        lineChart.getLegend().setEnabled(false); //remove legend

        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}
