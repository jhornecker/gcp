package org.aticsi.write.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.GsonBuilder;

@RestController
public class EventController {

	@Autowired
	private PubSubPublisher publisher;

	public PubSubPublisher getPublisher() {
		return publisher;
	}

	public void setPublisher(PubSubPublisher publisher) {
		this.publisher = publisher;
	}

	@RequestMapping("/cartEvent")
	public CartEvent event(@RequestParam(value = "EAN", defaultValue = "1234567890123") String ean,
			@RequestParam(value = "qte", defaultValue = "0") String qte) {
		CartEvent event = new CartEvent(ean, qte);

		try {
			publisher.publish(new GsonBuilder().create().toJson(event));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return event;
	}
}
