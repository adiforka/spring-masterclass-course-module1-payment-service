package com.adison.shop;

import com.adison.shop.orders.OrderService;
import com.adison.shop.payments.PaymentService;
import com.adison.shop.products.ProductService;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//classpath tells Spring to use an appropriate Resource interface impl here to be able to read stuff from a file in the
//classpath. add properties as deep as to the VM itself--
@PropertySource("classpath:jdbc.properties")
//switches AspectJ into the Spring config
@EnableAspectJAutoProxy
//enable transactions to make use of transaction manager
@EnableTransactionManagement
@Configuration
public class ShopConfiguration {

    @Bean
    public ShopService shopService(OrderService orderService,
                                   ProductService productService,
                                   PaymentService paymentService) {
        return new ShopService(orderService, productService, paymentService);
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
    public PropertiesFactoryBean jdbcFactoryBean() {
        PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
        factoryBean.setLocation(new ClassPathResource("jdbc.properties"));
        return factoryBean;
    }

    //configuring dataSource with data from jdbc.properties
    @Bean
    public DataSource dataSource(Environment env) {
        //DriverManagerDataSource good for dev env, but does not allow db connection pooling (important for efficiency)
        //so we use HikariCP
        HikariDataSource dataSource = new HikariDataSource();
        //getting properties from external file (env and stuff) through an object provided by Spring that encapsulates them
        dataSource.setUsername(env.getProperty("database.username"));
        dataSource.setPassword(env.getProperty("database.password"));
        dataSource.setJdbcUrl(env.getProperty("database.url"));
        dataSource.setDriverClassName(env.getProperty("database.driver"));
        return dataSource;
    }

    //configuring hibernate with data from hibernate.properties file
    @Bean
    public PropertiesFactoryBean hibernateProperties() {
        PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
        factoryBean.setLocation(new ClassPathResource("hibernate.properties"));
        return factoryBean;
    }

    @Bean //mind the properties qualifier-by-name
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, Properties hibernateProperties) {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(hibernateProperties);
        factoryBean.setPackagesToScan("com.adison.shop");
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);

    }
}
