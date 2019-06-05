package android.epita.fr.gitandroidapi.models;

import io.realm.RealmObject;

class Dog extends RealmObject {
    private String name;
    private int age;

    // ... Generated getters and setters ...

    public Dog()
    {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}