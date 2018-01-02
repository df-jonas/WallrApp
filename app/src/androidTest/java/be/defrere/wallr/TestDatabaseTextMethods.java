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
import be.defrere.wallr.entity.Text;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestDatabaseTextMethods {

    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();

        db.eventDao().insert(TestProvider.createEvent());
        Text t = TestProvider.createText();
        t.setEventId(db.eventDao().last().get(0).getId());
        db.textDao().insert(t);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertText() throws Exception {
        db.eventDao().insert(TestProvider.createEvent());
        Text t1 = TestProvider.createText();
        t1.setEventId(db.eventDao().last().get(0).getId());
        db.textDao().insert(t1);
        Text t2 = db.textDao().last().get(0);

        assertTrue("The compared texts should be the same.", t1.equals(t2));
    }

    @Test
    public void updateText() throws Exception {
        Text t2 = db.textDao().last().get(0);
        t2.setContent("HELLO This is a test message");
        db.textDao().update(t2);
        Text t3 = db.textDao().last().get(0);
        assertTrue("The compared texts should be the same.", t2.equals(t3));
    }

    @Test
    public void findTextByEventId() throws Exception {
        Text t1 = TestProvider.createText();
        t1.setEventId(db.eventDao().last().get(0).getId());
        t1.setId(100);
        db.textDao().insert(t1);
        Text t2 = db.textDao().findByEvent(t1.getEventId()).get(0);

        assertTrue("The compared events should be the same.", t1.equals(t2));
    }

    @Test
    public void deleteText() throws Exception {
        Text t2 = db.textDao().last().get(0);
        int oldVal = db.textDao().all().size();
        db.textDao().delete(t2);
        int newVal = db.textDao().all().size();
        assertTrue("The count of objects should differ by 1.", newVal == oldVal - 1);
    }
}