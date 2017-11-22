package be.defrere.wallr.models;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Date;

/**
 * Created by Jonas on 22/11/2017.
 */

public class Event extends SugarRecord {

    @Unique
    private int id;
    private int user_id;
    private String name;
    private String public_event_id;
    private Date created_at;
    private Date updated_at;

    public Event() {
    }

    public Event(int id, int user_id, String name, String public_event_id) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.public_event_id = public_event_id;
        this.created_at = new Date();
        this.updated_at = new Date();
    }

    public Event(int id, int user_id, String name, String public_event_id, Date created_at, Date updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.public_event_id = public_event_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getIdentifier() {
        return id;
    }

    public void setIdentifier(int id) {
        this.updated_at = new Date();
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.updated_at = new Date();
        this.user_id = user_id;
    }

    public String getEventName() {
        return name;
    }

    public void setEventName(String name) {
        this.updated_at = new Date();
        this.name = name;
    }

    public String getPublic_event_id() {
        return public_event_id;
    }

    public void setPublic_event_id(String public_event_id) {
        this.updated_at = new Date();
        this.public_event_id = public_event_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }
}
