package shop.mtcoding.blogv2._core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.blogv2._core.filter.MyFilter1;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter1> MyFilter1() {
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*"); // '/'로 시작하는 모든 주소에 발동 됨
        bean.setOrder(0); // 낮은 번호부터 실행됨
        return bean;
    }
}
