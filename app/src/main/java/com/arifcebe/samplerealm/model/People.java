package com.arifcebe.samplerealm.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by arifcebe on 6/13/16.
 */
public class People extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private int age;
    private RealmList<Dog> dog;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Dog> getDog() {
        return dog;
    }

    public void setDog(RealmList<Dog> dog) {
        this.dog = dog;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
