package com.shishuheng.framework.authentication.helper.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author shishuheng
 * @date 2020/3/6 4:16 下午
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "global")
public class GlobalProperties {
    private Boolean lowMemoryMode;

    private String signingKey;
}
