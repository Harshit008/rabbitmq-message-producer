package com.zensar.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.beans.JsonOrderBean;
import com.zensar.beans.XmlFulfilmentOrderBean;
import com.zensar.config.MessageConfig;
import com.zensar.exceptions.MacysRunTimeException;

@RequestMapping("/producer")
@RestController
public class MessageProducerController {

	private static final Logger logger = LoggerFactory.getLogger(MessageProducerController.class);
	StringBuffer cause= new StringBuffer();
	
	@Qualifier(value = "templateForJson")
	@Autowired
	AmqpTemplate templateforJson;
	
	@Qualifier(value = "templateForXML")
	@Autowired
	AmqpTemplate templateforXml;
	
	@PostMapping("/orderJsonMessage")
	public String produceJsonMessage(@RequestBody JsonOrderBean order){
		try {
				logger.info("Json order being pushed to json_order_queue: "+order);
				templateforJson.convertAndSend(MessageConfig.JSON_EXCHANGE, MessageConfig.JSON_ROUTING_KEY, order);
		}catch (MacysRunTimeException e) {

			logger.error("--------------------EXCEPTIONAL EVENT LOG BEGINs-----------------------------------");
			logger.error("ERROR MESSAGE AS--" + e.getMessage() + " ERROR CAUSE :" + e.getCause());
			logger.error("Exception caught in produceJsonMessage Method--");
			logger.error("--------------------EXCEPTIONAL EVENT LOG ENDS-----------------------------------");
			cause.append(", ").append(e.getMessage());

		} catch (Exception e) {

			logger.error("--------------------EXCEPTIONAL EVENT LOG BEGINs-----------------------------------");
			logger.error("ERROR MESSAGE AS--" + e.getMessage() + " ERROR CAUSE :" + e.getCause());
			logger.error("Exception caught in produceJsonMessage Method--");
			logger.error("--------------------EXCEPTIONAL EVENT LOG ENDS-----------------------------------");
			cause.append(", ").append(e.getMessage());
			throw new MacysRunTimeException(e.getMessage());
		}
		
		return "Success!!";
		
	}
	
	@PostMapping("/orderXmlMessage")
	public String produceXmlMessage(@RequestBody XmlFulfilmentOrderBean order) {
		try {
				logger.info("Xml Order being pushed to xml_order_queue: "+order);
				templateforXml.convertAndSend(MessageConfig.XML_EXCHANGE, MessageConfig.XML_ROUTING_KEY, order);
				
		}catch (MacysRunTimeException e) {

			logger.error("--------------------EXCEPTIONAL EVENT LOG BEGINs-----------------------------------");
			logger.error("ERROR MESSAGE AS--" + e.getMessage() + " ERROR CAUSE :" + e.getCause());
			logger.error("Exception caught in produceXmlMessage Method--");
			logger.error("--------------------EXCEPTIONAL EVENT LOG ENDS-----------------------------------");
			cause.append(", ").append(e.getMessage());

		} catch (Exception e) {

			logger.error("--------------------EXCEPTIONAL EVENT LOG BEGINs-----------------------------------");
			logger.error("ERROR MESSAGE AS--" + e.getMessage() + " ERROR CAUSE :" + e.getCause());
			logger.error("Exception caught in produceXmlMessage Method--");
			logger.error("--------------------EXCEPTIONAL EVENT LOG ENDS-----------------------------------");
			cause.append(", ").append(e.getMessage());
			throw new MacysRunTimeException(e.getMessage());
		}
		
		return "Success!!";
	}

	public MessageProducerController(AmqpTemplate templateforJson, AmqpTemplate templateforXml) {
		super();
		this.templateforJson = templateforJson;
		this.templateforXml = templateforXml;
	}

	public MessageProducerController() {
		super();
	}
	
}
