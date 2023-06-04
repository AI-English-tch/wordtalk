package com.mmr.wordtalk.bridge.controller;

import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {
	private String id;
	private String input;
	private List<String> chatlog;

}
