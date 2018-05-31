package com.parenting.attendance.presentation.view.adapter;

import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parenting.attendance.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.parenting.attendance.presentation.view.adapter.ReportsAdapter.CHARTS.LINE;
import static com.parenting.attendance.presentation.view.adapter.ReportsAdapter.CHARTS.PIE;

/**
 * Created by adwait on 04/03/18.
 */

public class ReportsAdapter extends RecyclerView.Adapter {


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PIE, LINE})
    public @interface CHARTS {
        int PIE = 1;
        int LINE = 2;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LINE:
                return new LineReportsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_report_linechart_layout,parent,false));
            case PIE:
                return new PieReportsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_report_piechart_layout,parent,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LineReportsViewHolder){
            LineReportsViewHolder lineReportsViewHolder = (LineReportsViewHolder) holder;
        }else if(holder instanceof PieReportsViewHolder){
            PieReportsViewHolder pieReportsViewHolder = (PieReportsViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return LINE;
        } else if (position == 1) {
            return PIE;
        }
        return 0;
    }

    public class LineReportsViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView_section_title;
        public final LineChart lineChart_attendance;

        public LineReportsViewHolder(View itemView) {
            super(itemView);
            textView_section_title = itemView.findViewById(R.id.textView_section_title);
            lineChart_attendance = itemView.findViewById(R.id.lineChart_attendance);
        }
    }

    public class PieReportsViewHolder extends RecyclerView.ViewHolder {

        public final TextView textView_section_title;
        public final PieChart pieChart_students;

        public PieReportsViewHolder(View itemView) {
            super(itemView);
            textView_section_title = itemView.findViewById(R.id.textView_section_title);
            pieChart_students = itemView.findViewById(R.id.pieChart_students);
        }
    }
}
