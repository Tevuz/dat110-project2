package no.hvl.dat110.messages;

public class CreateTopicMsg extends Message {
	
	// message sent from client to create topic on the broker

    private String topic;

    public CreateTopicMsg(String user, String topic) {
        super(MessageType.CREATETOPIC, user);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return "[type=" + super.getType() + ", user=" + this.getUser() + ", topic=" + this.getTopic() + "]";
    }
}
