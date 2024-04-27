package com.example.ailatrieuphu2.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ailatrieuphu2.CommonUtils;
import com.example.ailatrieuphu2.MediaManager;
import com.example.ailatrieuphu2.R;
import com.example.ailatrieuphu2.databinding.FragmentHomeBinding;
import com.example.ailatrieuphu2.dialog.SettingDialog;


public class HomeFragment extends BaseFragment<FragmentHomeBinding>{
    public static final String TAG = HomeFragment.class.getName();

    @Override
    protected FragmentHomeBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        MediaManager.getInstance().setPlayBG(R.raw.bgmusic);
        addEvents();
    }
    public void onStart(){super.onStart();}

    public void onResume() {
        super.onResume();
        boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_BACKGROUND);
        if (stateOfMusic) {
            MediaManager.getInstance().pauseBG();
        }
    }

    private void addEvents() {
        binding.btnPlay.setOnClickListener(this);
        binding.btnShop.setOnClickListener(this);
        binding.btnRank.setOnClickListener(this);
        binding.btnSetting.setOnClickListener(this);
        binding.btnExit.setOnClickListener(this);
    }

    protected void clickView(View v) {
        super.clickView(v);
        if (v.getId() == R.id.btn_play) {
            showPlayerMoneyFragment();
        } else if (v.getId() == R.id.btn_shop) {
            showShopFragment();
        } else if (v.getId() == R.id.btn_rank) {
            showRankFragment();
        } else if (v.getId() == R.id.btn_setting) {
            showSettingDialog();
        } else if (v.getId() == R.id.btn_exit) {
            showExitDialog();
        }
        v.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
    }

    private void showPlayerMoneyFragment() {
        MediaManager.getInstance().stopBG();
        callBack.showFragment(PlayerMoneyFragment.TAG, false);
    }

    private void showExitDialog() {
        MediaManager.getInstance().setPlaySoundGame(R.raw.ping, null);
        callBack.showFragment(HomeFragment.TAG, false);}

    private void showSettingDialog() {
        MediaManager.getInstance().setPlaySoundGame(R.raw.ping, null);
        callBack.showFragment(HomeFragment.TAG, false);}

    private void showRankFragment() {
        MediaManager.getInstance().setPlaySoundGame(R.raw.ping, null);
        callBack.showFragment(RankingFragment.TAG, false);
    }

    private void showShopFragment() {
        MediaManager.getInstance().setPlaySoundGame(R.raw.ping, null);
        callBack.showFragment(HomeFragment.TAG, false);
    }
}
