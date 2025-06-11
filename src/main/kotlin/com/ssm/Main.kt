package com.ssm

import com.ssm.config.AppConfig
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

fun main(): Unit = with(Server(1028)) {//创建服务监听1028端口
    // 1. 创建并配置 Spring 应用上下文
    val springContext = AnnotationConfigWebApplicationContext().apply {
        register(AppConfig::class.java) // 注册 AppConfig 作为 Spring 配置来源
    }
    // 2. 创建并配置 Servlet 上下文处理器
    handler = ServletContextHandler().apply {
        contextPath = "/" // 设置 Web 应用的上下文路径为根路径 "/"
        // 添加 ContextLoaderListener，初始化 Spring 容器
        addEventListener(ContextLoaderListener(springContext))
        // 添加 Spring MVC 的核心 DispatcherServlet，并映射到所有 URL ("/*")
        addServlet(ServletHolder(DispatcherServlet(springContext)), "/*")
    }
    // 3. 启动 Jetty 服务器并阻塞主线程
    start() // 启动 Jetty 服务器
    join()  // 阻塞当前线程，直到服务器停止
}