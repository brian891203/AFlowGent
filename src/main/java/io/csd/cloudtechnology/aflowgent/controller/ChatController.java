// package io.csd.cloudtechnology.aflowgent.controller;

// import org.springframework.ai.chat.ChatClient;
// import org.springframework.ai.chat.Generation;
// import org.springframework.ai.chat.prompt.Prompt;
// import org.springframework.ai.chat.prompt.PromptTemplate;
// import org.springframework.ai.parser.BeanOutputParser;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.http.ResponseEntity;
// import org.springframework.http.HttpStatus;

// @RestController
// @RequestMapping("/songs")
// public class ChatController {
// 	// Inject ChatClient
// 	private final ChatClient chatClient;

// 	public SongsController(ChatClient chatClient) {
// 		this.chatClient = chatClient;
// 	}

// 	// String Prompt
// 	@GetMapping("/stringprompt/topSong")
// 	public String topSong() {
// 		String stringPrompt =
// 			"What was the Billboard number one year-end top 100 single for 2015?";
// 		System.out.println(stringPrompt);
// 		return chatClient.call(stringPrompt);
// 	}

// 	// Prompt with parameter
// 	@GetMapping("/parameter/topsong/{year}")
// 	public String parameterTopSong(@PathVariable("year") int year) {
// 		String stringPrompt =
// 			"What was the Billboard number one year-end top 100 single for {year}?";
// 		PromptTemplate template = new PromptTemplate(stringPrompt);
// 		template.add("year", year);
// 		return chatClient.call(template.render());
// 	}

// 	// Return object
// 	@GetMapping("/objectreturn/topsong/{year}")
// 	public TopSong objectReturnTopSong(@PathVariable("year") int year) {
// 		BeanOutputParser<TopSong> parser = new BeanOutputParser<>(TopSong.class);
// 		String stringPrompt =
// 			"""
// 			What was the Billboard number one year-end top 100 single for {year}?
// 			{format}
// 			""";
// 		PromptTemplate template = new PromptTemplate(stringPrompt);
// 		template.add("year", year);
// 		template.add("format", parser.getFormat());
// 		//System.out.println("PARSER FORMAT: " + parser.getFormat());

// 		Prompt prompt = template.create();
// 		System.out.println(prompt);

// 		Generation generation = chatClient.call(prompt).getResult();
// 		TopSong topSong = parser.parse(generation.getOutput().getContent());
// 		return topSong;
// 	}

// 	// Prompt with parameter
// 	@PostMapping("/greeting")
// 	public String greeting(@RequestBody greetings greeting) {
// 		System.out.println(greeting.getWord());
// 		String stringPrompt = greeting.getWord();
// 		// PromptTemplate template = new PromptTemplate(stringPrompt);
// 		// template.add("year", year);
// 		return chatClient.call(stringPrompt);
// 	}
	
// 	@PostMapping("/classifyQuestion")
// 	public String classifyQuestion(@RequestBody Query query) {
// 		// Use LLM to analyze the question
// 		String stringPrompt = query.getQuery();
		
// 		return "";
// 	}
// }