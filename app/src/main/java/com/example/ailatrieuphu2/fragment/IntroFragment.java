package com.example.ailatrieuphu2.fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ailatrieuphu2.App;
import com.example.ailatrieuphu2.MediaManager;
import com.example.ailatrieuphu2.R;
import com.example.ailatrieuphu2.databinding.FragmentIntroBinding;

public class IntroFragment extends BaseFragment<FragmentIntroBinding> {
    public static String TAG = IntroFragment.class.getName();
    private TextView tvWelcome, tvMessTouch;
    private View vTouch;
    private ImageView imgViewAiLaTrieuPhu;
    private Animation faInOut, faInLogo,faMessTouch, blMessTouch;
    @Override
    protected FragmentIntroBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentIntroBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        tvWelcome = binding.tvWelcome;
        imgViewAiLaTrieuPhu = binding.imgAilatrieuphu;
        tvMessTouch = binding.tvMessTouch;
        vTouch = binding.vTouch;

        tvWelcome.setVisibility(View.INVISIBLE);
        imgViewAiLaTrieuPhu.setVisibility(View.INVISIBLE);
        tvMessTouch.setVisibility(View.INVISIBLE);

        faInOut = AnimationUtils.loadAnimation(App.getInstance().getApplicationContext(), R.anim.fade_in_out);
        faInLogo = AnimationUtils.loadAnimation(App.getInstance().getApplicationContext(), R.anim.fade_in_logo);
        faMessTouch = AnimationUtils.loadAnimation(App.getInstance().getApplicationContext(), R.anim.fade_in_mess_touch);
        blMessTouch = AnimationUtils.loadAnimation(App.getInstance().getApplicationContext(), R.anim.blink);
        tvWelcome.startAnimation(faInOut);
        faInOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvWelcome.clearAnimation();
                imgViewAiLaTrieuPhu.startAnimation(faInLogo);
                MediaManager.getInstance().setPlayBG(R.raw.welcomegame);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        faInLogo.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgViewAiLaTrieuPhu.clearAnimation();
                imgViewAiLaTrieuPhu.setVisibility(View.VISIBLE);
                tvMessTouch.startAnimation(faMessTouch);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        faMessTouch.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvMessTouch.startAnimation(blMessTouch);
                vTouch.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                            MediaManager.getInstance().stopBG();
                            MediaManager.getInstance().setPlayBG(R.raw.bgmusic);
                            callBack.showFragment(LoginFragment.TAG, false);
                            return true;
                        }
                        return false;
                    }
                });
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
};
