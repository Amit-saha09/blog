package com.example.blog.configuration;

import com.example.blog.helper.messages.CommonMessageConstants;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * This class is to provide swagger config
 *
 * @author Amit Saha
 * @since 25th september
 */
@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPIConfiguration(){
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI().components(new Components())
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .info(new Info().title(CommonMessageConstants.APPLICATION_TITLE_FOR_SWAGGER).description(CommonMessageConstants.APPLICATION_DESCRIPTION_FOR_SWAGGER));
    }
}


