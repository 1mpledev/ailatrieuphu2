package com.example.ailatrieuphu2.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import com.example.ailatrieuphu2.R;
import com.example.ailatrieuphu2.OnMainCallBack;
import com.example.ailatrieuphu2.fragment.BaseFragment;


public abstract class BaseActivity<B extends ViewBinding>
        extends AppCompatActivity implements View.OnClickListener, OnMainCallBack {
    protected B binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = initViewBinding();
        setContentView(binding.getRoot());
        initViews();
    }

    protected abstract B initViewBinding();
    protected abstract void initViews();

    public final void onClick(View v) {
        clickView(v);
    }
    private void clickView(View v){}

    @Override
    public void showFragment(String tag, boolean isBacked) {
        try {
            Class<?> instance = Class.forName(tag);
            BaseFragment<?> fragment = (BaseFragment<?>) instance.newInstance();

            fragment.setOnMainCallBack(this);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            if (isBacked) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.replace(R.id.fr_container, fragment, tag).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
