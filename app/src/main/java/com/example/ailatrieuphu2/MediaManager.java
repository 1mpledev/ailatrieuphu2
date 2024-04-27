package com.example.ailatrieuphu2;

import android.media.MediaPlayer;

public class MediaManager {
    private MediaPlayer playBG;
    private MediaPlayer playSoundGame;
    private boolean isPauseBG;
    private boolean isPauseSoundGame;
    private static MediaManager instance;

    public static final int[] START_QUESTION = {R.raw.start_cau1, R.raw.start_cau2, R.raw.start_cau3, R.raw.start_cau4, R.raw.start_cau5
            , R.raw.start_cau6, R.raw.start_cau7, R.raw.start_cau8, R.raw.start_cau9, R.raw.start_cau10
            , R.raw.start_cau11, R.raw.start_cau12, R.raw.start_cau13, R.raw.start_cau14, R.raw.start_cau15};
    public static final int[] HELP_CALL = {R.raw.call_a, R.raw.call_b, R.raw.call_c, R.raw.call_d};
    public static final int[] SELECT = {R.raw.ans_1, R.raw.ans_2, R.raw.ans_3, R.raw.ans_4};
    public static final int[] TRUE = {R.raw.true_1, R.raw.true_2, R.raw.true_3, R.raw.true_4};
    public static final int[] LOSE = {R.raw.lose_1, R.raw.lose_2, R.raw.lose_3, R.raw.lose_4};

    private  MediaManager() {}

    public static MediaManager getInstance(){
        if (instance == null) {
            instance = new MediaManager();
        }
        return instance;
    }

    public void setPlayBG(int sound){
        if (playBG != null){
            playBG.reset();
        }
        playBG = MediaPlayer.create(App.getInstance(), sound);
        playBG.setLooping(true);
        playBG.start();
    }
    public void setPlayBG(int sound, MediaPlayer.OnCompletionListener event){
        if (playBG != null){
            playBG.reset();
        }
        playBG = MediaPlayer.create(App.getInstance(), sound);
        playBG.setOnCompletionListener(event);
        playBG.start();
    }
    public void setPlaySoundGame(int sound, MediaPlayer.OnCompletionListener event) {
        if (playSoundGame != null) {
            playSoundGame.reset();
        }
        playSoundGame = MediaPlayer.create(App.getInstance(), sound);
        playSoundGame.setOnCompletionListener(event);
        playSoundGame.start();
    }
    public void resumeSound(){
        if (playSoundGame != null && isPauseSoundGame) {
            isPauseSoundGame = false;
            playSoundGame.start();
        }
    }
    public void resumeBG() {
        if (playBG != null && isPauseBG) {
            isPauseBG = false;
            playBG.start();
        }
    }
    public void pauseBG() {
        if (playBG != null && playBG.isPlaying()) {
            playBG.pause();
            isPauseBG = true;
        }
    }
    public void pauseSoundGame() {
        if (playSoundGame != null && playSoundGame.isPlaying()) {
            playSoundGame.pause();
            isPauseSoundGame = true;
        }
    }
    public void stopBG() {
        if (playBG != null) {
            playBG.reset();
        }
    }
    public void stopSoundGame() {
        if (playSoundGame != null) {
            playSoundGame.reset();
        }
    }

}
