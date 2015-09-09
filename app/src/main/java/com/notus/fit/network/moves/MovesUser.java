package com.notus.fit.network.moves;


// Referenced classes of package com.notus.fit.network.moves:
//            MovesUserProfile

public class MovesUser {

    boolean caloriesAvailable;
    String platform;
    MovesUserProfile profile;
    String userId;

    public MovesUser() {
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String s) {
        platform = s;
    }

    public MovesUserProfile getProfile() {
        return profile;
    }

    public void setProfile(MovesUserProfile movesuserprofile) {
        profile = movesuserprofile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String s) {
        userId = s;
    }

    public boolean isCaloriesAvailable() {
        return caloriesAvailable;
    }

    public void setCaloriesAvailable(boolean flag) {
        caloriesAvailable = flag;
    }

    public String toString() {
        return (new StringBuilder()).append("MovesUser{userId='").append(userId).append('\'').append(", profile=").append(profile).append(", caloriesAvailable=").append(caloriesAvailable).append(", platform='").append(platform).append('\'').append('}').toString();
    }
}
