import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.mmr.wordtalk.bridge.BridgeApplication;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.ConsoleStreamListener;
import com.plexpt.chatgpt.util.Proxys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.Proxy;
import java.util.Arrays;
import java.util.function.Consumer;

@SpringBootTest(classes = BridgeApplication.class)
public class ChatGptTest {

	@Test
	public void test(){
		System.out.println(DateUtil.now());
	}

	@Test
	public void testGpt(){
		Proxy proxy = Proxys.http("127.0.0.1", 7890);
		ChatGPT chatGPT = ChatGPT.builder()
				.apiKey("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")
				.proxy(proxy)
				.apiHost("https://api.openai.com/") //反向代理地址
				.build()
				.init();

		String res = chatGPT.chat("以《码农》为题，写一首五言叙事诗");
		System.out.println(res);
	}

	@Test
	public void testGptStream(){
		Proxy proxy = Proxys.http("127.0.0.1", 7890);
		ChatGPTStream chatGPTStream = ChatGPTStream.builder()
				.apiKey("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")
				.proxy(proxy)
				.apiHost("https://api.openai.com/") //反向代理地址
				.build()
				.init();

		ChatGPT chatGPT = ChatGPT.builder()
				.apiKey("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")
				.proxy(proxy)
				.apiHost("https://api.openai.com/") //反向代理地址
				.build()
				.init();

		ConsoleStreamListener listener = new ConsoleStreamListener();
		Message prompt = Message.ofSystem("你现在是一个哲学家，问题的答案要向宇宙的规律、存在的意义上靠拢");
		Message question = Message.of("为什么1+1=2 ?");

		ChatCompletion completion = ChatCompletion.builder()
				.messages(Arrays.asList(prompt, question))
				.maxTokens(3000)
				.temperature(0.9)
				.build();

		ChatCompletionResponse response = chatGPT.chatCompletion(Arrays.asList(prompt, question));
		Message message = response.getChoices().get(0).getMessage();
		System.out.println(message);

//		chatGPTStream.streamChatCompletion(completion,listener);
	}


}
