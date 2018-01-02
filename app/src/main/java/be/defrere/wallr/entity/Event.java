package be.defrere.wallr.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "events")
/*
foreignKeys = @ForeignKey(
    entity = User.class,
    parentColumns = "id",
    childColumns = "user_id"
)
*/
public class Event {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "keyword")
    private String keyword;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "public_event_id")
    private String publicEventId;

    @ColumnInfo(name = "is_synced")
    private boolean synced;

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPublicEventId() {
        return publicEventId;
    }

    public void setPublicEventId(String publicEventId) {
        this.publicEventId = publicEventId;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Event event = (Event) o;

        return !(name == null || keyword == null || phone == null || publicEventId == null)
                && name.equals(event.name)
                && keyword.equals(event.keyword)
                && phone.equals(event.phone)
                && publicEventId.equals(event.publicEventId);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (publicEventId != null ? publicEventId.hashCode() : 0);
        result = 31 * result + (synced ? 1 : 0);
        return result;
    }
}
