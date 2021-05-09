package ua.kpi.comsys.iv8106.ui.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import ua.kpi.comsys.iv8106.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PieChartFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_piechart, container, false);

        PieChart pieChart = (PieChart) root.findViewById(R.id.pie);
        showPieChart(pieChart);
        return root;
    }

    private void showPieChart(PieChart pieChart){

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        typeAmountMap.put("1",30);
        typeAmountMap.put("2",30);
        typeAmountMap.put("3",40);


        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#ffaa00"));
        colors.add(Color.parseColor("#00aa00"));
        colors.add(Color.parseColor("#000000"));



        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(false); //remove values
        pieChart.getDescription().setEnabled(false); //remove description
        pieChart.getLegend().setEnabled(false); //remove legend
        pieChart.setDrawEntryLabels(false); //remove keys

        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
