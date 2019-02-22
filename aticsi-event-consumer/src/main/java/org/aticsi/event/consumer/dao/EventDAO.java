package org.aticsi.event.consumer.dao;

import org.aticsi.event.consumer.CartEvent;

public interface EventDAO {
	public void createEvent(CartEvent event);
}
