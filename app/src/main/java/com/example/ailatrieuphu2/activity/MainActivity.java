package com.example.ailatrieuphu2.activity;

import androidx.fragment.app.Fragment;

import com.example.ailatrieuphu2.CommonUtils;
import com.example.ailatrieuphu2.MediaManager;
import com.example.ailatrieuphu2.R;
import com.example.ailatrieuphu2.databinding.ActivityMainBinding;
import com.example.ailatrieuphu2.dialog.NoticeDialog;
import com.example.ailatrieuphu2.dialog.SettingDialog;
import com.example.ailatrieuphu2.fragment.GamePlayFragment;
import com.example.ailatrieuphu2.fragment.HomeFragment;
import com.example.ailatrieuphu2.fragment.IntroFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    @Override
    protected ActivityMainBinding initViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initViews() {
        showFragment(IntroFragment.TAG, false);
    }
    protected void onStop() {
        super.onStop();
        MediaManager.getInstance().pauseBG();
        MediaManager.getInstance().pauseSoundGame();
    }
    protected void onStart() {
        super.onStart();
        checkStateBG();
        checkStateVoiceReading();
    }

    private void checkStateBG() {
        boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_BACKGROUND);
        if (stateOfMusic) {
            MediaManager.getInstance().pauseBG();
        } else {
            MediaManager.getInstance().resumeBG();
        }
    }

    private void checkStateVoiceReading() {
        boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_VOICE_READING);
        if (stateOfMusic) {
            MediaManager.getInstance().pauseSoundGame();
        } else {
            MediaManager.getInstance().resumeSound();
        }
    }
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fr_container);
        if (fragment != null) {
            if (fragment instanceof HomeFragment) {
                showQuitDiaLog();
            } else if (fragment instanceof GamePlayFragment) {
                if (!((GamePlayFragment) fragment).isBack) {
                    ((GamePlayFragment) fragment).showBackMainDialog();
                } else {
                    super.onBackPressed();
                }
            } else {
                MediaManager.getInstance().stopSoundGame();
                getSupportFragmentManager().popBackStack();
                super.onBackPressed();
            }
        }
    }

    public void showQuitDiaLog(){
        NoticeDialog noticeDialog = new NoticeDialog(this);
        noticeDialog.createCustomDialog(false, "Thông báo"
                , "Bạn muốn dừng cuộc chơi?", "Không", "Có", view -> {
            if (view.getId() == R.id.btn_yes){
                finish();
            } else {
                noticeDialog.cancel();
//                showFragment(HomeFragment.TAG, false);
            }
            noticeDialog.dismiss();
        });
        noticeDialog.show();
    }

}