import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fintechedge.payflex"})
public class WebfluxApplication {

        public static void main(String[] args) {
            SpringApplication.run(WebfluxApplication.class, args);
        }
}
