package org.aticsi.event.consumer.dao;

import org.aticsi.event.consumer.CartEvent;
import org.springframework.stereotype.Component;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.KeyFactory;

@Component
public class EventDSDAO implements EventDAO {
	private Datastore datastore;
	private KeyFactory keyFactory;

	public EventDSDAO() {
		datastore = DatastoreOptions.getDefaultInstance().getService(); // Authorized Datastore service
		keyFactory = datastore.newKeyFactory().setKind("CartEvent"); // Is used for creating keys later
	}

	public void createEvent(CartEvent event) {
		IncompleteKey key = keyFactory.newKey(); // Key will be assigned once written
		FullEntity<IncompleteKey> incBookEntity = Entity.newBuilder(key).set(CartEvent.EVENT_ID, event.getEventID())
				.set(CartEvent.QTE, event.getQte()).set(CartEvent.EAN, event.getEan()).build();
		datastore.add(incBookEntity);
	}
}
