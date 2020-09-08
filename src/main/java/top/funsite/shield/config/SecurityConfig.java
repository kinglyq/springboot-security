package top.funsite.shield.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author Li Yuqing
 * @date 2020/9/6 20:32:03
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 排除的接口
        String[] permits = {
                "/h2/**"
        };
        http.authorizeRequests(auth -> auth.
                antMatchers(permits).permitAll()
                .anyRequest().authenticated())
                .headers(header -> {
                    // 防止打不开iframe页面
                    header.frameOptions().sameOrigin();
                })
                // 禁用csrf
                .csrf(csrf -> csrf.ignoringAntMatchers("/h2/**"));
    }

    /**
     * 跨域配置
     *
     * @return 基于URL的跨域配置信息
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 注册跨域配置
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
