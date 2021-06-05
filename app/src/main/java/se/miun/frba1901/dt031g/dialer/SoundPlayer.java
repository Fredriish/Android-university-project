package se.miun.frba1901.dt031g.dialer;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;

public class SoundPlayer {
    private SoundPool soundPool;
    private static SoundPlayer instance = null;
    private SoundPlayer(Context context){
        // Skapar SoundPool som har usage: USAGE_MEDIA och content type: CONTENT_TYPE_SONIFICATION
        // max streams använder default värde (1)
        soundPool = new SoundPool.Builder().setAudioAttributes(new AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()).build();

    }
    public static SoundPlayer getInstance(Context context){
        if(instance == null)
            instance = new SoundPlayer(context);
        return instance;
    }
    // Översätter ett tecken, tex "1" till "one" eller "#" till "pound" för att rätt filnamn ska kunna hämtas
    private String translateSymbol(CharSequence name){
        switch(name.toString()){
            case "1":
                return "one";
            case "2":
                return "two";
            case "3":
                return "three";
            case "4":
                return "four";
            case "5":
                return "five";
            case "6":
                return "six";
            case "7":
                return "seven";
            case "8":
                return "eight";
            case "9":
                return "nine";
            case "0":
                return "zero";
            case "*":
                return "star";
            case "#":
                return "pound";
        }
        return name.toString();
    }
    public void playSound(DialpadButton button){
        soundPool.play(soundPool.load("res/raw/" + translateSymbol(button.getTitle()) + ".mp3",
                1),1,1,1,0,1);
    }
    public void destroy(){
        soundPool.release();
        soundPool = null;
        instance = null;
    }
}
