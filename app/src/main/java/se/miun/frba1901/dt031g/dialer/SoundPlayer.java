package se.miun.frba1901.dt031g.dialer;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.util.Log;

import androidx.loader.content.Loader;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {
    private SoundPool soundPool;
    private static SoundPlayer instance = null;

    // Varje character motsvarar ett loaded sound
    private Map<Character, Integer>soundMap;
    private SoundPlayer(Context context){
        soundMap = new HashMap<>();

        // Skapar SoundPool som har usage: USAGE_ASSISTANCE_SONIFICATION och content type: CONTENT_TYPE_SONIFICATION
        // max streams använder default värde (1)
        soundPool = new SoundPool.Builder().setAudioAttributes(new AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()).build();
        loadSounds(context, "raw"); // Laddar ljud
    }
    public static SoundPlayer getInstance(Context context){
        if(instance == null)
            instance = new SoundPlayer(context);
        return instance;
    }
    // Voice anger vilket directory som vi ska ladda ljud ifrån, alltså vilken röst, just nu finns bara 1 alternativ
    private void loadSounds(Context context, String voice){
        if(voice.equals("raw")){
            soundMap.put('0', soundPool.load(context, R.raw.zero,1));
            soundMap.put('1', soundPool.load(context, R.raw.one,1));
            soundMap.put('2', soundPool.load(context, R.raw.two,1));
            soundMap.put('3', soundPool.load(context, R.raw.three,1));
            soundMap.put('4', soundPool.load(context, R.raw.four,1));
            soundMap.put('5', soundPool.load(context, R.raw.five,1));
            soundMap.put('6', soundPool.load(context, R.raw.six,1));
            soundMap.put('7', soundPool.load(context, R.raw.seven,1));
            soundMap.put('8', soundPool.load(context, R.raw.eight,1));
            soundMap.put('9', soundPool.load(context, R.raw.nine,1));
            soundMap.put('#', soundPool.load(context, R.raw.pound,1));
            soundMap.put('*', soundPool.load(context, R.raw.star,1));
        }
    }

    public void playSound(DialpadButton button){
        Character c = button.getTitle().charAt(0); // Hämtar en character från knappens titel
        //, denna character kommer att ge ljudet som vi vill starta om den anges i Map soundMap
        soundPool.play(soundMap.get(c), 1f, 1f, 1, 0, 1f);
    }
    public void destroy(){
        soundPool.release();
        soundPool = null;
        instance = null;
        soundMap.clear();
        soundMap = null;
    }
}
