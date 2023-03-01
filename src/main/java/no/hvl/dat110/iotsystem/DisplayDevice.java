package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messages.PublishMsg;
import no.hvl.dat110.common.TODO;

public class DisplayDevice {
	
	private static final int COUNT = 10;
		
	public static void main (String[] args) {
		
		System.out.println("Display starting ...");

		// create a client object - use "display" as the username
		Client client = new Client("display", Common.BROKERHOST, Common.BROKERPORT);
		
		// - connect to the broker
		while (!client.connect()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		// - create the temperature topic on the broker
		client.createTopic(Common.TEMPTOPIC);

		// - subscribe to the topic
		client.subscribe(Common.TEMPTOPIC);

		// - receive messages on the topic
		for (int i = 0; i < COUNT; i++) {
			Message msg = client.receive();

			Logger.log(msg.toString());
		}

		// - unsubscribe from the topic
		client.unsubscribe(Common.TEMPTOPIC);

		// - disconnect from the broker
		client.disconnect();
		
		System.out.println("Display stopping ... ");
		
	}
}
