package com.hiLunch.config;
import com.hiLunch.interceptor.JwtTokenAdminInterceptor;
import com.hiLunch.interceptor.JwtTokenUserInterceptor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 設定クラスで、WEB層の関連コンポーネントを登録する
 */
@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
    @Autowired
    JwtTokenUserInterceptor jwtTokenUserInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        log.info("カスタムインターセプタ〜の登録を開始...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/v3/api-docs",
                        "/swagger-resources/**");
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/signup",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/v3/api-docs",
                        "/swagger-resources/**");
    }



}
