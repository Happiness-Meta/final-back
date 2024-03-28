package org.happinessmeta.last.portfolio.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomJsonComponent {
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init(){
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    public String objectToJson(Object obj){
        String jsonStr = "";
        try {
            jsonStr = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new RuntimeException("Parsing Exception: ", e);
        }
        return jsonStr;
    }

    public <T> T jsonToObject(String json, Class<T> tClass){
        T obj = null;
        try {
            obj = objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }


}
