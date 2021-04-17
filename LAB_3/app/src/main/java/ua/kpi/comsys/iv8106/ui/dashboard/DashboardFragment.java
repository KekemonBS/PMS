package ua.kpi.comsys.iv8106.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.widget.CompoundButton;
import android.widget.Switch;

import ua.kpi.comsys.iv8106.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        View pie = root.findViewById(R.id.pie);
        View graph = root.findViewById(R.id.line);
        graph.setVisibility(View.GONE);

        Switch switch_graph  = (Switch) root.findViewById(R.id.switch1);
        switch_graph.setShowText(true);
        switch_graph.setTextOn("Графік");
        switch_graph.setTextOff("Діаграма");

        switch_graph.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showLine(pie, graph);
                } else {
                    showPie(pie, graph);
                }
            }
        });
        return root;
    }
    public void showPie(View pie, View graph) {
        graph.setVisibility(View.INVISIBLE);
        pie.setVisibility(View.VISIBLE);
    }

    public void showLine(View pie, View graph) {
        pie.setVisibility(View.INVISIBLE);
        graph.setVisibility(View.VISIBLE);
    }
}