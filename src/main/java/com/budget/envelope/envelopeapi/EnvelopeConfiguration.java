package com.budget.envelope.envelopeapi;

import com.budget.envelope.envelopeapi.core.CoreConfiguration;
import com.budget.envelope.envelopeapi.security.WebSecurityConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import({CoreConfiguration.class, WebSecurityConfiguration.class})
class EnvelopeConfiguration {


}
