package com.suyh5101;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TempDemoController {
    @GetMapping("/bean")
    public String registerBean() {
        //将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringContextUtil.getApplicationContext();

        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserComponent.class);

        // 属性注入
        // 设置属性userService,此属性引用已经定义的bean:userService,这里userService已经被spring容器管理了.
        beanDefinitionBuilder.addPropertyReference("userService", "userService");

        // 注册bean
        defaultListableBeanFactory.registerBeanDefinition("userComponent", beanDefinitionBuilder.getRawBeanDefinition());


        UserComponent userComponent = (UserComponent) SpringContextUtil.getBean("userComponent");

        return userComponent.toAction("动态注册生成调用");

        //删除bean.
        //defaultListableBeanFactory.removeBeanDefinition("testService");

    }
}
