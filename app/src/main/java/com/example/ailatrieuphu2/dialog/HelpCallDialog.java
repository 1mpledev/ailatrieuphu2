package com.example.ailatrieuphu2.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.ailatrieuphu2.CommonUtils;
import com.example.ailatrieuphu2.Constants;
import com.example.ailatrieuphu2.MediaManager;
import com.example.ailatrieuphu2.OnDialogListener;
import com.example.ailatrieuphu2.R;
import com.example.ailatrieuphu2.databinding.DialogHelpCallBinding;

public class HelpCallDialog extends Dialog {
    private final DialogHelpCallBinding binding;
    boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_VOICE_READING);
    private OnDialogListener onDialogListener;
    private String answerHelpCall;
    public void setOnDialogListener(OnDialogListener onDialogListener){
        this.onDialogListener = onDialogListener;
    }

    public HelpCallDialog(@NonNull Context context) {
        super(context);
        binding = DialogHelpCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

    public void getAnsCall(int idAnsTrue) {
        if (!stateOfMusic) {
            showAns(idAnsTrue);
        } else {
            MediaManager.getInstance().setPlaySoundGame(R.raw.time_call, mp -> showAns(idAnsTrue));
        }
    }

    @SuppressLint("SetTextI18n")
    private void showAns(int idAnsTrue) {
        answerHelpCall = Constants.ANSWER_ARRAY[idAnsTrue - 1];
        if (!stateOfMusic) {
            binding.tvAnswerCall.setText("Đáp án là: " + answerHelpCall);
            binding.btThanks.setVisibility(View.VISIBLE);
        } else {
            MediaManager.getInstance().setPlaySoundGame(MediaManager.HELP_CALL[idAnsTrue - 1], mp -> {
                binding.tvAnswerCall.setText("Đáp án là: " + answerHelpCall);
                binding.btThanks.setVisibility(View.VISIBLE);
            } );
        }
    }
}
