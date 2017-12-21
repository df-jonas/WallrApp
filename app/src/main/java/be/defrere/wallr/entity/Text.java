package be.defrere.wallr.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "texts",
        foreignKeys = @ForeignKey(
                entity = Event.class,
                parentColumns = "id",
                childColumns = "event_id",
                onDelete = CASCADE
        )
)
public class Text {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "event_id")
    private int eventId;

    @ColumnInfo(name = "source")
    private String source;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "is_synced")
    private boolean synced;

    public Text() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    @Override
    public String toString() {
        return source + ": " + content;
    }
}
