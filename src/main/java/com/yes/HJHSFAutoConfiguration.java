package com.yes;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by huangJin on 2023/5/16.
 */


@Configuration
@EnableConfigurationProperties(HJHSFConfigServer.class)
public class HJHSFAutoConfiguration {

}
