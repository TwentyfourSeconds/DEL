package twentyfour_seconds.com.del.chat;

public class UserDTO {
    String uid;
    String username;
    int age;
    int gender;
    String location;
    String profile;
    String profileImageUrl;
    String filename;
    String regionSetting;

    public UserDTO(){
    }

    //チャット実施時に使用するコンストラクタ
    public UserDTO(String uid, String username, int age, int gender, String location,String profile,  String profileImageUrl, String filename, String regionSetting){
        this.uid = uid;
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.profile = profile;
        this.profileImageUrl = profileImageUrl;
        this.filename = filename;
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

    public String getLocation() {
        return location;
    }

    public String getProfile() {
        return profile;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getFilename() {
        return filename;
    }

    public String getRegionSetting() {
        return regionSetting;
    }


}

