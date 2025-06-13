package com.xlei.aiagent.agent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author https://github.com/xuzhixing181
 * 1) 实现ReAct模式(思考-行动-观察),将代理的执行过程分为思考(Think) 和 行动(Act)两个关键步骤
 * 2) 具体如何思考,行动,交给子类去实现
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public abstract class ReActAgent extends BaseAgent{

    /**
     * 处理当前状态并决定下一步行动
     * @return 下一次是否需要执行行动,true:需要执行; false:不需要执行
     */
    public abstract Boolean think();

    /**
     * 执行单次行动
     * @return 行动执行结果
     */
    public abstract String act();


    @Override
    public String step() {
        try {
            // 1.先思考
            Boolean shouldAct = think();
            if (!shouldAct){
                return "已思考完毕,无需执行";
            }
            // 2.再行动
            return act();
        } catch (Exception e) {
            e.printStackTrace();
            return "agent execute error: " + e.getMessage();
        }
    }
}