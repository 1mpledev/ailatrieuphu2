package com.example.ailatrieuphu2.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ailatrieuphu2.R;
import com.example.ailatrieuphu2.App;
import com.example.ailatrieuphu2.adapter.RankingAdapter;
import com.example.ailatrieuphu2.database.entities.ScoreUser;
import com.example.ailatrieuphu2.databinding.FragmentRankingBinding;

import java.util.List;
import android.widget.ListAdapter;

public class RankingFragment extends BaseFragment<FragmentRankingBinding>{
    public static final String TAG = RankingFragment.class.getName();
    private List<ScoreUser> scoreUserList;
    private RankingAdapter rankingAdapter;
    @Override
    protected FragmentRankingBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentRankingBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        initAdapter();
        addEvents();
    }

    private void initAdapter() {
        new Thread(() -> {
            scoreUserList = App.getInstance().getDatabase().getScoreUserDAO().getAllScoreUser();
            requireActivity().runOnUiThread(() -> {
                rankingAdapter = new RankingAdapter(scoreUserList);
                binding.lvHighScores.setAdapter(rankingAdapter);
                Log.i(RankingFragment.TAG, scoreUserList.toString() + scoreUserList.size());
            });
        }).start();
    }

    protected void clickView(View v) {
        super.clickView(v);
        v.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
        callBack.showFragment(HomeFragment.TAG, false);
    }

    private void addEvents() {
        binding.imgExitRank.setOnClickListener(this);
    }

}
