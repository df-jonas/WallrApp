package be.defrere.wallr.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @Ignore
    public static User currentUser = null;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "device_name")
    private String deviceName;

    @ColumnInfo(name = "device_uuid")
    private String deviceUuid;

    @ColumnInfo(name = "device_verifytoken")
    private String deviceVerifytoken;

    @ColumnInfo(name = "api_token")
    private String apiToken;

    @ColumnInfo(name = "isSynced")
    private boolean synced;

    public User() {
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public String getDeviceVerifytoken() {
        return deviceVerifytoken;
    }

    public void setDeviceVerifytoken(String deviceVerifytoken) {
        this.deviceVerifytoken = deviceVerifytoken;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    @Override
    public String toString() {
        return deviceName + " (" + deviceUuid + ")";
    }
}
