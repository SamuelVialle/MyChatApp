package com.samuelvialle.mychatapp.f3_find_friend;

public class F32_FindFriendModel {
    private static boolean requestSent;
    /** 1 Ajout des variables **/
    private String name;
    private String avatar;
    private String userId;

    /** 2 Géneration des constructeurs **/

    // 2.1 Un constructeur vide
    public F32_FindFriendModel() {
    }

    // 2.2 Un constructeur avec nos variables
    public F32_FindFriendModel(String name, String avatar, String userId, boolean requestSent) {
        this.name = name;
        this.avatar = avatar;
        this.userId = userId;
        this.requestSent = requestSent;
    }


    /** 3 Création des getter et setters **/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static boolean isRequestSent() {
        return requestSent;
    }

    public void setRequestSent(boolean requestSent) {
        this.requestSent = requestSent;
    }
}
