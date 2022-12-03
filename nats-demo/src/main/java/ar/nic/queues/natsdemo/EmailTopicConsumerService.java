package ar.nic.queues.natsdemo;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/*
 * Email notification topic consumer.
 */
@Service
public class EmailTopicConsumerService implements MessageHandler{

    Logger logger = LoggerFactory.getLogger(NatsConfig.class);

    final static String TOPIC_NAME="email-notification";

    final Connection connection;

    @Autowired
    public EmailTopicConsumerService(Connection connection) throws IOException {
        this.connection = connection;
        this.connection.subscribe(TOPIC_NAME,this);

        //TODO: The following lines are only to test the consumer at init.
        Message message = new Message();
        message.setSubject(TOPIC_NAME);
        message.setData("HELLO WORLD THIS IS SOMETHING WRITING IN A QUEUE".getBytes());
        this.connection.publish(message);
    }


    @Override
    public void onMessage(Message message) {
        logger.info(message.toString());
    }
}
