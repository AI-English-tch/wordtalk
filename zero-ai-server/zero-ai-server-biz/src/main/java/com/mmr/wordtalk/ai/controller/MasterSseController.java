//package com.mmr.wordtalk.ai.controller;
//
//import com.mmr.wordtalk.ai.constant.SystemEnum;
//import com.mmr.wordtalk.ai.sse.SseEmitterUtil;
//import com.mmr.wordtalk.common.core.util.R;
//import com.mmr.wordtalk.common.security.annotation.Inner;
//import com.mmr.wordtalk.common.security.util.SecurityUtils;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
///**
// * master对话管理
// *
// * @author 张恩睿
// * @date 2023-06-14 11:36:00
// */
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/master")
//@Tag(description = "master-sse", name = "master中的sse连接管理")
//@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
//public class MasterSseController {
//
//    /**
//     * 打开EventSource连接
//     */
//    @Operation(summary = "打开EventSource连接", description = "打开EventSource连接")
//    @GetMapping(value = "/open", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
//    public SseEmitter open() {
//        String username = SecurityUtils.getUser().getUsername();
//        return SseEmitterUtil.openEmitter(SystemEnum.MASTER.getSystem(), username);
//    }
//
//    /**
//     * 关闭EventSource连接
//     *
//     * @return
//     */
//    @Operation(summary = "关闭EventSource连接", description = "关闭EventSource连接")
//    @GetMapping("/close")
//    public R close() {
//        String username = SecurityUtils.getUser().getUsername();
//        SseEmitterUtil.closeEmitter(SystemEnum.MASTER.getSystem(), username);
//        return R.ok();
//    }
//
//    /**
//     * 推送文字到EventSource
//     *
//     * @param msg
//     * @return
//     */
//    @SneakyThrows
//    @Operation(summary = "推送文字到EventSource", description = "推送文字到EventSource")
//    @GetMapping("/push")
//    @Inner
//    public R push(@RequestParam String msg, @RequestParam String token) {
//        SseEmitter emitter = SseEmitterUtil.openEmitter(SystemEnum.MASTER.getSystem(), token);
//        emitter.send(msg);
//        return R.ok("发送成功");
//    }
//
//}
