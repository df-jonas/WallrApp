package be.defrere.wallr;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import be.defrere.wallr.database.AppDatabase;
import be.defrere.wallr.entity.Event;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestDatabaseEventMethods {

    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();

        db.eventDao().insert(TestProvider.createEvent());
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertEvent() throws Exception {
        Event e1 = TestProvider.createEvent();
        db.eventDao().insert(e1);
        Event e2 = db.eventDao().last().get(0);

        assertTrue("The compared events should be the same.", e1.equals(e2));
    }

    @Test
    public void updateEvent() throws Exception {
        Event e2 = db.eventDao().last().get(0);
        e2.setName("Event Testing Android");
        db.eventDao().update(e2);
        Event e3 = db.eventDao().last().get(0);
        assertTrue("The compared events should be the same.", e2.equals(e3));
    }

    @Test
    public void findEventById() throws Exception {
        Event e1 = TestProvider.createEvent();
        e1.setName("Event with Id 100");
        e1.setId(100);
        db.eventDao().insert(e1);
        Event e2 = db.eventDao().findById(100);

        assertTrue("The compared events should be the same.", e1.equals(e2));
    }

    @Test
    public void deleteEvent() throws Exception {
        Event e2 = db.eventDao().last().get(0);
        int oldVal = db.eventDao().all().size();
        db.eventDao().delete(e2);
        int newVal = db.eventDao().all().size();
        assertTrue("The count of objects should differ by 1.", newVal == oldVal - 1);
    }
}