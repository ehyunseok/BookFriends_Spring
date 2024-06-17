package com.daney.bookfriends.config;

import jakarta.servlet.Servlet;
import org.apache.jasper.servlet.JspServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //json과 xml 충돌 방지를 위해 추가
    @Bean
    public MappingJackson2HttpMessageConverter jsonConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public MappingJackson2XmlHttpMessageConverter xmlConverter() {
        return new MappingJackson2XmlHttpMessageConverter();
    }


    // json 형식 파일 업로드을 위해 추가
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .favorParameter(false)
                .ignoreAcceptHeader(true)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/WEB-INF/js/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/WEB-INF/css/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("/WEB-INF/images/");
        registry.addResourceHandler("/commons/**")
                .addResourceLocations("/WEB-INF/commons/");

        // 파일 업로드
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:C:/BookFriends/uploads/");


        // 정적 리소스 처리 설정
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600)
                .resourceChain(true);
    }

    // 뷰 리졸버 설정 추가
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }


    /* HTTP 메소드의 문제는 주로 Spring MVC의 핸들러 매핑이 해당 메소드에 대한 요청을 처리할 수 없을 때 발생한다.
    JSP에서 hidden input 필드를 통해 `_method`를 `DELETE`로 설정하는 방식은 Spring MVC에서 추가 설정이 필요히다.(게시글 삭제 기능에 필요!!)
    Spring MVC는 hidden input 필드를 통해 HTTP 메소드를 반환할 수 있다.
    이를 위해서는 Spring MVC에 `hiddenHttpMethodFilter`를 추가해야 한다. */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

}

