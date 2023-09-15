import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author 张恩睿
 * @date 2023/09/14
 */
//@SpringBootTest(classes = ZeroAiApplication.class)
public class WebClientTest {

    class GPTBody {
        private String model;
        private List<Message> messages;
        private Double temperature;

        private Boolean stream;

        public Boolean getStream() {
            return stream;
        }

        public void setStream(Boolean stream) {
            this.stream = stream;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public List<Message> getMessages() {
            return messages;
        }

        public void setMessages(List<Message> messages) {
            this.messages = messages;
        }

        public Double getTemperature() {
            return temperature;
        }

        public void setTemperature(Double temperature) {
            this.temperature = temperature;
        }

        public Message buildMessage(String role, String content) {
            Message message = new Message(role, content);
            return message;
        }

        private class Message {
            private String role;
            private String content;

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Message(String role, String content) {
                this.role = role;
                this.content = content;
            }

        }
    }

    @Test
    public void testWebClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.virapi.com/vir_github2af369e34hggh/demo/visualapi")
                .defaultHeader("app-token", "$2a$10$wgwJnh3saBKT16Z2yLwoiuFYYxhZgMJowyx93R0xRYN9KrUWGBn3C")
                .build();
        WebClient.ResponseSpec retrieve = webClient.get().retrieve();

        Mono<HashMap> hashMapMono = retrieve.bodyToMono(HashMap.class);
        HashMap map = hashMapMono.block();
        System.out.println(map);
    }

    @SneakyThrows
    @Test
    public void testWebClient2() {


        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(
                HttpClient.create()
                        .proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP).host("127.0.0.1").port(7890))
        );
        WebClient client = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .clientConnector(connector)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add("Content-Type", "application/json");
                    httpHeaders.add("Authorization", "Bearer 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");
                })
                .build();
        GPTBody body = new GPTBody();
        body.setModel("gpt-3.5-turbo");
        body.setTemperature(0.7);
        GPTBody.Message message = body.buildMessage("user", "Say this is a test !");
        body.setMessages(Arrays.asList(message));

//        HashMap chat = client.post().bodyValue(JSONUtil.toJsonStr(body))
//                .retrieve()
//                .bodyToMono(HashMap.class)
//                .block();
//
//        System.out.println("对话模式："+JSONUtil.toJsonStr(chat));
//
//        HashMap text = client.post()
//                .bodyValue("This is indeed a test")
//                .retrieve().bodyToMono(HashMap.class)
//                .block();
//
//        System.out.println("文本模式："+ JSONUtil.toJsonStr(text));


        body.setStream(Boolean.TRUE);
        Mono<List<String>> listMono = client.post().bodyValue(JSONUtil.toJsonStr(body))
                .retrieve()
                .bodyToFlux(String.class)
                .collectList();
        List<String> block = listMono.block();
        System.out.println(block);


        Thread.sleep(3000);
    }
}
