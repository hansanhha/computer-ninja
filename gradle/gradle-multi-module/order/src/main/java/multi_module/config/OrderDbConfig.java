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
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
    basePackages = "order.repository",
    entityManagerFactoryRef = "orderEmf",
    transactionManagerRef = "orderTx"
)
public class OrderDbConfig {

    @Bean
    DataSource orderDataSource() {
        return DataSourceBuilder.create()
        .url("jdbc:h2:mem:orderdb")
        .driverClassName("org.h2.Driver")
        .username("sa")
        .build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean orderEmf() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(orderDataSource());
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setPackagesToScan("order.domain");
        emf.setPersistenceUnitName("orderPU");
        return emf;
    }

    @Bean
    PlatformTransactionManager orderTx(@Qualifier("orderEmf") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}