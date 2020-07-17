package com.adison.shop;

import com.adison.shop.orders.OrderService;
import com.adison.shop.payments.PaymentService;
import com.adison.shop.products.ProductService;
import com.adison.shop.users.UserService;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@EnableCaching
//when migrating from JPA (Hibernate) to Spring Data, this with base packages to scan for repository interfaces to implement
@EnableJpaRepositories(basePackages = "com.adison.shop")
@PropertySource("classpath:jdbc.properties")
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Configuration
public class ShopConfiguration {

    @Bean
    public ShopService shopService(OrderService orderService,
                                   ProductService productService,
                                   PaymentService paymentService,
                                   UserService userService) {
        return new ShopService(orderService, productService, paymentService, userService);
    }

    //configuration for internationalization--adding a message source component to Spring and configuring it
    //this component will have to be injected wherever you want to use it
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages", "messages_pl_PL");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        return messageSource;
    }

    @Bean
    public DataSource dataSource(Environment env) {
        //DriverManagerDataSource good for dev env, but does not allow db connection pooling (important for efficiency)
        //so we use HikariCP--
        HikariDataSource dataSource = new HikariDataSource();
        //getting properties from external file (env and stuff) through an object provided by Spring that encapsulates them
        dataSource.setUsername(env.getProperty("database.username"));
        dataSource.setPassword(env.getProperty("database.password"));
        dataSource.setJdbcUrl(env.getProperty("database.url"));
        dataSource.setDriverClassName(env.getProperty("database.driver"));
        return dataSource;
    }

    //configuring hibernate with data from jpa.properties file
    @Bean
    public PropertiesFactoryBean jpaProperties() {
        PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
        factoryBean.setLocation(new ClassPathResource("jpa.properties"));
        return factoryBean;
    }

    @Bean //when migrating to SpringData, a bean with method name "entityManagerFactory" is required
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaProperties(jpaProperties);
        factoryBean.setPackagesToScan("com.adison.shop");
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("products");
    }
}
