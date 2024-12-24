package com.cisdi.cisdipreview.config;

import com.cisdi.cisdipreview.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/share/data") // 拦截请求
                .excludePathPatterns(
                        "/login",
                        "/register",
                        "/public/**" // 排除不需要拦截的路径，根据实际情况调整
                );
    }

    // 如果需要添加静态资源的处理，可以覆盖其他方法
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 示例：映射静态资源路径
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
