package com.zensar.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.zensar.beans.JsonOrderBean;
import com.zensar.config.MessageConfig;

@RestController
public class MessageProducerController {

	@Autowired
	RabbitTemplate template;
	
	@PostMapping("/orderMessage")
	public String testApi(@RequestBody JsonOrderBean order) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		//String json = ow.writeValueAsString(order);
		template.convertAndSend(MessageConfig.EXCHANGE, MessageConfig.ROUTING_KEY, order);
		return "Success!!";
		
	}
}
