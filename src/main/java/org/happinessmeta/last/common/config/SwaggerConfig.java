package org.happinessmeta.last.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Swagger 사용 시
 * /api 를 Prefix 로 주셔야 Swagger 에 반영되도록 설정되어 있습니다
 * Controller 설계 시 주의하여 주세요
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Final Project API 명세서",
                description = "(서비스 이름 미정)에 사용되는 API 명세서",
                version = "v1",
                contact= @Contact(
                        name = "junsu",
                        email = "pksjmh5295@gmail.com"
                )
        ),
        servers = {
                @Server(
                        description = "DEPLOY",
                        url = "http://localhost:8080"
                ),
//                @Server(
//                        description = "DEV",
//                        url = "http://localhost:8081"
//                )
        }
)
@Configuration
public class SwaggerConfig {

}