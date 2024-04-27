package org.example;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class AvroTestProducer {

    private static Logger logger = LoggerFactory.getLogger(AvroTestProducer.class);

    public static void main(String[] args) {
        Properties properties = new Properties();

        logger.info("Starting producer.");

        properties.setProperty("bootstrap.servers", "127.0.0.1:19092");
        // properties.setProperty("bootstrap.servers", "127.0.0.1:2181");
        properties.setProperty("retries", "2");
        properties.setProperty("acks", "all");

        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://127.0.0.1:8081");

        Producer<String, Customer> producer = new KafkaProducer<String, Customer>(properties);

        String topic = "customer";

        Customer customer = Customer.newBuilder()
                .setFirstName("John")
                .setLastName("Wick")
                .setAge(43)
                .build();

        ProducerRecord<String, Customer> producerRecord = new ProducerRecord<String, Customer>(
                topic, customer
        );


        Future<RecordMetadata> result =
                producer.send(producerRecord);
        try {
            RecordMetadata recordMetadata = result.get();
            logger.info("Sent data, offset: {}",  recordMetadata.offset());

        } catch (InterruptedException | ExecutionException exception) {
            exception.printStackTrace();
        }

        producer.flush();
        producer.close();

    }
}
