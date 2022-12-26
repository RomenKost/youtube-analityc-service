package com.kostenko.youtube.analytic.config;

import com.kostenko.youtube.analytic.repository.security.UsersRepository;
import com.kostenko.youtube.analytic.repository.youtube.ChannelRepository;
import com.kostenko.youtube.analytic.repository.youtube.ChannelsInfoRepository;
import com.kostenko.youtube.analytic.repository.youtube.VideosInfoRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import static org.mockito.Mockito.*;

@MockBean({
        UsersRepository.class,
        ChannelRepository.class,
        ChannelsInfoRepository.class,
        VideosInfoRepository.class
})
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@TestConfiguration
public class MockedDataSourceConfiguration {
    @Bean
    public TransactionManager transactionManager() {
        return mock(PlatformTransactionManager.class);
    }
}
