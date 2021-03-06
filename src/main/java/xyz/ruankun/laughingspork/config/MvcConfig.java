package xyz.ruankun.laughingspork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.ruankun.laughingspork.util.SystemUtil;

@Configuration
public class MvcConfig {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
//                word下载文件映射
                registry.addResourceHandler("/**").addResourceLocations("file:" + (SystemUtil.isWindows()?(System.getProperty("user.dir") + "\\static\\"):(System.getProperty("user.dir") + "/static/")));
//                swagger映射
                registry.addResourceHandler("swagger-ui.html")
                        .addResourceLocations("classpath:/META-INF/resources/");
            }
        };
    }
}