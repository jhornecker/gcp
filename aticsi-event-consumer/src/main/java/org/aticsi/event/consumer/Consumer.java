package org.aticsi.event.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.aticsi.event.consumer.dao.EventDSDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.gson.GsonBuilder;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

@Component
public class Consumer implements MessageReceiver {

	// use the default project id
	private static final String PROJECT_ID = "aticsi-pubsub";
	private static final String SUB_ID = "my-sub";
	private static final BlockingQueue<PubsubMessage> messages = new LinkedBlockingDeque<>();

	Publisher publisher;

	@Autowired
	EventDSDAO eventDSDAO;

	public EventDSDAO getEventDSDAO() {
		return eventDSDAO;
	}

	public void setEventDSDAO(EventDSDAO eventDSDsdao) {
		this.eventDSDAO = eventDSDsdao;
	}

	public Consumer() {

		ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(PROJECT_ID, SUB_ID);
		Subscriber subscriber = Subscriber.newBuilder(subscriptionName, this).build();
		subscriber.startAsync().awaitRunning();
	}

	@Override
	public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
		messages.offer(message);
		consumer.ack();

		System.out.println(" receiver ..... " + message.getMessageId());

		String dataMessage = message.getData().toStringUtf8();
		System.out.println(" receiver ..... " + dataMessage);

		CartEvent cartEvent = new GsonBuilder().create().fromJson(dataMessage, CartEvent.class);
		eventDSDAO.createEvent(cartEvent);
	}
}