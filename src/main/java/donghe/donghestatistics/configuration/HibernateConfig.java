package donghe.donghestatistics.configuration;


import donghe.donghestatistics.domain.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;



@Configuration
public class HibernateConfig {

    private Class<?>[] annotatedClasses = {
            Tea.class,
            TeaPrice.class,
            TeaPriceMonth.class,
            TeaInterested.class,
            ParamByMonth.class,
            TeaInterestedPriceMonthCut.class,
            PriceMonthAvg.class,
    };

    @Autowired
    private Environment env;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
        dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
        return dataSource;
    }

    @Bean(name = "sessionFactory")
    public SessionFactory sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);

        localSessionFactoryBean.setAnnotatedClasses(annotatedClasses);

        java.util.Properties properties = new java.util.Properties();
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.current_session_context_class", env.getProperty("hibernate.current_session_context_class"));
        properties.put("hibernate.id.new_generator_mappings", env.getProperty("hibernate.id.new_generator_mappings"));
        localSessionFactoryBean.setHibernateProperties(properties);
        try {
            localSessionFactoryBean.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    //hibernate默认会在dao层立即关闭该session，改成请求结束再关闭
    @Bean
    public OpenSessionInViewFilter openSessionInViewFilter() {
        OpenSessionInViewFilter openSessionInViewFilter = new OpenSessionInViewFilter();
        openSessionInViewFilter.setSessionFactoryBeanName("sessionFactory");
        return openSessionInViewFilter;
    }
}
