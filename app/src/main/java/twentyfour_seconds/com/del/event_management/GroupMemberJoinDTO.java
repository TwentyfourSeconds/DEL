package twentyfour_seconds.com.del.event_management;

public class GroupMemberJoinDTO {

    String uid;

    public GroupMemberJoinDTO(String uid){
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
