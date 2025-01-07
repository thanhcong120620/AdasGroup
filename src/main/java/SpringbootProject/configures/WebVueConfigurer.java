package SpringbootProject.configures;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebVueConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Cấu hình CORS cho tất cả các endpoint
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Cho phép truy cập từ frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Các phương thức HTTP
                .allowedHeaders("*") // Cho phép tất cả headers
                .allowCredentials(true); // Cho phép gửi cookie nếu cần
    }
}

