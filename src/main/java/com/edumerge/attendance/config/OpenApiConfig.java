package com.edumerge.attendance.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI smartAttendanceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Smart Attendance Management API")
                        .description("REST API for managing student attendance, corrections, and reporting.")
                        .version("v1.0"));
    }
}
