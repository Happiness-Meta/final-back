package org.happinessmeta.last.common.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IamportConfig {
    @Bean
    public IamportClient iamportClient(
            @Value("${iamport.apiKey}") String apiKey,
            @Value("${iamport.secretKey}") String secretKey
    ){
        return new IamportClient(apiKey, secretKey);
    }
}
