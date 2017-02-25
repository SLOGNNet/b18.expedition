package com.bridge18.expedition.consumers;

import com.bridge18.service.streams.Consumer;
import com.bridge18.service.streams.kafka.KafkaConsumerGroup;
import com.bridge18.service.streams.kafka.KafkaProducer;
import com.google.inject.Inject;
import play.Configuration;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class ConsumersBootstrap {
    @Inject
    public ConsumersBootstrap(Configuration configuration,
                              TaskNewLoadConsumer newLoadConsumer,
                              TaskAddLoadDetailsConsumer addLoadDetailsConsumer) {

        Map kafkaProperitesMap = configuration.getConfig("kafka").asMap();

        Properties kafkaProperties = new Properties();
        kafkaProperties.putAll(kafkaProperitesMap);

        KafkaProducer kafkaProducer = new KafkaProducer(kafkaProperties);

        KafkaConsumerGroup newLoadGroup = new KafkaConsumerGroup(
                kafkaProducer, kafkaProperties, (Consumer) newLoadConsumer, "camunda-task-new-load",
                Arrays.asList("camunda-task-new-load"), 10, 10, 1
        );

        newLoadGroup.start();

        KafkaConsumerGroup addLoadDetailsGroup = new KafkaConsumerGroup(
                kafkaProducer, kafkaProperties, (Consumer) addLoadDetailsConsumer, "camunda-task-new-load",
                Arrays.asList("camunda-task-add-load-details"), 10, 10, 1
        );

        addLoadDetailsGroup.start();
    }
}