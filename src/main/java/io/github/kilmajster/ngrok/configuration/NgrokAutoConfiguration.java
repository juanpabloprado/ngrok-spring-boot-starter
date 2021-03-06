package io.github.kilmajster.ngrok.configuration;

import io.github.kilmajster.ngrok.control.NgrokApiClient;
import io.github.kilmajster.ngrok.control.NgrokDownloader;
import io.github.kilmajster.ngrok.control.NgrokRunner;
import io.github.kilmajster.ngrok.control.NgrokSystemCommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

@ComponentScan(basePackages = "io.github.kilmajster.ngrok")
@Configuration
public class NgrokAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(NgrokAutoConfiguration.class);

    @Bean
    @ConditionalOnProperty(name = "ngrok.enabled", havingValue = "true")
    public NgrokRunner ngrokRunner(
            @Value("${server.port:8080}") String port,
            @Value("${ngrok.directory:}") String ngrokDirectory,
            @Autowired NgrokApiClient ngrokApiClient,
            @Autowired NgrokDownloader ngrokDownloader,
            @Autowired NgrokSystemCommandExecutor systemCommandExecutor,
            @Autowired @Qualifier("ngrokExecutor") TaskExecutor ngrokExecutor) {
        log.info("Ngrok is enabled.");

        return new NgrokRunner(port, ngrokDirectory, ngrokApiClient, ngrokDownloader, systemCommandExecutor, ngrokExecutor);
    }
}