package com.xlei.aiagent.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

/**
 * @author xulei https://github.com/xuzhixing181
 * CallAroundAdvisor: 非流式调用(返回单个响应),一次性处理完整响应,主要应用于日志记录、请求验证、响应转换等场景
 * StreamAroundAdvisor: 流式调用(返回响应流),实时处理部分响应,主要应用于内容过滤、进度反馈等场景
 */
@Slf4j
public class MyLoggerAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        advisedRequest = before(advisedRequest);

        // 链式调用下一个Advisor
        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);

        observeAfter(advisedResponse);
        return advisedResponse;
    }


    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        advisedRequest = before(advisedRequest);

        // 链式调用下一个Advisor
        Flux<AdvisedResponse> advisedResponse = chain.nextAroundStream(advisedRequest);

        return new MessageAggregator().aggregateAdvisedResponse(advisedResponse,this::observeAfter);
    }


    private AdvisedRequest before(AdvisedRequest advisedRequest) {
//        log.info("AI advisor request: {}", advisedRequest.userText());
        return advisedRequest;
    }

    private void observeAfter(AdvisedResponse advisedResponse) {
//        log.info("AI advisor response: {}", advisedResponse.response().getResult().getOutput().getText());
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 定义当前拦截器的优先级
     *   order值越小,则优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}