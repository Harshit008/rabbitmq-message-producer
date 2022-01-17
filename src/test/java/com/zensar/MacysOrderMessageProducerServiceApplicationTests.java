package com.zensar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.zensar.beans.JsonOrderBean;
import com.zensar.beans.XmlFulfilmentOrderBean;
import com.zensar.controller.MessageProducerController;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MacysOrderMessageProducerServiceApplicationTests {

	@Qualifier("templateForJson")
	@Mock
	private AmqpTemplate templateforJson;
	
	@Qualifier("templateForXML")
	@Mock
	private AmqpTemplate templateforXml;
	
	@Mock
	private JsonOrderBean jsonOrder;
	
	@Mock
	private XmlFulfilmentOrderBean xmlOrder;
	
	@InjectMocks
	private MessageProducerController controller = new MessageProducerController();
	

	@BeforeEach
	void setUp() {
		this.controller= new MessageProducerController(templateforJson, templateforXml);
	}
	
	@Test
	void testForProducerJson() {
		String produceJsonMessage = controller.produceJsonMessage(jsonOrder);
		assertEquals("Success!!", produceJsonMessage);
		
	}
	
	@Test
	void testForProducerXml() {
		String produceJsonMessage = controller.produceXmlMessage(xmlOrder);
		assertEquals("Success!!", produceJsonMessage);
		
	}
}
