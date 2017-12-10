package be.defrere.wallr.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import be.defrere.wallr.entity.Text;

@Dao
public interface TextDao {

    @Query("SELECT * FROM texts")
    List<Text> all();

    @Query("SELECT * FROM texts where id=:id")
    Text findById(int id);

    @Insert
    void insert(Text text);

    @Update
    void update(Text text);

    @Delete
    void delete(Text text);
}
