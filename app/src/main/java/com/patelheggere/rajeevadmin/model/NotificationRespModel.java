package com.patelheggere.rajeevadmin.model;

public class NotificationRespModel {
public String multicast_id;
public long success;
public long failure;
public long canonical_ids;

    public NotificationRespModel() {
    }

    public NotificationRespModel(String multicast_id, long success, long failure, long canonical_ids) {
        this.multicast_id = multicast_id;
        this.success = success;
        this.failure = failure;
        this.canonical_ids = canonical_ids;
    }

    public String getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(String multicast_id) {
        this.multicast_id = multicast_id;
    }

    public long getSuccess() {
        return success;
    }

    public void setSuccess(long success) {
        this.success = success;
    }

    public long getFailure() {
        return failure;
    }

    public void setFailure(long failure) {
        this.failure = failure;
    }

    public long getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(long canonical_ids) {
        this.canonical_ids = canonical_ids;
    }
}
