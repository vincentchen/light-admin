package org.lightadmin.core.test;

import org.lightadmin.core.config.context.LightAdminDomainConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@Import({
        LightAdminTestConfiguration.PersistanceConfiguration.class, LightAdminDomainConfiguration.class
})
public class LightAdminTestConfiguration {

    @Configuration
    @EnableTransactionManagement
    public static class PersistanceConfiguration {

        private static final String MODEL_BASE_PACKAGE = "org.lightadmin.core.test.model";

        @Bean
        public PlatformTransactionManager transactionManager() {
            return new JpaTransactionManager(entityManagerFactory());
        }

        @Bean
        public DataSource dataSource() {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder.setType(EmbeddedDatabaseType.HSQL).addScript("classpath:db/schema.sql").addScript("classpath:db/data.sql").build();
        }

        @Bean
        public EntityManagerFactory entityManagerFactory() {
            HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            vendorAdapter.setDatabase(Database.HSQL);
            vendorAdapter.setGenerateDdl(true);

            LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
            factory.setJpaVendorAdapter(vendorAdapter);
            factory.setPackagesToScan(MODEL_BASE_PACKAGE);
            factory.setDataSource(dataSource());

            factory.afterPropertiesSet();

            return factory.getObject();
        }

        @Bean
        public JpaDialect jpaDialect() {
            return new HibernateJpaDialect();
        }
    }
}