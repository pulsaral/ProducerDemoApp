package com.demo.producer.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
	
	private static final List<String> DEFAULT_TOPICS = Arrays.asList("testtopic", "testtopic1");

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void publishToDefaultTopics(String message) {
		DEFAULT_TOPICS.forEach(t -> publishToTopic(t, message));
	}
	
	public void publishToTopics(List<String> topics, String message) {
		topics.forEach(t -> publishToTopic(t, message));
	}
	
	public void publishToTopic(String topic, String message) {
		System.out.println("Producing message: " + message);
		if (message != null) kafkaTemplate.send(topic, message);
		System.out.println("Produced: " + message);
	}

}
