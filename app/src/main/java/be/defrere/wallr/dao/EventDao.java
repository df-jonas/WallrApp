package be.defrere.wallr.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import be.defrere.wallr.entity.Event;

@Dao
public interface EventDao {

    @Query("SELECT * FROM events")
    List<Event> all();

    @Query("SELECT * FROM events where id=:id")
    Event findById(int id);

    @Query("SELECT * FROM events ORDER BY id DESC LIMIT 1;")
    List<Event> last();

    @Insert
    void insert(Event event);

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);
}
