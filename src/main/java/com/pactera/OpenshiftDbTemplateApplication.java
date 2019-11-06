package com.pactera;

import com.pactera.amq.AMQPConfiguration;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring/camel-context.xml")
public class OpenshiftDbTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenshiftDbTemplateApplication.class, args);
    }
    @Bean(name = "amqp-component")
    AMQPComponent amqpComponent(AMQPConfiguration config) {
        JmsConnectionFactory qpid = new JmsConnectionFactory(config.getUsername(), config.getPassword(), "amqp://"+ config.getHost() + ":" + config.getPort());

        PooledConnectionFactory factory = new PooledConnectionFactory();
        factory.setConnectionFactory(qpid);
        return new AMQPComponent(factory);
    }
}
