package com.bee.team.fastgo.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author hs
 * @date 2020/7/25 15:44
 * @desc 配置文件
 **/
@Component
public class CommonProperties {

    @Value("frontTemplate")
    public static String a;
}
