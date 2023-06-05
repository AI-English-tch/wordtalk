package com.mmr.wordtalk.bridge.controller;

import com.mmr.wordtalk.bridge.service.PythonService;
import com.mmr.wordtalk.common.core.util.R;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PythonController
 *
 * @author 张恩睿
 * @date 2023-06-05 23:12:52
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/python")
@Tag(description = "python", name = "沟通python后台的接口")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class PythonController {

    private final PythonService pythonService;

    @PostMapping("/setWords")
    public R setWords(@RequestBody List<String> words) {
        return R.ok(pythonService.setWords(words));
    }

    @GetMapping("/sendTalk")
    public R sendTalk(String talk){
        return R.ok(pythonService.sendTalk(talk));
    }

}
