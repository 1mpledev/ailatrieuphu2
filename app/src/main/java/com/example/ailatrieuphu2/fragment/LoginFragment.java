package com.example.ailatrieuphu2.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.ailatrieuphu2.R;
import com.example.ailatrieuphu2.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends BaseFragment<com.example.ailatrieuphu2.databinding.FragmentLoginBinding> {
    public static String TAG = LoginFragment.class.getName();
    private TextView tvRegisterInLogin;
    private EditText etEmail, etPassword;
//    private CheckBox checkBox;
    private Button btnLogin;
    private FirebaseAuth firebaseAuth;
    @Override
    protected FragmentLoginBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentLoginBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        tvRegisterInLogin = binding.tvRegisterInLogin;
        etEmail = binding.etEmail;
        etPassword = binding.etPassword;
        btnLogin = binding.btnLogin;
        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        String t = getString(R.string.tv_register);
        SpannableString spannableString = new SpannableString(t);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                callBack.showFragment(RegisterFragment.TAG, false);
            }
        };
        spannableString.setSpan(clickableSpan, t.indexOf(getString(R.string.tv_register)), t.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), t.indexOf(getString(R.string.tv_register)), t.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRegisterInLogin.setText(spannableString);
        tvRegisterInLogin.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    }

    private void login(){
        String email, password;
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Ban chua nhap email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Ban chua nhap password!", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Dang nhap thanh cong!", Toast.LENGTH_SHORT).show();
                    callBack.showFragment(HomeFragment.TAG, false);
                } else {
                    Toast.makeText(getContext(), "Dang nhap khong thanh cong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


