package org.happinessmeta.last.common.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IamportConfig {
    String apiKey = "0731328434414024";
    String secretKey = "re7LPkscD770PF6oKXx4TR2YPBrYLHUoNbuOa83oCNgtQ6ep2pfW6eSZ4wopymMUtXosDqPOWSrMe1Fv";

    @Bean
    public IamportClient iamportClient(){
        return new IamportClient(apiKey, secretKey);
    }
}
