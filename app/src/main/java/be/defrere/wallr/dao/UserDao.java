package be.defrere.wallr.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import be.defrere.wallr.entity.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    List<User> all();

    @Query("SELECT * FROM users where id=:id")
    User findById(int id);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}