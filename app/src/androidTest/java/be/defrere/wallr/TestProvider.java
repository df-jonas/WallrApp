package be.defrere.wallr;

import be.defrere.wallr.entity.Event;
import be.defrere.wallr.entity.Text;

/**
 * Created by Jonas on 2/01/2018.
 */

public class TestProvider {
    public static Event createEvent() {
        Event e = new Event();
        e.setName("Event Test");
        e.setKeyword("TEST");
        e.setPhone("0499887766");
        e.setPublicEventId("mKLPOr3KxjGtKLmv");
        return e;
    }

    public static Text createText() {
        Text t = new Text();
        t.setSource("0499887766");
        t.setContent("TEST This is a test message.");
        return t;
    }
}
