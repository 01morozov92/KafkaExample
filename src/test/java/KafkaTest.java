import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.util.List;

import static java.lang.Thread.sleep;

public class KafkaTest {

    @Test
    public void kafkaTest() {
        CustomKafkaConsumer consumer =  consumeMessage("mytopic");
        produceMessage("mytopic", "SomeMessage From Code BBLALBLALBL");
        List<ConsumerRecord<String, String>> consumerRecords = consumer.getConsumerRecordList();
        int size = consumerRecords.size()-1;
        System.out.println(consumerRecords.get(size).value());
    }


    @SneakyThrows
    public void produceMessage(String topicName, String message) {
        String bootstrapServerUrl = "localhost:9092";

        CustomKafkaProducer customKafkaProducer = new CustomKafkaProducer(message, bootstrapServerUrl, topicName);
        try {
            customKafkaProducer.doProduce();
        } catch (Exception e) {
            throw new ConnectException(message);
        }
        sleep(10000);
    }

    public CustomKafkaConsumer consumeMessage(String topicName) {
        String bootstrapServerUrl = "localhost:9092";
        return new CustomKafkaConsumer(topicName, bootstrapServerUrl, true);
    }
}
