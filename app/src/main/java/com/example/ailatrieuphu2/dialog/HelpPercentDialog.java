package com.example.ailatrieuphu2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.ailatrieuphu2.Constants;
import com.example.ailatrieuphu2.OnDialogListener;
import com.example.ailatrieuphu2.databinding.DialogHelpPercentBinding;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HelpPercentDialog extends Dialog {
    public DialogHelpPercentBinding binding;
    private OnDialogListener onDialogListener;
    public void setOnDialogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }
    public HelpPercentDialog(@NonNull Context context) {
        super(context);
        binding = DialogHelpPercentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configureChart();
        handleThanks();
    }

    private void handleThanks() {
        binding.btThanks.setOnClickListener(view -> {
            dismiss();
            if (onDialogListener != null) {
                onDialogListener.onDialogClosed();
            }
        });
    }

    private void configureChart() {
        XAxis xAxis = binding.barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(Constants.ANSWER_ARRAY));
        xAxis.setTextColor(Color.WHITE);

        binding.barChart.setFitBars(true);
        binding.barChart.getAxisLeft().setEnabled(false);
        binding.barChart.getAxisRight().setEnabled(false);
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.getLegend().setEnabled(false);
        binding.barChart.setDragEnabled(false);
        binding.barChart.setDragEnabled(false);
        binding.barChart.setDragEnabled(false);
        binding.barChart.animateY(6000);
    }

    public void updateChartData(int idAnswerTrue, List<Integer> listAnswerHidden) {
        float percentA, percentB, percentC, percentD;
        float remainingPercent = Constants.TOTAL_PERCENT;

        // tính toán % cho mối câu trả lời
        // câu trả lời đúng luôn > 3 câu sai
        switch (idAnswerTrue) {
            case 1:
                percentA = getRandomPercent(50, 60);
                remainingPercent -= percentA;
                percentB = getRandomPercent(0, remainingPercent);
                remainingPercent -= percentB;
                percentC = getRandomPercent(0, remainingPercent);
                percentD = Constants.TOTAL_PERCENT - percentA - percentB - percentC;
                break;
            case 2:
                percentB = getRandomPercent(45, 60);
                remainingPercent -= percentB;
                percentA = getRandomPercent(0, remainingPercent);
                remainingPercent -= percentA;
                percentC = getRandomPercent(0, remainingPercent);
                percentD = Constants.TOTAL_PERCENT - percentA - percentB - percentC;
                break;
            case 3:
                percentC = getRandomPercent(50, 60);
                remainingPercent -= percentC;
                percentA = getRandomPercent(0, remainingPercent);
                remainingPercent -= percentA;
                percentB = getRandomPercent(0, remainingPercent);
                percentD = Constants.TOTAL_PERCENT - percentA - percentB - percentC;
                break;
            default:
                percentD = getRandomPercent(50, 60);
                remainingPercent -= percentD;
                percentA = getRandomPercent(0, remainingPercent);
                remainingPercent -= percentA;
                percentB = getRandomPercent(0, remainingPercent);
                percentC = Constants.TOTAL_PERCENT - percentA - percentB - percentD;
                break;
        }
        // kiem tra xem list ans co null khong
        if (!listAnswerHidden.isEmpty()) {
            switch (idAnswerTrue) {
                case 1:
                    percentA = getRandomPercent(50, 100);
                    percentB = Constants.TOTAL_PERCENT - percentA;
                    percentC = Constants.TOTAL_PERCENT - percentA;
                    percentD = Constants.TOTAL_PERCENT - percentA;
                    break;
                case 2:
                    percentB = getRandomPercent(50, 70);
                    percentA = Constants.TOTAL_PERCENT - percentB;
                    percentC = Constants.TOTAL_PERCENT - percentB;
                    percentD = Constants.TOTAL_PERCENT - percentB;
                    break;
                case 3:
                    percentC = getRandomPercent(50, 60);
                    percentA = Constants.TOTAL_PERCENT - percentC;
                    percentB = Constants.TOTAL_PERCENT - percentC;
                    percentD = Constants.TOTAL_PERCENT - percentC;
                    break;
                default:
                    percentD = getRandomPercent(50, 65);
                    percentA = Constants.TOTAL_PERCENT - percentD;
                    percentB = Constants.TOTAL_PERCENT - percentD;
                    percentC = Constants.TOTAL_PERCENT - percentD;
                    break;
            }
        }

        //BarEntry to store data for each column
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, percentA));
        entries.add(new BarEntry(1f, percentB));
        entries.add(new BarEntry(2f, percentC));
        entries.add(new BarEntry(3f, percentD));

        //remove hidden ans
        for (int answerIndex : listAnswerHidden) {
            entries.get(answerIndex - 1).setY(0f);
        }

        //BarDataset to store data and configure for the columns of chart
        BarDataSet dataSet = new BarDataSet(entries, "Đáp án");
        dataSet.setValueTextColors(Collections.singletonList(Color.WHITE));
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setValueTextSize(12f);
        dataSet.setBarBorderWidth(0.8f);

        //BarData containing the configure BarDataSet
        BarData barData = new BarData(dataSet);
        binding.barChart.setData(barData);
        binding.barChart.invalidate();

        new Handler().postDelayed(() -> binding.btThanks.setVisibility(View.VISIBLE), 6000);
    }

    private float getRandomPercent(float min, float max) {
        Random random = new Random();
        return random.nextInt((int) (max - min + 1)) + min;
    }
}
