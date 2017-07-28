package com.eniro.test.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "api")
public class EniroApiConfiguration {
    private String searchUrl;
    private String profile;
    private String key;
    private String country;
    private String version;
}
