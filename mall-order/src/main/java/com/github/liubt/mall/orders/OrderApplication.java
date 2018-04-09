package com.github.liubt.mall.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

@SpringBootApplication
@EnableCaching
public class OrderApplication {

    private static final Logger log = LoggerFactory.getLogger(OrderApplication.class);

    public static void main(final String[] args) throws UnknownHostException {
        final HashMap<String, Object> props = new HashMap<>();
        final ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .properties(props)
                .sources(OrderApplication.class)
                .run(args);

        final Environment env = context.getEnvironment();
        OrderApplication.log.info(
                "Application '{}' is running as http://{}:{}!",
                env.getProperty("spring.application.name"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
    }
}
