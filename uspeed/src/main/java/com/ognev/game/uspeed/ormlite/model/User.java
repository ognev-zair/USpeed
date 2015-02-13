// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.ormlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class User {

    @DatabaseField
    private String birthDay;
    @DatabaseField
    private String email;
    @DatabaseField
    private String facebookId;
    @DatabaseField
    private String facebookToken;
    @DatabaseField
    private String gender;
    @DatabaseField(id = true, unique = true)
    private String id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String phoneNumber;
    @DatabaseField
    private String surname;
    @DatabaseField
    private boolean systemLanguage;

    public User() {
    }

    public User(String s) {
        id = s;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            User user = (User) obj;
            if (facebookToken == null ? user.facebookToken != null : !facebookToken.equals(user.facebookToken)) {
                return false;
            }
            if (id == null ? user.id != null : !id.equals(user.id)) {
                return false;
            }
            if (name == null ? user.name != null : !name.equals(user.name)) {
                return false;
            }
            if (phoneNumber == null ? user.phoneNumber != null : !phoneNumber.equals(user.phoneNumber)) {
                return false;
            }
            if (surname == null ? user.surname != null : !surname.equals(user.surname)) {
                return false;
            }
        }
        return true;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public String getEmail() {
        return email;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public String getGender() {
        return gender;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public boolean getSystemLanguage() {
        return systemLanguage;
    }

    public int hashCode() {
        int i;
        int j;
        int k;
        int l;
        int i1;
        int j1;
        int k1;
        int l1;
        String s;
        int i2;
        if (id != null) {
            i = id.hashCode();
        } else {
            i = 0;
        }
        j = i * 31;
        if (name != null) {
            k = name.hashCode();
        } else {
            k = 0;
        }
        l = 31 * (j + k);
        if (surname != null) {
            i1 = surname.hashCode();
        } else {
            i1 = 0;
        }
        j1 = 31 * (l + i1);
        if (phoneNumber != null) {
            k1 = phoneNumber.hashCode();
        } else {
            k1 = 0;
        }
        l1 = 31 * (j1 + k1);
        s = facebookToken;
        i2 = 0;
        if (s != null) {
            i2 = facebookToken.hashCode();
        }
        return l1 + i2;
    }

    public void setBirthDay(String s) {
        birthDay = s;
    }

    public void setEmail(String s) {
        email = s;
    }

    public void setFacebookId(String s) {
        facebookId = s;
    }

    public void setFacebookToken(String s) {
        facebookToken = s;
    }

    public void setGender(String s) {
        gender = s;
    }

    public void setId(String s) {
        id = s;
    }

    public void setName(String s) {
        name = s;
    }

    public void setPhoneNumber(String s) {
        phoneNumber = s;
    }

    public void setSurname(String s) {
        surname = s;
    }

    public void setSystemLanguage(boolean flag) {
        systemLanguage = flag;
    }

    public String toString() {
        return (new StringBuilder()).append("User{id='").append(id).append('\'').append(", name='").append(name).append('\'').append(", surname='").append(surname).append('\'').append(", phoneNumber='").append(phoneNumber).append('\'').append(", facebookToken='").append(facebookToken).append('\'').append('}').toString();
    }
}
