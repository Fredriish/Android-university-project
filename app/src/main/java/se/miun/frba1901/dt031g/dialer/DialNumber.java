package se.miun.frba1901.dt031g.dialer;


import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dialnumbers")
public class DialNumber {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String number;
    public String date;

    @Nullable
    public Double latitude;
    @Nullable
    public Double longitude;
}