package com.example.ailatrieuphu2.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ailatrieuphu2.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {
    public static String TAG = RegisterFragment.class.getName();
    private EditText etEmail, etPassword;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;

    @Override
    protected FragmentRegisterBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentRegisterBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        etEmail = binding.etEmail;
        etPassword = binding.etPassword;
        btnRegister = binding.btnRegister;
        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        String email, password;
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Ban chua nhap email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Ban chua nhap password", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Tao tai khoang thanh cong", Toast.LENGTH_SHORT).show();
                    callBack.showFragment(LoginFragment.TAG, false);
                } else {
                    Toast.makeText(getContext(), "Tao tai khoang thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
