import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * @author 张恩睿
 * @date 2023/09/14
 */
//@SpringBootTest(classes = ZeroAiApplication.class)
public class WebClientTest {

    @Test
    public void testWebClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.virapi.com/vir_github2af369e34hggh/demo/visualapi")
                .defaultHeader("app-token","$2a$10$wgwJnh3saBKT16Z2yLwoiuFYYxhZgMJowyx93R0xRYN9KrUWGBn3C")
                .build();
        WebClient.ResponseSpec retrieve = webClient.get().retrieve();

        Mono<HashMap> hashMapMono = retrieve.bodyToMono(HashMap.class);
        HashMap map = hashMapMono.block();
        System.out.println(map);
    }

    @Test
    public void testWebClient2() {

    }
}
