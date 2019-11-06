package tcc.PluriGame.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class WebConfig {
	
	public WebMvcConfigurer CorsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedMethods("").allowedOrigins("*");
			}
		};
	}

}