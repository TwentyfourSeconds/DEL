package twentyfour_seconds.com.del.chat;

public class UserDTO {
    String uid;
    String username;
    String email;
    String password;
    int age;
    String sex;
    String profile;
    String profileImageUrl;
    String regionSetting;

    public UserDTO(){
    }

    //チャット実施時に使用するコンストラクタ
    public UserDTO(String uid, String username, String profileImageUrl){
        this.uid = uid;
        this.username = username;
        this.age = age;
        this.sex = sex;
        this.profile = profile;
        this.profileImageUrl = profileImageUrl;
        this.regionSetting = regionSetting;
    }

}

