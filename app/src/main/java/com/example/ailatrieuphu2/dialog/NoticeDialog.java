package com.example.ailatrieuphu2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.ailatrieuphu2.databinding.DialogNoticeBinding;

public class NoticeDialog extends Dialog {
    private final DialogNoticeBinding binding;

    public NoticeDialog(@NonNull Context context) {
        super(context);
        binding = DialogNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void createCustomDialog(boolean isOut, String title
            , String description, String btnNameNo, String btnNameYes
            , View.OnClickListener onClickListener) {
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        binding.tvName.setText(title);
        binding.tvDescription.setText(description);
        setCanceledOnTouchOutside(isOut);
        setCancelable(isOut);

        if (btnNameNo == null) {
            binding.btnNo.setVisibility(View.GONE);
        } else {
            binding.btnNo.setText(btnNameNo);
        }

        if (btnNameYes == null) {
            binding.btnYes.setVisibility(View.GONE);
        } else {
            binding.btnYes.setText(btnNameYes);
        }

        binding.btnYes.setOnClickListener(onClickListener);
        binding.btnNo.setOnClickListener(onClickListener);

    }
}
