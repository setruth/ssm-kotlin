package com.ssm.config

import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import javax.sql.DataSource

/**
 * 用于定义 Spring 应用上下文中的核心配置。
 *
 * 标记为open，由于spring需要对配置类进行动态代理，但是Kotlin的class和函数默认是final的
 * 包含数据源配置、MyBatis工厂配置，并启用组件扫描以加载 Controller 和 Service.
 * 同时扫描 MyBatis Mapper 接口。
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = ["com.ssm.controller", "com.ssm.service"])
@MapperScan("com.ssm.mapper")
open class AppConfig {

    /**
     * 配置数据源 Bean。
     *
     * 使用 DriverManagerDataSource 实现，适用于开发和测试环境，不推荐用于生产。
     *
     * @return 配置好的 [DataSource] 实例
     */
    @Bean
    open fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver")
        dataSource.url = "jdbc:mysql://localhost:3306/ssm?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
        dataSource.username = "root"
        dataSource.password = "071721"
        return dataSource
    }

    /**
     * 配置 MyBatis SqlSessionFactory。
     *
     * SqlSessionFactory 是 MyBatis 的核心对象，用于创建 SqlSession。
     *
     * @return 配置好的 [SqlSessionFactoryBean] 实例
     */
    @Bean
    open fun sqlSessionFactory(): SqlSessionFactoryBean {
        val sqlSessionFactoryBean = SqlSessionFactoryBean()
        sqlSessionFactoryBean.setDataSource(dataSource())
        sqlSessionFactoryBean.setTypeAliasesPackage("com.ssm.model") // 设置实体类包路径，用于别名映射
        return sqlSessionFactoryBean
    }
}
