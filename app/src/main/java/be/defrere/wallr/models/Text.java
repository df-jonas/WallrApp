package be.defrere.wallr.models;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Date;

/**
 * Created by Jonas on 22/11/2017.
 */

public class Text extends SugarRecord {

    @Unique
    private int id;
    private int event_id;
    private String source;
    private String content;
    private Date created_at;
    private Date updated_at;

    public Text() {
    }

    public Text(int id, int event_id, String source, String content) {

        this.id = id;
        this.event_id = event_id;
        this.source = source;
        this.content = content;
        this.created_at = new Date();
        this.updated_at = new Date();
    }

    public Text(int id, int event_id, String source, String content, Date created_at, Date updated_at) {
        this.id = id;
        this.event_id = event_id;
        this.source = source;
        this.content = content;
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

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.updated_at = new Date();
        this.event_id = event_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.updated_at = new Date();
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.updated_at = new Date();
        this.content = content;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }
}
