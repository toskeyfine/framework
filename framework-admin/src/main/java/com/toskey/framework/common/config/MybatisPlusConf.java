package com.toskey.framework.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.toskey.framework.common.interceptor.DataScopeInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.toskey.framework.modules.*.dao"})
public class MybatisPlusConf {

    @Autowired
    DruidConf druidConf;

    /**
     * 数据源
     */
    private DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        druidConf.config(dataSource);
        return dataSource;
    }

    /**
     * 数据源连接池配置
     */
    @Bean
    public DruidDataSource singleDatasource() {
        return dataSource();
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 数据范围mybatis插件
     */
    @Bean
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }

}
