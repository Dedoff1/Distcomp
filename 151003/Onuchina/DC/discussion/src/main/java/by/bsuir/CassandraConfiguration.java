package by.bsuir;

import com.datastax.oss.driver.api.core.CqlSession;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

@Configuration
public class CassandraConfiguration {
    public @Bean CqlSession session(){
        return CqlSession.builder().withKeyspace("distcomp").build();
    }

}
