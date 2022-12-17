package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.socket.adapter.standard.ConvertingEncoderDecoderSupport;

import javax.websocket.DecodeException;
import javax.websocket.EndpointConfig;

@Slf4j
@RequiredArgsConstructor
public class MessageDecoder extends ConvertingEncoderDecoderSupport.TextDecoder<Message> {
    private static final ObjectMapper mapper = new Jackson2ObjectMapperBuilder().build();

    @Override
    public Message decode(String s) throws DecodeException {
        try {
            return mapper.readValue(s, Message.class);
        } catch (JsonProcessingException e) {
            log.error("Error on decode");
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
        super.init(endpointConfig);
        log.debug("Decoder init");
    }

    @Override
    public void destroy() {
        // Close resources
        super.destroy();
        log.debug("Decoder destroy");
    }
}
