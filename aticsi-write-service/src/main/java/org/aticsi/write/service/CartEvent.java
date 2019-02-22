package org.aticsi.write.service;

import java.util.UUID;

public class CartEvent {
	private final String eventID = UUID.randomUUID().toString();

	private final String ean;
	@Override
	public String toString() {
		return "CartEvent [eventID=" + eventID + ", ean=" + ean + ", qte=" + qte + "]";
	}

	private final String qte;

	public CartEvent(String ean, String qte) {
		super();
		this.ean = ean;
		this.qte = qte;
	}

	public String getEan() {
		return ean;
	}

	public String getQte() {
		return qte;
	}

	public String getEventID() {
		return eventID;
	}
}