package multi_module.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration

// JPA의 경우 @EnableJpaRepositories를 통해 스캔된 리포지토리가 어떤 DB를 사용할지 스프링에게 알린다
@EnableJpaRepositories(
    basePackages = "user.repository",
    entityManagerFactoryRef = "userEmf",
    transactionManagerRef = "userTx"
)
public class UserDbConfig {

    @Bean
    DataSource userDataSource() {
        return DataSourceBuilder.create()
        .url("jdbc:h2:mem:userdb")
        .driverClassName("org.h2.Driver")
        .username("sa")
        .build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean userEmf() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(userDataSource());
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setPackagesToScan("user.domain");
        emf.setPersistenceUnitName("userPU");
        return emf;
    }

    @Bean
    PlatformTransactionManager userTx(@Qualifier("userEmf") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}