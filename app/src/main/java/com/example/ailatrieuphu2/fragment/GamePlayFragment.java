package com.example.ailatrieuphu2.fragment;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ailatrieuphu2.App;
import com.example.ailatrieuphu2.CommonUtils;
import com.example.ailatrieuphu2.Constants;
import com.example.ailatrieuphu2.MediaManager;
import com.example.ailatrieuphu2.OnDialogListener;
import com.example.ailatrieuphu2.R;
import com.example.ailatrieuphu2.database.entities.Question;
import com.example.ailatrieuphu2.databinding.FragmentGamePlayBinding;
import com.example.ailatrieuphu2.dialog.ConfirmSelectAnsDialog;
import com.example.ailatrieuphu2.dialog.HelpAskHereDialog;
import com.example.ailatrieuphu2.dialog.HelpCallDialog;
import com.example.ailatrieuphu2.dialog.HelpPercentDialog;
import com.example.ailatrieuphu2.dialog.NoticeDialog;
import com.example.ailatrieuphu2.dialog.SaveScoreDialog;
import com.example.ailatrieuphu2.dialog.SettingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePlayFragment extends BaseFragment<FragmentGamePlayBinding> implements OnDialogListener {
    public static final String TAG = GamePlayFragment.class.getName();
    public List<Integer> listAnswerHidden = new ArrayList<>(); // lưu ds câu trả lời ẩn
    public boolean stateOfMusic = CommonUtils.getInstance().getPrefDefaultTrue(SettingDialog.STATE_BACKGROUND);
    public boolean stateOfConfirm = CommonUtils.getInstance().getPrefDefaultTrue(ConfirmSelectAnsDialog.STATE_CONFIRM);
    public List<Question> questionList;
    private NoticeDialog noticeTimeOutDialog;
    private NoticeDialog noticeConfirmSelectDialog;
    private NoticeDialog noticeQuitGameDialog;
    private ConfirmSelectAnsDialog confirmSettingDialog;
    private SaveScoreDialog saveScoreDialog;
    private Question question;
    private CountDownTimer countDownTimer;
    private long remainingTime; // lưu thời gian còn lại hiện tại
    private int level;
    public boolean isBack = false;
    private boolean isUseFifty = true;
    private boolean isUseHere = true;
    private boolean isUseCall = true;
    private boolean isUsePercent = true;
    @Override
    protected FragmentGamePlayBinding initViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentGamePlayBinding.inflate(inflater, container, false);
    }
    @Override
    protected void initViews() {
        addEvent();
        initQuestion();
        showMilestone(level);
    }

    private void initQuestion() {
        new Thread(() -> {
            questionList = App.getInstance().getDatabase().getQuestionDAO().get15QuestionByLevel();
            requireActivity().runOnUiThread(() -> showQuestion(level));
        }).start();
    }

    private void addEvent() {
        binding.imgExit.setOnClickListener(this);
        binding.imgFifty.setOnClickListener(this);
        binding.imgSetting.setOnClickListener(this);
        binding.imgHere.setOnClickListener(this);
        binding.imgTelephone.setOnClickListener(this);
        binding.imgPercent.setOnClickListener(this);
        binding.tvAnswer1.setOnClickListener(this);
        binding.tvAnswer2.setOnClickListener(this);
        binding.tvAnswer3.setOnClickListener(this);
        binding.tvAnswer4.setOnClickListener(this);
    }
    @Override
    protected void clickView(View v) {
        super.clickView(v);
        if (v.getId() == R.id.img_exit) {
            showBackMainDialog();
        } else if (v.getId() == R.id.tv_answer1) {
            handleClickAnsA();
        } else if (v.getId() == R.id.tv_answer2) {
            handleClickAnsB();
        } else if (v.getId() == R.id.tv_answer3) {
            handleClickAnsC();
        } else if (v.getId() == R.id.tv_answer4) {
            handleClickAnsD();
        } else if (v.getId() == R.id.img_setting) {
            showSettingDialog();
        } else if (v.getId() == R.id.img_fifty) {
            handleClickFiftyHelp();
        } else if (v.getId() == R.id.img_here) {
            handleClickAskHereHelp();
        } else if (v.getId() == R.id.img_telephone) {
            handleClickTelephoneHelp();
        } else if (v.getId() == R.id.img_percent) {
            handleClickPercentHelp();
        }
    }

    private void setViewsEnabled(boolean enabled) {
        binding.imgExit.setEnabled(enabled);
        binding.imgSetting.setEnabled(enabled);
        binding.tvAnswer1.setEnabled(enabled);
        binding.tvAnswer2.setEnabled(enabled);
        binding.tvAnswer3.setEnabled(enabled);
        binding.tvAnswer4.setEnabled(enabled);

        // kiểm tra trạng thái các sự trợ giúp
        if (isUseFifty) {
            binding.imgFifty.setEnabled(enabled);
        } else {
            binding.imgFifty.setEnabled(false);
        }

        if (isUseHere) {
            binding.imgHere.setEnabled(enabled);
        } else {
            binding.imgHere.setEnabled(false);
        }

        if (isUseCall) {
            binding.imgTelephone.setEnabled(enabled);
        } else {
            binding.imgTelephone.setEnabled(false);
        }
        if (isUsePercent) {
            binding.imgPercent.setEnabled(enabled);
        } else {
            binding.imgPercent.setEnabled(false);
        }
    }

    public void showBackMainDialog() {
        noticeQuitGameDialog = new NoticeDialog(context);
        noticeQuitGameDialog.createCustomDialog(false, "Thông báo"
                , "Bạn có muốn dừng cuộc chơi không? \nLưu ý: Dữ liệu hiện tại sẽ không được lưu lại."
                , "Không", "Có", v -> {
            if (v.getId() == R.id.btn_yes) {
                isBack = true;
                // dừng đếm ngược
                countDownTimer.cancel();
                // tắt hành động click
                setViewsEnabled(false);

                // false
                if (!stateOfMusic) {
                    callBack.showFragment(HomeFragment.TAG, false);
                } else {
                    MediaManager.getInstance().setPlaySoundGame(R.raw.sound_ket_thuc
                            , mp -> callBack.showFragment(HomeFragment.TAG, false));
                }
            }
            noticeQuitGameDialog.dismiss();
                });
        noticeQuitGameDialog.show();
        musicId(R.raw.ping);
    }

    private void musicId(int idMusic) {
        // true
        if (stateOfMusic) {
            MediaManager.getInstance().setPlaySoundGame(idMusic, null);
        }
    }

    private void handleClickAnsA() {
        // false
        if (!stateOfConfirm) {
            selected(binding.tvAnswer1, 1);
        } else {
            showConfirmationDialog(binding.tvAnswer1, 1);
        }
    }

    private void showConfirmationDialog(TextView answerView, int position) {
        musicId(R.raw.ping);

        noticeConfirmSelectDialog = new NoticeDialog(context);
        noticeConfirmSelectDialog.createCustomDialog(false, "Xác nhận"
                , "Bạn có  chắn chắn muốn chọn đáp án này không?"
                , "Không", "Có", v -> {
            if (v.getId() == R.id.btn_yes){
                selected(answerView, position);
            }
            noticeConfirmSelectDialog.dismiss();
                });
        noticeConfirmSelectDialog.show();
    }

    private void selected(TextView answerView, int position) {
        // turn off click
        setViewsEnabled(false);

        //stop countdown time
        countDownTimer.cancel();

        //false
        if (!stateOfMusic) {
            if (position == question.answerTrue) {
                ansTrue(position, answerView);
            } else {
                ansFalse(question.answerTrue, answerView);
            }
        } else {
            answerView.setBackgroundResource(R.drawable.player_answer_background_selected);
            MediaManager.getInstance().setPlaySoundGame(MediaManager.SELECT[position - 1]
                    , mp -> MediaManager.getInstance().setPlaySoundGame(R.raw.ans_now
                            , mp1 -> {
                        if (position == question.answerTrue) {
                            ansTrue(position, answerView);
                        } else {
                            ansFalse(question.answerTrue, answerView);
                        }
                            }));
        }
    }

    private void ansFalse(int idAnswerTrue, TextView answerView) {
        if (!stateOfMusic) {
            showGameOver();
        } else {
            // đổi background nếu chọn câu trả lời sai
            answerView.setBackgroundResource(R.drawable.player_answer_background_wrong);
            // nếu đúng
            getAnswerSelected(idAnswerTrue).setBackgroundResource(R.drawable.player_answer_background_true);
            animationSelectTrueAnswer(getAnswerSelected(idAnswerTrue));
            MediaManager.getInstance().setPlaySoundGame(MediaManager.LOSE[idAnswerTrue - 1],
                    mp -> MediaManager.getInstance().setPlaySoundGame(R.raw.sound_chiatay, mp1 -> {
                        MediaManager.getInstance().setPlaySoundGame(R.raw.sound_fail, null);
                        showGameOver();
                    }));
        }
    }

    private TextView getAnswerSelected(int idAnswerTrue) {
        TextView tvSelected = null;
        if (idAnswerTrue == 1) {
            tvSelected = binding.tvAnswer1;
        } else if (idAnswerTrue == 2) {
            tvSelected = binding.tvAnswer2;
        }
        if (idAnswerTrue == 3) {
            tvSelected = binding.tvAnswer3;
        }
        if (idAnswerTrue == 4) {
            tvSelected = binding.tvAnswer4;
        }
        return tvSelected;
    }

    private void ansTrue(int position, TextView answerView) {
        if (!stateOfMusic) {
            if (level == 14) {
                champion();
            } else {
                nexQuestionGame();
            }
        } else {
            // thay doi background khi chon cau tra loi dung
            answerView.setBackgroundResource(R.drawable.player_answer_background_true);
            animationSelectTrueAnswer(answerView);
            loadSoundNextQuestionGame(position);
        }
    }

    private void loadSoundNextQuestionGame(int position) {
        MediaManager.getInstance().setPlaySoundGame(MediaManager.TRUE[position - 1], mp -> {
            if (level == 4) {
                MediaManager.getInstance().setPlaySoundGame(R.raw.pass_milestone_1, mp1 -> nexQuestionGame());
            } else if (level == 9) {
                MediaManager.getInstance().setPlaySoundGame(R.raw.pass_milestone_2, mp12 -> nexQuestionGame());
            } else if (level == 14) {
                MediaManager.getInstance().setPlaySoundGame(R.raw.pass_milestone_3, mp13 -> {
                    MediaManager.getInstance().setPlaySoundGame(R.raw.win_games, null);
                    champion();
                });
            }else {
                nexQuestionGame();
            }
        });
    }

    private void animationSelectTrueAnswer(View v) {
        Animation animation = new AlphaAnimation(2.0f, 0.0f);
        animation.setDuration(100);
        animation.setRepeatCount(15);
        v.startAnimation(animation);
    }

    private void nexQuestionGame() {
        if (level < 14) {
            //level up
            level++;
            //show next question
            listAnswerHidden.clear();
            showQuestion(level);
            showMilestone(level);

            if (level == 5) {
                binding.imgPercent.setVisibility(View.VISIBLE);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void showQuestion(int level) {
        // binding data
        try {
            question = questionList.get(level);
            Log.i(TAG, "Name: " + question.nameQuestion + "\nLevel: "
                    + question.level + "\nAnswer true: " + question.answerTrue);
            binding.tvLevelQuestion.setText(String.format("Câu %s", level + 1));
            binding.tvContentQuestion.setText(question.nameQuestion);
            binding.tvAnswer1.setText(Constants.ANSWER_ARRAY[0] + ". " + question.answerA);
            binding.tvAnswer2.setText(Constants.ANSWER_ARRAY[1] + ". " + question.answerB);
            binding.tvAnswer3.setText(Constants.ANSWER_ARRAY[2] + ". " + question.answerC);
            binding.tvAnswer4.setText(Constants.ANSWER_ARRAY[3] + ". " + question.answerD);

            // bg default answer
            binding.tvAnswer1.setBackgroundResource(R.drawable.player_answer_background_normal);
            binding.tvAnswer2.setBackgroundResource(R.drawable.player_answer_background_normal);
            binding.tvAnswer3.setBackgroundResource(R.drawable.player_answer_background_normal);
            binding.tvAnswer4.setBackgroundResource(R.drawable.player_answer_background_normal);

            binding.tvAnswer1.setVisibility(View.VISIBLE);
            binding.tvAnswer2.setVisibility(View.VISIBLE);
            binding.tvAnswer3.setVisibility(View.VISIBLE);
            binding.tvAnswer4.setVisibility(View.VISIBLE);

            //turn on click
            setViewsEnabled(true);

            //start countdown time
            setCountDownTimer();

            //play sounf start question 1 -> 15
            musicId(MediaManager.START_QUESTION[level]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCountDownTimer() {
        long duration = getDurationForLevel(level);

        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;

                String sDuration = String.format("%s", remainingTime / 1000);
                binding.tvCountdownTime.setText(sDuration);
            }

            @Override
            public void onFinish() {
                onTimerFinish();
            }
        };
        countDownTimer.start();
    }

    private void onTimerFinish() {
        setViewsEnabled(false);
        closePreciousDialog();

        if (!stateOfMusic) {
            noticeTimeOutDialog = new NoticeDialog(context);
            noticeTimeOutDialog.createCustomDialog(false, "Thông báo"
                    , "Thời gian đã hết! \n Bạn đã thua cuộc!"
                    , "Đóng", null, v -> {
                if (v.getId() == R.id.btn_no){
                    showGameOver();
                    noticeTimeOutDialog.dismiss();
                }
                    });
            noticeTimeOutDialog.show();
        } else {
            loadSoundTimeOut();
        }
    }

    private void loadSoundTimeOut() {
        MediaManager.getInstance().setPlaySoundGame(R.raw.out_of_time
                , mp -> {
            noticeTimeOutDialog = new NoticeDialog(context);
            noticeTimeOutDialog.createCustomDialog(false, "Thông báo"
                    , "Thời gian đã hết \n Bạn đã thua cuộc!"
                    , "Đóng", null, v -> {
                if (v.getId() == R.id.btn_no) {
                    showGameOver();
                    noticeTimeOutDialog.dismiss();
                }
                    });
            noticeTimeOutDialog.show();
                });
    }

    private void showGameOver() {
        countDownTimer.cancel();
        int milestoneGameOver = Integer.parseInt(binding.tvLevelQuestionScore.getText().toString());
        saveScoreDialog = new SaveScoreDialog(context, callBack);
        saveScoreDialog.createSaveScoreDialog("Thất bại :((", milestoneGameOver, v -> {
            if (v.getId() == R.id.bt_save) {
                saveScoreDialog.insertOrUpdateScore(milestoneGameOver);
            } else {
                loadSoundPlayAgain();
            }
        });
        saveScoreDialog.show();
    }

    private void closePreciousDialog() {
        if (noticeQuitGameDialog != null && noticeQuitGameDialog.isShowing()) {
            noticeQuitGameDialog.dismiss();
        } else if (noticeConfirmSelectDialog != null && noticeConfirmSelectDialog.isShowing()) {
            noticeConfirmSelectDialog.dismiss();
        } else if (confirmSettingDialog !=null && confirmSettingDialog.isShowing()){
            confirmSettingDialog.dismiss();
        }
    }

    private long getDurationForLevel(int level) {
        if (level < 4) {
            return 16000;
        } else if (level <= 9) {
            return 31000;
        } else if (level <= 14) {
            return 46000;
        }
        return 0;
    }

    private void champion() {
        countDownTimer.cancel();
        int milestoneChampion = 150000000;
        saveScoreDialog = new SaveScoreDialog(context, callBack);
        saveScoreDialog.createSaveScoreDialog("Chiến thắng", milestoneChampion, v -> {
            if (v.getId() == R.id.bt_save) {
                saveScoreDialog.insertOrUpdateScore(milestoneChampion);
            } else {
                loadSoundPlayAgain();
            }
        });
        saveScoreDialog.show();
    }

    private void loadSoundPlayAgain() {
        if (stateOfMusic) {
            MediaManager.getInstance().setPlaySoundGame(R.raw.play1, null);
        }
        saveScoreDialog.dismiss();
        callBack.showFragment(GamePlayFragment.TAG, false);
    }

    private void handleClickAnsB() {
        if (!stateOfConfirm) {
            selected(binding.tvAnswer2, 2);
        } else {
            showConfirmationDialog(binding.tvAnswer2, 2);
        }
    }

    private void handleClickAnsC() {
        if (!stateOfConfirm) {
            selected(binding.tvAnswer3, 3);
        } else {
            showConfirmationDialog(binding.tvAnswer3, 3);
        }
    }

    private void handleClickAnsD() {
        if (!stateOfConfirm) {
            selected(binding.tvAnswer4, 4);
        } else {
            showConfirmationDialog(binding.tvAnswer4, 4);
        }
    }

    private void showSettingDialog() {
        musicId(R.raw.ping);
        confirmSettingDialog = new ConfirmSelectAnsDialog(context);
        confirmSettingDialog.setOnConfirmationChangeListener(confirmed -> stateOfConfirm = confirmed);
        confirmSettingDialog.show();
    }

    private void handleClickFiftyHelp() {
        pauseCountDownTimer();
        isUseFifty = false;
        setViewsEnabled(false);

        if (!stateOfMusic) {
            removeRandomAnswer();
        } else {
            MediaManager.getInstance().setPlaySoundGame(R.raw.ping
                    , mp -> MediaManager.getInstance().setPlaySoundGame(R.raw.sound_chon_50_50
                            , mp1 -> MediaManager.getInstance().setPlaySoundGame(R.raw.remove50
                                    , mp2 -> MediaManager.getInstance().setPlaySoundGame(R.raw.tinh
                                            , mp3 -> removeRandomAnswer()))));
        }
    }

    private void removeRandomAnswer() {
        int answerCount = 0; // dem texview hidden
        int trueAnswerIndex = question.answerTrue;
        Random random = new Random();

        while (answerCount < 2) {
            int randomAnswerHide = random.nextInt(4) + 1;

            if (randomAnswerHide != trueAnswerIndex && !listAnswerHidden.contains(randomAnswerHide)) {
                TextView tvHide = getAnswerSelected(randomAnswerHide);
                tvHide.setVisibility(View.INVISIBLE);
                listAnswerHidden.add(randomAnswerHide);
                answerCount++;
                Log.i(TAG, "Count: " + answerCount);
            }
        }

        setViewsEnabled(true);
        binding.imgFifty.setImageResource(R.drawable.atp__activity_player_button_image_help_5050_x);
        resumeCountDownTimer();
    }

    private void resumeCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer = new CountDownTimer(remainingTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTime = millisUntilFinished;

                    String sDuration = String.format("%s", remainingTime / 1000);
                    binding.tvCountdownTime.setText(sDuration);
                }

                @Override
                public void onFinish() {
                    onTimerFinish();
                }
            };
            countDownTimer.start();
        }
    }

    private void pauseCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void handleClickAskHereHelp() {
        pauseCountDownTimer();
        isUseHere = false;
        setViewsEnabled(false);

        if (!stateOfMusic) {
            showDialogAskHere();
        } else {
            MediaManager.getInstance().setPlaySoundGame(R.raw.ping
                    , mp -> MediaManager.getInstance().setPlaySoundGame(R.raw.sound_chon_tu_van
                            , mp1 -> showDialogAskHere()));
        }
    }

    private void showDialogAskHere() {
        HelpAskHereDialog helpAskHereDialog = new HelpAskHereDialog(context);
        helpAskHereDialog.setCancelable(false);
        helpAskHereDialog.showAskHereAns(question.answerTrue);
        helpAskHereDialog.setOnDialogListener(this);
        helpAskHereDialog.show();

        binding.imgHere.setImageResource(R.drawable.atp__activity_player_button_image_help_audience_x);
    }

    private void handleClickTelephoneHelp() {
        pauseCountDownTimer();
        isUseCall = false;
        setViewsEnabled(false);

        if (!stateOfMusic) {
            showDiaLogCall();
        } else {
            MediaManager.getInstance().setPlaySoundGame(R.raw.ping
                    , mp -> MediaManager.getInstance().setPlaySoundGame(R.raw.sound_goi_dien
                            , mp1 -> MediaManager.getInstance().setPlaySoundGame(R.raw.connec_call
                                    , mp2 -> showDiaLogCall())));
        }
    }

    private void showDiaLogCall() {
        HelpCallDialog callDialog = new HelpCallDialog(context);
        callDialog.getAnsCall(question.answerTrue);
        callDialog.setCancelable(false);
        callDialog.show();
        callDialog.setOnDialogListener(this);

        binding.imgTelephone.setImageResource(R.drawable.atp__activity_player_button_image_help_call_x);
    }

    private void handleClickPercentHelp() {
        pauseCountDownTimer();
        isUsePercent = false;
        setViewsEnabled(false);

        if (!stateOfMusic) {
            showDialogPercent();
        } else {
            MediaManager.getInstance().setPlaySoundGame(R.raw.ping
                    , mp -> MediaManager.getInstance().setPlaySoundGame(R.raw.sound_chon_y_kien
                            , mp1 -> MediaManager.getInstance().setPlaySoundGame(R.raw.help_ask_01
                                    , mp2 -> {
                                showDialogPercent();
                                musicId(R.raw.help_ask_02);
                                    })));
        }
    }

    private void showDialogPercent() {
        HelpPercentDialog percentDialog = new HelpPercentDialog(context);
        percentDialog.updateChartData(question.answerTrue, listAnswerHidden);
        percentDialog.setCancelable(false);
        percentDialog.show();
        percentDialog.setOnDialogListener(this);

        binding.imgPercent.setImageResource(R.drawable.bar_chart_w);
    }

    private void showMilestone(int level) {
        binding.tvLevelQuestionScore.setText(Constants.MILESTONE_ARRAY[level]);
        musicId(R.raw.tinh);
    }

    @Override
    public void onDialogClosed() {
        resumeCountDownTimer();
        setViewsEnabled(true);
    }
}
