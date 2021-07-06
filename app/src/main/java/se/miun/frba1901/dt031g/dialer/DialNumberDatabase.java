package se.miun.frba1901.dt031g.dialer;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DialNumber.class}, version = 4)
public abstract class DialNumberDatabase extends RoomDatabase{
    public abstract DialNumberDAO dialNumberDAO();
}
