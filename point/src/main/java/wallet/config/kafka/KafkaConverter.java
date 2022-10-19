
package wallet.config.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import wallet.dto.event.AbstractEvent;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.kafka.support.KafkaHeaderMapper;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.SmartMessageConverter;

import wallet.dto.event.PointsDeducted;
import wallet.dto.event.InsufficientPointsOccurred;

@Configuration
public class KafkaConverter {

    @Autowired
    ObjectMapper objectMapper;

    @Bean
    public SmartMessageConverter smartMessageConverter() {
        return new SmartMessageConverter() {
            KafkaHeaderMapper headerMapper = new DefaultKafkaHeaderMapper();

            @Override
            public Object fromMessage(Message<?> message, Class<?> targetClass) {
                return fromMessage(message, targetClass, null);
            }

            @Override
            public Message<?> toMessage(Object payload, MessageHeaders headers) {
                return null;
            }

            @Override
            public Object fromMessage(Message<?> message, Class<?> targetClass, Object conversionHint) {
                MessageHeaders headers = message.getHeaders();
                Object payload = convertPayload(message.getPayload());
                Headers recordHeaders = new RecordHeaders();
                if (this.headerMapper != null) {
                    this.headerMapper.fromHeaders(headers, recordHeaders);
                }
                return payload;
            }

            @Override
            public Message<?> toMessage(Object payload, MessageHeaders headers, Object conversionHint) {
                return null;
            }
        };
    }

    public Object convertPayload(Object payload) {
        try {
            if (!(payload instanceof String)) {
                return payload;
            }
            AbstractEvent abstractEvent = objectMapper.readValue((String) payload, AbstractEvent.class);
            
            if (abstractEvent.getEventType().equals("PointsDeducted")) {
                PointsDeducted pointsDeducted = objectMapper.readValue((String) payload, PointsDeducted.class);
                return pointsDeducted;
            }
            else if (abstractEvent.getEventType().equals("InsufficientPointsOccurred")) {
                InsufficientPointsOccurred insufficientPointsOccurred = objectMapper.readValue((String) payload, InsufficientPointsOccurred.class);
                return insufficientPointsOccurred;
            }
            

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}