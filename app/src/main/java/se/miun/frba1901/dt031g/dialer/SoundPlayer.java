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
    private Map<String, Integer>soundMap;
    private SoundPlayer(Context context){
        soundMap = new HashMap<>();

        // Skapar SoundPool som har usage: USAGE_ASSISTANCE_SONIFICATION och content type: CONTENT_TYPE_SONIFICATION
        // max streams använder default värde (1)
        soundPool = new SoundPool.Builder().setAudioAttributes(new AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()).build();
        loadSounds(context, "mamacita_us"); // Laddar ljud
    }
    public static SoundPlayer getInstance(Context context){
        if(instance == null)
            instance = new SoundPlayer(context);
        return instance;
    }
    // Voice anger vilket directory som vi ska ladda ljud ifrån, alltså vilken röst, just nu finns bara 1 alternativ
    private void loadSounds(Context context, String voice){
        // Loopar igenom array som innehåller alla symboler
        for(String symbol : Util.ALL_SYMBOLS) {
            // Sätter in symbolen och ID som motsvarar den symbolen, till load() skickar vi in path till ljudfilen för motsvarande symbol
            soundMap.put(symbol, soundPool.load(Util.getDirForVoice(context, voice).getPath() + "/" + Util.DEFAULT_VOICE_FILE_NAMES.get(symbol), 1));
        }
    }

    public void playSound(DialpadButton button){
        String str = button.getTitle().toString(); // Hämtar knappens titel
        //, denna string kommer att ge ljudet som vi vill starta om den anges i Map soundMap

        soundPool.play(soundMap.get(str), 1f, 1f, 1, 0, 1f);
    }
    public void destroy(){
        soundPool.release();
        soundPool = null;
        instance = null;
        soundMap.clear();
        soundMap = null;
    }
}
