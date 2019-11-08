package twentyfour_seconds.com.del.event_management;

public class GroupMembersDTO {
    Long id;
    String uid;
    String username;
    String profileImageUrl;

    public GroupMembersDTO(){}

    public GroupMembersDTO(String uid){
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getUid() {
        return uid;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}


