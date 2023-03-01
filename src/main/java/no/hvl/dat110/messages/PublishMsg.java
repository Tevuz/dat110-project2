package no.hvl.dat110.messages;

import no.hvl.dat110.common.TODO;

public class PublishMsg extends Message {
	
	// message sent from client to create publish a message on a topic

	private String topic;
	private String message;

	public PublishMsg(String user, String topic, String message) {
		super(MessageType.PUBLISH, user);
		this.topic = topic;
		this.message = message;
	}

	public String getTopic() {
		return topic;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "[type=" + super.getType() + ", user=" + this.getUser() + ", topic=" + this.getTopic() + ", message=" + this.getMessage() + "]";
	}
}
