package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.common.TODO;

public class TemperatureDevice {

	private static final int COUNT = 10;

	public static void main(String[] args) {

		// simulated / virtual temperature sensor
		TemperatureSensor sn = new TemperatureSensor();

		// create a client object - user "sensor" as the user name
		Client client = new Client("sensor", Common.BROKERHOST, Common.BROKERPORT);

		// connect to the broker
		while (!client.connect()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		// publish the temperature(s)
		for (int i = 0; i < COUNT; i++){
			int temperature = sn.read();

			client.publish(Common.TEMPTOPIC, "Temperature measured: " + temperature);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		// - disconnect from the broker
		client.disconnect();

		System.out.println("Temperature device stopping ... ");
	}
}
