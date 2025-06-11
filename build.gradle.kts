plugins {
    //这里的kotlin版本是项目默认的，没进行修改，无影响，可自行查阅jdk版本来修改
    kotlin("jvm") version "2.1.20"
}
repositories {
    mavenCentral()
}

dependencies {
    //我们只引入核心框架，如果觉得版本太高可以自行查阅进行修改，此项目基于spring6x jdk17配置
    //Spring-WebMVC
    //implementation("org.springframework:spring-webmvc:6.1.20")
    //也可以选择分开其中的依赖信息进行引入，而不是直接用:连起来，规则实际上和Maven是一样的
    implementation("org.springframework", "spring-webmvc", "6.1.7")
    //整个DataSource的连接实现
    implementation("org.springframework:spring-jdbc:6.1.7")

    // MyBatis
    implementation("org.mybatis:mybatis-spring:3.0.3")
    implementation("org.mybatis:mybatis:3.5.13")
    // Json库，Jackson有Kotlin的扩展我们直接使用即可，不会出现反射找不到默认空构造函数问题
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.0")
    //数据库驱动，使用runtimeOnly表明不参与编译，只在运行时使用，避免依赖污染和增大包体积
    runtimeOnly("com.mysql:mysql-connector-j:8.4.0")
    // 内置Jetty
    implementation("org.eclipse.jetty:jetty-server:11.0.15")
    implementation("org.eclipse.jetty:jetty-servlet:11.0.15")
    //加个SLF4J的实现，避免jetty警告，也为了看日志
    implementation("ch.qos.logback:logback-classic:1.4.14")

    //测试依赖引入，对应maven的<scope>test</scope>，避免打包到项目中
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.springframework:spring-test:6.1.7")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
tasks.jar {
    manifest {
        // 设置启动的主类，Kotlin类编译后都是xxxKt的形式
        attributes(mapOf("Main-Class" to "com.ssm.MainKt"))
    }

    // 将所有运行时依赖（JAR 文件）的内容合并到最终的 JAR 包中。
    from(configurations.runtimeClasspath.get().map {
        // 如果是目录就直接包含，如果是 JAR 文件就解压其内容再包含。
        if (it.isDirectory) it else zipTree(it)
    })

    // 处理重复文件：当多个依赖库包含同名文件时（如 META-INF/LICENSE），
    // 告诉 Gradle 排除重复项，只保留第一次遇到的文件，以避免构建失败。
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // 将项目编译后的代码（.class 文件）添加到最终的 JAR 包中。
    from(sourceSets.main.get().output)
}