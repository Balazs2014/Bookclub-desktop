package com.example.bookclubdesktop;

import java.time.LocalDate;
import java.util.Objects;

public class Member {
    private int id;
    private String name;
    private String gender;
    private LocalDate birth_date;
    private boolean banned;

    public Member(int id, String name, String gender, LocalDate birth_date, boolean banned) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birth_date = birth_date;
        this.banned = banned;
    }

    public boolean isBanned() {
        return banned;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public String getBanned() {
        return this.banned ? "X" : "";
    }

    public String getStringGender() {
        return Objects.equals(this.gender, "F") ? "Nő" : Objects.equals(this.gender, "M") ? "Férfi" : "Ismeretlen";
    }
}
