package se.miun.frba1901.dt031g.dialer;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DialNumberDAO {
    @Query("SELECT * FROM dialnumbers")
    LiveData<List<DialNumber>> getAll();

    @Insert
    void insertAll(DialNumber... numbers);

    @Query("DELETE FROM dialnumbers")
    void deleteAll();
}