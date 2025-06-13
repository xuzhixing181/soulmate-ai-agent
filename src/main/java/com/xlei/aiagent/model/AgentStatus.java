package com.xlei.aiagent.model;

/**
 * @author https://github.com/xuzhixing181
 * Agent代理执行的状态枚举类
 */
public enum AgentStatus {

    /**
     * 空闲状态
     */
    IDLE,

    /**
     * 运行中状态
     */
    RUNNING,

    /**
     * 已完成
     */
    FINISHED,

    /**
     * 执行错误
     */
    ERROR
}