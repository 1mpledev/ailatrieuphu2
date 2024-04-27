package com.example.ailatrieuphu2.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.ailatrieuphu2.Constants;
import com.example.ailatrieuphu2.R;
import com.example.ailatrieuphu2.CommonUtils;
import com.example.ailatrieuphu2.MediaManager;
import com.example.ailatrieuphu2.OnDialogListener;
import com.example.ailatrieuphu2.databinding.DialogHelpAskHereBinding;

public class HelpAskHereDialog extends Dialog {
    public final DialogHelpAskHereBinding binding;
    private OnDialogListener onDialogListener;
    boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_VOICE_READING);
    public void setOnDialogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }
    public HelpAskHereDialog(@NonNull Context context) {
        super(context);
        binding = DialogHelpAskHereBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleThanks();
    }

    public void showAskHereAns(int idAnsTrue) {
        if (!stateOfMusic) {
            getAskAns(idAnsTrue);
        } else {
            MediaManager.getInstance().setPlaySoundGame(R.raw.help_ask_01, mp ->
                    getAskAnsHaveSound(idAnsTrue));
        }
    }

    private void getAskAnsHaveSound(int idAnsTrue) {
        switch (idAnsTrue) {
            case 1:
                playSound(R.raw.tv1, R.drawable.user_call_b, R.raw.here_a, "A", R.raw.tv2, R.drawable.user_call_b, R.raw.here_b, "B", R.raw.tv3, R.drawable.user_call_b, R.raw.here_a2, "A");
                break;
            case 2:
                playSound(R.raw.tv1_1, R.drawable.user_call_b, R.raw.here_c, "C", R.raw.tv2_1, R.drawable.user_call_b, R.raw.here_b, "B", R.raw.tv3_1, R.drawable.user_call_b, R.raw.here_b2, "B");
                break;
            case 3:
                playSound(R.raw.tv1, R.drawable.user_call_b, R.raw.here_c, "C", R.raw.tv2, R.drawable.user_call_b, R.raw.here_c2, "C", R.raw.tv3, R.drawable.user_call_b, R.raw.here_c3, "C");
                break;
            case 4:
                playSound(R.raw.tv1_1, R.drawable.user_call_b, R.raw.here_d, "D", R.raw.tv2_1, R.drawable.user_call_b, R.raw.here_d2, "D", R.raw.tv3_1, R.drawable.user_call_b, R.raw.here_a2, "A");
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void playSound(int sound1, int drawable1, int sound2, String text2, int sound3, int drawable3, int sound4, String text4, int sound5, int drawable5, int sound6, String text6) {
        MediaManager.getInstance().setPlaySoundGame(sound1, mp -> {
            binding.imgHere1.setImageResource(drawable1);

            MediaManager.getInstance().setPlaySoundGame(sound2, mp1 -> {
                binding.tvHere1.setText("Đáp án là: " + text2);
                binding.imgHere1.setImageResource(R.drawable.user_call_w);

                MediaManager.getInstance().setPlaySoundGame(sound3, mp2 -> {
                    binding.imgHere2.setImageResource(drawable3);

                    MediaManager.getInstance().setPlaySoundGame(sound4, mp3 -> {
                        binding.tvHere2.setText("Theo tôi là: " + text4);
                        binding.imgHere2.setImageResource(R.drawable.user_call_w);

                        MediaManager.getInstance().setPlaySoundGame(sound5, mp4 -> {
                            binding.imgHere3.setImageResource(drawable5);

                            MediaManager.getInstance().setPlaySoundGame(sound6, mp5 -> {
                                binding.tvHere3.setText("Tôi nghĩ là: " + text6);
                                binding.btThanks.setVisibility(View.VISIBLE);
                                binding.imgHere3.setImageResource(R.drawable.user_call_w);
                            });
                        });
                    });
                });
            });
        });
    }

    @SuppressLint("SetTextI18n")
    private void getAskAns(int idAnsTrue) {
        String ansHelp = Constants.ANSWER_ARRAY[idAnsTrue - 1];

        binding.tvHere1.setText("Tôi nghĩ là: " + ansHelp);
        binding.tvHere2.setText("Phương án đúng là: " + ansHelp);
        binding.tvHere3.setText("Đáp án đúng là: " + ansHelp);

        binding.imgHere1.setImageResource(R.drawable.user_call_b);
        binding.imgHere2.setImageResource(R.drawable.user_call_b);
        binding.imgHere3.setImageResource(R.drawable.user_call_b);

        binding.btThanks.setVisibility(View.VISIBLE);
    }

    private void handleThanks() {
        binding.btThanks.setOnClickListener(view -> {
            dismiss();
            if (onDialogListener != null) {
                onDialogListener.onDialogClosed();
            }
        });
    }
}
