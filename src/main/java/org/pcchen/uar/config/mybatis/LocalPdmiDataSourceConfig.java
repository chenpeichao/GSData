package org.pcchen.uar.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 第一个数据源
 * Created by ceek on 2018-08-08 21:56.
 */
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = LocalPdmiDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "localPdmiSqlSessionFactory")
public class LocalPdmiDataSourceConfig {
    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "org.pcchen.uar.mapper.local_pdmi";
    static final String MAPPER_LOCATION = "classpath:mapper/local_pdmi/*.xml";

    @Value("${local_pdmi.datasource.url}")
    private String url;

    @Value("${local_pdmi.datasource.username}")
    private String user;

    @Value("${local_pdmi.datasource.password}")
    private String password;

    @Value("${local_pdmi.datasource.driverClassName}")
    private String driverClass;

    @Bean(name = "localPdmiDataSource")
    @Primary
    public DataSource localPdmiDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "localPdmiTransactionManager")
    @Primary
    public DataSourceTransactionManager localPdmiTransactionManager() {
        return new DataSourceTransactionManager(localPdmiDataSource());
    }

    @Bean(name = "localPdmiSqlSessionFactory")
    @Primary
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("localPdmiDataSource") DataSource localPdmiDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(localPdmiDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(LocalPdmiDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
