package com.yes;

import com.yes.scan.HJHSFScannerRegistrar;
import com.yes.scan.MyHSFScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by huangJin on 2023/5/16.
 */


@Configuration
@EnableConfigurationProperties(HJHSFConfigServer.class)
@Import(HJHSFScannerRegistrar.class)
public class HJHSFAutoConfiguration {
}
