package com.itcorey.schedule.domian;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by ：Corey
 * 14:06 2018/11/22
 */

@Configuration   //标记配置类 兼备Component的效果
@EnableScheduling //开启定时任务
public class SimpleScheduleConfig {

    //添加定时任务
   // @Scheduled(cron = "0/5 * * * * ?")
    public  void  configureTask(){
        System.err.println("执行定时任务1: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
