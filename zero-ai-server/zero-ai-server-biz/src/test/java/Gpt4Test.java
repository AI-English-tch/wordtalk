import cn.hutool.core.util.StrUtil;
import com.mmr.wordtalk.ai.ZeroAiApplication;
import com.mmr.wordtalk.ai.core.ChatGptSendStrategy;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张恩睿
 * @date 2023-07-04 15:54:00
 */
@SpringBootTest(classes = ZeroAiApplication.class)
public class Gpt4Test {

	@Test
	public void test() {
		List<Message> messageList = new ArrayList<>();
		messageList.add(Message.of("鲁迅为什么暴打周树人？"));
		ChatGPT gpt = ChatGPT.builder()
				.apiKey("sk-NLtw33IrEZtkj0g7Fe139cF33c804d0a9d364d285b076e18")
				.apiHost("https://123.oneapi.cc/")
				.build().init();

		boolean equals = StrUtil.equals("gpt-4-0613", ChatCompletion.Model.GPT_4_0613.getName());
		ChatCompletion completion = ChatCompletion.builder()
				.messages(messageList)
				.model(ChatCompletion.Model.GPT_4_0613.getName())
				.stream(false)
				.build();

		ChatCompletionResponse response = gpt.chatCompletion(completion);
		String chat = ChatGptSendStrategy.parse(response.getChoices(), false);
		System.out.println(chat);
	}

}
