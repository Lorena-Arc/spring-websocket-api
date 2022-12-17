package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.socket.adapter.standard.ConvertingEncoderDecoderSupport;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;

@Slf4j
@RequiredArgsConstructor
public class MessageEncoder extends ConvertingEncoderDecoderSupport.TextEncoder<Message> {
    private static final ObjectMapper mapper = new Jackson2ObjectMapperBuilder().build();


    @Override
    public String encode(Message message) throws EncodeException {
        try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.error("Encode fail");
            return null;
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
        super.init(endpointConfig);
        log.debug("Encoder init");
    }

    @Override
    public void destroy() {
        // Close resources
        super.destroy();
        log.debug("Encoder destroy");
    }
}
