package com.example.ailatrieuphu2.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ailatrieuphu2.R;
import com.example.ailatrieuphu2.database.entities.ScoreUser;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {
    private final List<ScoreUser> scoreUsersList;
    private final int[] imageArray = {R.drawable.ic_1, R.drawable.ic_2, R.drawable.ic_3,
            R.drawable.ic_4, R.drawable.ic_5, R.drawable.ic_6, R.drawable.ic_7,
            R.drawable.ic_8, R.drawable.ic_9, R.drawable.ic_10};

    public RankingAdapter(List<ScoreUser> scoreUsersList) {
        this.scoreUsersList = scoreUsersList;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rankingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_high_score, parent, false);
        return new RankingViewHolder(rankingView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
        ScoreUser scoreUser = scoreUsersList.get(position);
        if (scoreUser == null) {
            return;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formatNumber = decimalFormat.format(scoreUser.score);
        holder.tvScores.setText(formatNumber + "VND");

        Random random = new Random();
        int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        holder.tvName.setTextColor(color);
        holder.tvName.setText(scoreUser.nameUser);

        holder.imgRank.setImageResource(imageArray[position]);
    }

    @Override
    public int getItemCount() {
        Log.d("Adapter", "getItemCount: " + scoreUsersList.size());
        return scoreUsersList.size();
    }

    public static class RankingViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imgRank;
        private final TextView tvName;
        private final TextView tvScores;
        public RankingViewHolder(View itemView) {
            super(itemView);
            imgRank = itemView.findViewById(R.id.img_rank);
            tvName = itemView.findViewById(R.id.tv_name);
            tvScores = itemView.findViewById(R.id.tv_scores);
        }
    }
}
