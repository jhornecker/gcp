package org.aticsi.write.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;

@Component
public class PubSubPublisher {

	// use the default project id
	private static final String PROJECT_ID = "aticsi-pubsub";
	private static final String TOPIC_ID = "my-topic";

	Publisher publisher;

	public PubSubPublisher() {
		ProjectTopicName topicName = ProjectTopicName.of(PROJECT_ID, TOPIC_ID);
		try {
			publisher = Publisher.newBuilder(topicName).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Publish messages to a topic.
	 * 
	 * @param args topic name, number of messages
	 */
	public void publish(String event) throws Exception {
		List<ApiFuture<String>> futures = new ArrayList<>();
		System.out.println("in publish method : " + event);
		try {
			// convert message to bytes
			PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(event)).build();

			// Schedule a message to be published. Messages are automatically batched.
			ApiFuture<String> future = publisher.publish(pubsubMessage);
			System.out.println("message published");

			futures.add(future);
			// Wait on any pending requests
			List<String> messageIds = ApiFutures.allAsList(futures).get();

		} finally {

		}
	}
}