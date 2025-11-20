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
    basePackages = "payment.repository",
    entityManagerFactoryRef = "paymentEmf",
    transactionManagerRef = "paymentTx"
)
public class PaymentDbConfig {

    @Bean
    DataSource paymentDataSource() {
        return DataSourceBuilder.create()
        .url("jdbc:h2:mem:paymentdb")
        .driverClassName("org.h2.Driver")
        .username("sa")
        .build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean paymentEmf() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(paymentDataSource());
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setPackagesToScan("payment.domain");
        emf.setPersistenceUnitName("paymentPU");
        return emf;
    }

    @Bean
    PlatformTransactionManager paymentTx(@Qualifier("paymentEmf") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}