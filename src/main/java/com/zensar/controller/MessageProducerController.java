package com.zensar.controller;

import org.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.beans.JsonOrderBean;
import com.zensar.config.MessageConfig;


@RestController
public class MessageProducerController {

	
	@Qualifier(value = "templateForJson")
	@Autowired
	AmqpTemplate templateforJson;
	
	@Qualifier(value = "templateForXML")
	@Autowired
	AmqpTemplate templateforXml;
	
	@PostMapping("/orderJsonMessage")
	public String produceJsonMessage(@RequestBody JsonOrderBean order){
		System.out.println(order);
		JSONObject json = new JSONObject(order);
		templateforJson.convertAndSend(MessageConfig.JSON_EXCHANGE, MessageConfig.JSON_ROUTING_KEY, order);
		//template.convertAndSend(MessageConfig.JSON_EXCHANGE, MessageConfig.JSON_ROUTING_KEY, order);
		return "Success!!";
		
	}
	
	@PostMapping("/orderXmlMessage")
	public String produceXmlMessage(@RequestBody JsonOrderBean order) {
		System.out.println(order);
//		JSONObject json = new JSONObject(order);
//		String xml = XML.toString(json);
		templateforXml.convertAndSend(MessageConfig.XML_EXCHANGE, MessageConfig.XML_ROUTING_KEY, order);
		return "Success!!";
		
	}
}
