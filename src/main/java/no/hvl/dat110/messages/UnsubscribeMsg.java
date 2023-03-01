package no.hvl.dat110.messages;

public class UnsubscribeMsg extends Message {

	// message sent from client to unsubscribe on a topic

    private String topic;

    public UnsubscribeMsg(String user, String topic) {
        super(MessageType.UNSUBSCRIBE, user);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public String toString(){
        return "Message [type=" + super.getType() + ", user=" + super.getUser() + ", topic=" + this.getTopic() + "]";
    }

}
