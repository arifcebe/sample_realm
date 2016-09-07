package com.arifcebe.samplerealm.model;

import io.realm.RealmObject;

/**
 * Created by arifcebe on 6/13/16.
 */
public class Dog extends RealmObject {

    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
