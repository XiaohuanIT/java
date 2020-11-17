import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @Author: xiaohuan
 * @Date: 2020/8/21 20:19
 */
public class ConsumerFastStart {
	public static final String brokerList = "localhost:9092";
	public static final String topic = "topic-demo";
	public static final String groupId = "group.demo";

	public static void main(String[] args){
		Properties properties = new Properties();
		properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.put("bootstrap.servers", brokerList);
		properties.put("group.id",groupId);
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		consumer.subscribe(Collections.singletonList(topic));
		while (true){
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
			for(ConsumerRecord<String, String> record : records){
				System.out.println(record.value());
			}
		}


	}
}
