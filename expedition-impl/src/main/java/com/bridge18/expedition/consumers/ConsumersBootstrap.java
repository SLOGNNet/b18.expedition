package com.bridge18.expedition.consumers;

import com.bridge18.service.streams.Consumer;
import com.bridge18.service.streams.kafka.KafkaConsumerGroup;
import com.bridge18.service.streams.kafka.KafkaProducer;
import com.google.inject.Singleton;
import play.Configuration;

import java.util.Arrays;

@Singleton
public class ConsumersBootstrap {
    public ConsumersBootstrap(Configuration configuration,
                              TaskNewLoadConsumer newLoadConsumer,
                              TaskAddLoadDetailsConsumer addLoadDetailsConsumer) {

        String kafkaServers = configuration.getString("kafka.servers");

        String kafkaAckMode = configuration.getString("kafka.ackMode");

        Integer kafkaRetries = configuration.getInt("kafka.retries");

        Integer kafkaBatchSize = configuration.getInt("kafka.batchSize");

        Integer kafkaLingerMs = configuration.getInt("kafka.lingerMs");

        Integer kafkaBufferMemory = configuration.getInt("kafka.buffer");


        KafkaProducer kafkaProducer = new KafkaProducer(kafkaServers, kafkaAckMode, kafkaRetries,
                kafkaBatchSize, kafkaLingerMs, kafkaBufferMemory);

        KafkaConsumerGroup newLoadGroup = new KafkaConsumerGroup(
                kafkaProducer, kafkaServers, (Consumer) newLoadConsumer, "camunda-task-new-load",
                true, 100,
                Arrays.asList("camunda-task-new-load"), 10, 10, 1
        );

        newLoadGroup.start();

        KafkaConsumerGroup addLoadDetailsGroup = new KafkaConsumerGroup(
                kafkaProducer, kafkaServers, (Consumer) addLoadDetailsConsumer, "camunda-task-new-load",
                true, 100,
                Arrays.asList("camunda-task-add-load-details"), 10, 10, 1
        );

        addLoadDetailsGroup.start();
    }
}