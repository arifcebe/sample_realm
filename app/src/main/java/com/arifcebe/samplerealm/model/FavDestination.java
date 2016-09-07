package com.arifcebe.samplerealm.model;

import io.realm.RealmObject;

/**
 * Created by arifcebe on 9/6/16.
 */
public class FavDestination extends RealmObject{
    private String arrival_city;
    private String business_name;
    private String province_name;

    public String getArrival_city() {
        return arrival_city;
    }

    public void setArrival_city(String arrival_city) {
        this.arrival_city = arrival_city;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }
}

