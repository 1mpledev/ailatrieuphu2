package com.example.ailatrieuphu2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.ailatrieuphu2.CommonUtils;
import com.example.ailatrieuphu2.MediaManager;
import com.example.ailatrieuphu2.databinding.DialogSettingBinding;

public class SettingDialog extends Dialog {
    public DialogSettingBinding binding;
    public static final String STATE_BACKGROUND = "STATE_BACKGROUND";
    public static final String STATE_VOICE_READING = "STATE_VOICE_READING";
    public static final String STATE_LIGHT_MODE = "STATE_LIGHT_MODE";

    public SettingDialog(@NonNull Context context) {
        super(context);
        binding = DialogSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        checkStateBG();
        checkStateVoiceReading();
        checkLightMode();
        addEvent();
    }

    private void addEvent() {
        clickEventBG();
        clickEventVoiceReading();
        clickEventLightMode();
    }

    private void clickEventVoiceReading() {
        binding.imgOnVoice.setOnClickListener(v -> {
            binding.imgOnVoice.setImageLevel(binding.imgOnVoice.getDrawable().getLevel() == 0 ? 1 : 0);
            if (binding.imgOnVoice.getDrawable().getLevel() == 0) {
                MediaManager.getInstance().resumeSound();
                CommonUtils.getInstance().savePref(STATE_VOICE_READING, true);
            } else {
                MediaManager.getInstance().pauseSoundGame();
                CommonUtils.getInstance().savePref(STATE_VOICE_READING, false);
            }
        });
    }

    private void checkStateVoiceReading() {
        boolean stateOfRead = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_VOICE_READING);
        if (stateOfRead) {
            binding.imgOnVoice.setImageLevel(0);
        } else {
            binding.imgOnVoice.setImageLevel(1);
        }
    }

    private void checkStateBG() {
        boolean stateOfBG = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_BACKGROUND);
        if (stateOfBG) {
            binding.imgOnLightMode.setImageLevel(0);
        } else {
            binding.imgOnLightMode.setImageLevel(1);
        }
    }
    private void clickEventBG() {
        binding.imgOnMusic.setImageLevel(binding.imgOnMusic.getDrawable().getLevel() == 0 ? 1 : 0);
        if (binding.imgOnMusic.getDrawable().getLevel() == 0) {
            MediaManager.getInstance().resumeBG();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (binding.imgOnMusic.getDrawable().getLevel() == 1) {
            MediaManager.getInstance().pauseBG();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    private void checkLightMode() {
        boolean stateOfLightMode = CommonUtils.getInstance().getPrefDefaultFalse(SettingDialog.STATE_LIGHT_MODE);
        if (!stateOfLightMode) {
            binding.imgOnLightMode.setImageLevel(0);
        } else {
            binding.imgOnLightMode.setImageLevel(1);
        }
    }
    private void clickEventLightMode() {
        binding.imgOnLightMode.setImageLevel(binding.imgOnLightMode.getDrawable().getLevel() == 0 ? 1 : 0);
        if (binding.imgOnLightMode.getDrawable().getLevel() == 0) {
            CommonUtils.getInstance().savePref(STATE_LIGHT_MODE, false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (binding.imgOnLightMode.getDrawable().getLevel() == 1) {
            CommonUtils.getInstance().savePref(STATE_LIGHT_MODE, true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
