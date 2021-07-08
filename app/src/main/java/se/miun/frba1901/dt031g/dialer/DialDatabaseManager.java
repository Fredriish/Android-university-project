package se.miun.frba1901.dt031g.dialer;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

/**
 * @brief Singleton klass för att nå databasen
 */
public class DialDatabaseManager {
    private static DialDatabaseManager instance = null;
    private DialNumberDatabase database;
    private DialDatabaseManager(Context context){
        database = Room.databaseBuilder(context, DialNumberDatabase.class,
                context.getString(R.string.dialnumbers_filename)).allowMainThreadQueries().build();
    }
    public static DialDatabaseManager getInstance(Context context){
        if(instance == null){
            instance = new DialDatabaseManager(context);
        }
        return instance;
    }
    public void insertAll(DialNumber... dialNumbers){
        database.dialNumberDAO().insertAll(dialNumbers);
    }
    public LiveData<List<DialNumber>> getAll(){
        return database.dialNumberDAO().getAll();
    }

    public void deleteAll(){
        database.dialNumberDAO().deleteAll();
    }

}
