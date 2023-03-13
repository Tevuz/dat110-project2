package no.hvl.dat110.broker;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from a topic to set of pairs of subscribed users and their last received message id
	protected ConcurrentHashMap<String, Map<String, Integer>> subscriptions;

	// data structure for managing the topics messages
	// maps from a topic to map of messageID and message
	protected ConcurrentHashMap<String, NavigableMap<Integer, Message>> feeds;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	protected ConcurrentHashMap<String, ClientSession> clients;

	public Storage() {
		subscriptions = new ConcurrentHashMap<>();
		feeds = new ConcurrentHashMap<>();
		clients = new ConcurrentHashMap<>();
	}

	public Collection<ClientSession> getSessions() {
		return Collections.unmodifiableCollection(clients.values());
	}

	public Set<String> getTopics() {
		return Collections.unmodifiableSet(subscriptions.keySet());
	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	public ClientSession getSession(String user) {
		return clients.get(user);
	}

	public Set<String> getSubscribers(String topic) {
		return Collections.unmodifiableSet(subscriptions.get(topic).keySet());
	}

	public void addClientSession(String user, Connection connection) {
		clients.put(user, new ClientSession(user, connection));
	}

	public void removeClientSession(String user) {
		clients.remove(user);
	}

	public void createTopic(String topic) {
		subscriptions.putIfAbsent(topic, new ConcurrentHashMap<>());
		feeds.putIfAbsent(topic, new ConcurrentSkipListMap<>());
	}

	public void deleteTopic(String topic) {
		subscriptions.remove(topic);
		feeds.remove(topic);
	}

	public void addSubscriber(String user, String topic) {
		var subscription = subscriptions.get(topic);
		var feed = feeds.get(topic);

		if (subscription == null || feed == null)
			return;

		subscription.put(user, feed.isEmpty() ? 0 : feed.lastKey());
	}

	public void removeSubscriber(String user, String topic) {
		var subscription = subscriptions.get(topic);

		if (subscription == null)
			return;

		subscription.remove(user);
	}

	public void addMessage(String topic, Message message){
		var feed = feeds.get(topic);

		if (feed == null)
			return;

		feed.put(feed.isEmpty() ? 0 : (feed.lastKey() + 1), message);
	}

	public Collection<Message> getMessagesFor(String topic, String user){
		var feed = feeds.get(topic);
		if (feed == null)
			return Collections.emptySortedSet();

		var subscribers = subscriptions.get(topic);
		if (user == null)
			return Collections.emptySortedSet();

		var messageID = subscribers.get(user);
		if (messageID == null)
			return Collections.emptySortedSet();

		return Collections.unmodifiableCollection(feed.tailMap(messageID).values());
	}
}
