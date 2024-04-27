package com.example.ailatrieuphu2.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ailatrieuphu2.App;
import com.example.ailatrieuphu2.CommonUtils;
import com.example.ailatrieuphu2.MediaManager;
import com.example.ailatrieuphu2.OnMainCallBack;
import com.example.ailatrieuphu2.R;
import com.example.ailatrieuphu2.database.entities.ScoreUser;
import com.example.ailatrieuphu2.databinding.DialogSaveScoreBinding;
import com.example.ailatrieuphu2.fragment.HomeFragment;

import java.text.DecimalFormat;

public class SaveScoreDialog extends Dialog {
    private static final String TAG = SaveScoreDialog.class.getName();
    private final DialogSaveScoreBinding binding;
    private Context context;
    public boolean state0fMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_VOICE_READING);
    public OnMainCallBack callBack;

    public SaveScoreDialog(@NonNull Context context, OnMainCallBack callBack) {
        super(context);
        binding = DialogSaveScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.callBack = callBack;
        setCancelable(false);
    }

    @SuppressLint("SetTextI18n")
    public void createSaveScoreDialog(String title, int milestone
            , View.OnClickListener onClickListener) {
        binding.tvTitle.setText(title);
        binding.btAgain.setText("Chơi lại");

        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formatNumber = decimalFormat.format(milestone);
        binding.tvMilestone.setText(formatNumber + "VND");

        if (milestone == 0) {
            binding.etName.setVisibility(View.GONE);
            binding.btSave.setText("Thoát");
            binding.btSave.setOnClickListener(view -> {
                loadSoundEnd();
                callBack.showFragment(HomeFragment.TAG, false);
                dismiss();
            });
        } else {
            binding.etName.setVisibility(View.VISIBLE);
            binding.btSave.setText("Lưu điểm");
            binding.btSave.setOnClickListener(v -> {
                String name = binding.etName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getContext(), "Bạn chưa nhập tên", Toast.LENGTH_SHORT).show();
                } else {
                    insertOrUpdateScore(milestone);
                }
            });
        }
        binding.btAgain.setOnClickListener(onClickListener);
    }

    public void insertOrUpdateScore(int milestone) {
        String name = binding.etName.getText().toString();
        // xóa dấu cách
        name = name.trim().replaceAll("\\s+", " ");
        // viết hoa chữ cái đầu tiên
        char[] charArray = name.toCharArray();
        boolean foundSpace = true;

        for (int i = 0; i < charArray.length; i++) {
            if (Character.isLetter(charArray[i])) {
                if (foundSpace) {
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            } else {
                foundSpace = true;
            }
        }
        // convert char[] to string
        name = String.valueOf(charArray);

        String effectiveFinalName = name;

        new Thread(() -> {
            checkNameExit(effectiveFinalName, milestone);
            loadSoundEnd();
        }).start();
    }

    private void checkNameExit(String name, int milestone) {
        ScoreUser existingUser = App.getInstance().getDatabase().getScoreUserDAO().getScoreUserByName(name);
        if (existingUser != null) {
            int oldScore = existingUser.score;
            if (oldScore < milestone) {
                App.getInstance().getDatabase().getScoreUserDAO().updateScoreUserByName(name, milestone);
                Log.i(TAG, "Cập nhật thành công");
                Log.i(TAG, existingUser.nameUser + milestone);
            }
        } else {
            App.getInstance().getDatabase().getScoreUserDAO().insertScoreUser(new ScoreUser(name, milestone));
            Log.i(TAG, "Thêm mới");
            Log.i(TAG, name + milestone);
        }

        Log.i(TAG, App.getInstance().getDatabase().getScoreUserDAO().getAllScoreUser().toString());
    }

    private void loadSoundEnd() {
        if (!state0fMusic) {
            dismiss();
            callBack.showFragment(HomeFragment.TAG, false);
        } else {
            MediaManager.getInstance().setPlaySoundGame(R.raw.sound_ket_thuc, mp -> {
                dismiss();
                callBack.showFragment(HomeFragment.TAG, false);
            });
        }
        hideSoftKeyboard();
    }

    private void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
