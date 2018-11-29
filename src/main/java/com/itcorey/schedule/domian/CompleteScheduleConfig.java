package com.itcorey.schedule.domian;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by ：Corey
 * 14:23 2018/11/22
 */

@Configuration
@EnableScheduling
@Slf4j
public class CompleteScheduleConfig  implements SchedulingConfigurer {


    @Mapper
    public interface CronMapper{
        @Select("select cron from cron limit 1")
        String getCron();
    }


    @Autowired
    @SuppressWarnings("all")
    CronMapper cronMapper;


    /**
     * 执行定时任务
     * @param scheduledTaskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {

        scheduledTaskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> System.out.println("执行定时任务2: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.getCron();
                    log.info("表达式为==>"+cron);
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        System.out.println("表达式不存在......");

                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }

}
