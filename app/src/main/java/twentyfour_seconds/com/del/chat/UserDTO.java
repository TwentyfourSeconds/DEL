package twentyfour_seconds.com.del.chat;

public class UserDTO {
    String uid;
    String username;
    int age;
    int gender;
    String profile;
    String profileImageUrl;
    String regionSetting;

    public UserDTO(){
    }

    //チャット実施時に使用するコンストラクタ
    public UserDTO(String uid, String username, int age, int gender, String profile,  String profileImageUrl, String regionSetting){
        this.uid = uid;
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.profile = profile;
        this.profileImageUrl = profileImageUrl;
        this.regionSetting = regionSetting;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public int getGender() {
        return gender;
    }

    public String getProfile() {
        return profile;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getRegionSetting() {
        return regionSetting;
    }


}

