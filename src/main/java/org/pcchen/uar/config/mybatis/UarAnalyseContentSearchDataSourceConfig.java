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
 * 第二个数据源
 * Created by ceek on 2018-08-08 21:56.
 */
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = UarAnalyseContentSearchDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "uarAnalyseContentSearchSqlSessionFactory")
public class UarAnalyseContentSearchDataSourceConfig {
    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "org.pcchen.uar.mapper.uar_analyse_content_search";
    static final String MAPPER_LOCATION = "classpath:mapper/uar_analyse_content_search/*.xml";

    @Value("${uar_analyse_content_search.datasource.url}")
    private String url;

    @Value("${uar_analyse_content_search.datasource.username}")
    private String user;

    @Value("${uar_analyse_content_search.datasource.password}")
    private String password;

    @Value("${uar_analyse_content_search.datasource.driverClassName}")
    private String driverClass;

    @Bean(name = "uarAnalyseContentSearchDataSource")
    public DataSource uarAnalyseContentSearchDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "uarAnalyseContentSearchTransactionManager")
    public DataSourceTransactionManager uarAnalyseContentSearchTransactionManager() {
        return new DataSourceTransactionManager(uarAnalyseContentSearchDataSource());
    }

    @Bean(name = "uarAnalyseContentSearchSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("uarAnalyseContentSearchDataSource") DataSource uarAnalyseContentSearchDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(uarAnalyseContentSearchDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(UarAnalyseContentSearchDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
