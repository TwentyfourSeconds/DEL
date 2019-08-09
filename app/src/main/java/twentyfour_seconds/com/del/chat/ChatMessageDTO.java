package twentyfour_seconds.com.del.chat;

public class ChatMessageDTO {
    String id;
    String text;
    String sendId;
    Long timeStamp;

    public ChatMessageDTO(){}

    public ChatMessageDTO(String id, String text,String sendId, Long timeStamp){
        this.id = id;
        this.text = text;
        this.sendId = sendId;
        this.timeStamp = timeStamp;
    }

}
