package be.defrere.wallr.models;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Date;

/**
 * Created by Jonas on 22/11/2017.
 */

public class User extends SugarRecord {

    @Unique
    private int id;
    private String device_name;
    private String device_uuid;
    private String device_verifytoken;
    private String api_token;
    private Date created_at;
    private Date updated_at;

    public User() {
    }

    public User(int id, String device_name, String device_uuid, String device_verifytoken, String api_token) {
        this.id = id;
        this.device_name = device_name;
        this.device_uuid = device_uuid;
        this.device_verifytoken = device_verifytoken;
        this.api_token = api_token;
        this.created_at = new Date();
        this.updated_at = new Date();
    }

    public User(int id, String device_name, String device_uuid, String device_verifytoken, String api_token, Date created_at, Date updated_at) {
        this.id = id;
        this.device_name = device_name;
        this.device_uuid = device_uuid;
        this.device_verifytoken = device_verifytoken;
        this.api_token = api_token;
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

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.updated_at = new Date();
        this.device_name = device_name;
    }

    public String getDevice_uuid() {
        return device_uuid;
    }

    public void setDevice_uuid(String device_uuid) {
        this.updated_at = new Date();
        this.device_uuid = device_uuid;
    }

    public String getDevice_verifytoken() {
        return device_verifytoken;
    }

    public void setDevice_verifytoken(String device_verifytoken) {
        this.updated_at = new Date();
        this.device_verifytoken = device_verifytoken;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.updated_at = new Date();
        this.api_token = api_token;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }
}
