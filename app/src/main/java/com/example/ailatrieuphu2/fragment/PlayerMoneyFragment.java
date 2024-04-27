package com.example.ailatrieuphu2.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ailatrieuphu2.CommonUtils;
import com.example.ailatrieuphu2.MediaManager;
import com.example.ailatrieuphu2.R;
import com.example.ailatrieuphu2.databinding.FragmentPlayerMoneyBinding;
import com.example.ailatrieuphu2.dialog.SettingDialog;

public class PlayerMoneyFragment extends BaseFragment<FragmentPlayerMoneyBinding>{
    public static final String TAG = PlayerMoneyFragment.class.getName();
    boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_VOICE_READING);
    @Override
    protected FragmentPlayerMoneyBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentPlayerMoneyBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        addEvents();
    }

    private void addEvents() {
        binding.tvStartGame.setVisibility(View.VISIBLE);
        binding.imgExitPlayerMoney.setOnClickListener(this);
        binding.tvStartGame.setOnClickListener(this);
    }

    @Override
    protected void clickView(View v) {
        super.clickView(v);
        if (v.getId() == R.id.img_exit_player_money) {
//            MediaManager.getInstance().stopSoundGame();
            callBack.showFragment(HomeFragment.TAG, false);
        } else if (v.getId() == R.id.tv_start_game) {
            binding.tvStartGame.setEnabled(false);
            if (!stateOfMusic) {
                callBack.showFragment(GamePlayFragment.TAG, false);
            } else {
                MediaManager.getInstance().setPlaySoundGame(R.raw.play
                        , mp -> callBack.showFragment(GamePlayFragment.TAG, false));
            }
        }
        v.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
    }
}
