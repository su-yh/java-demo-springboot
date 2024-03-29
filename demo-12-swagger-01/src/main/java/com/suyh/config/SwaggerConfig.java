package com.suyh.config;

import com.google.common.base.Predicates;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 苏雲弘
 * @Description:
 * @date 2020-04-03 17:01
 */
@Configuration
@EnableSwagger2
@Profile({"local"}) // 限制，只有当spring.profiles.active = local 时此配置才有效
public class SwaggerConfig {

    /**
     * 通过分组可以在生成的文档是下拉选择，查看哪一个分组
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("所有文档")
                .select()
                // 指定要扫描的基础包
//                .apis(RequestHandlerSelectors.any())
                //扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 过滤前缀路径(不包含上下文，上下文另外处理)
                .paths(Predicates.or(PathSelectors.ant("/services/suyh/dev/**"), PathSelectors.ant("/services/suyh/local/**")))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/dev-api")    // 将所有从swagger 映射的路径都添加一个 dev-api 而正常访问却是不需要的
                .ignoredParameterTypes(ApiIgnore.class);
    }

    /**
     * 通过分组可以在生成的文档是下拉选择，查看哪一个分组
     */
    @Bean
    public Docket createRestApiCustomer() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("客户、供应商、承运商等明细表控制器分组")
                .select()
                // 指定要扫描的基础包
                .apis(RequestHandlerSelectors.basePackage("com.suyh.controller.normal"))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(ApiIgnore.class);
    }

    /**
     * 通过分组可以在生成的文档是下拉选择，查看哪一个分组
     */
    @Bean
    public Docket createRestApiOther() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("其他分组")
                .select()
                // 指定要扫描的基础包
                .apis(RequestHandlerSelectors.basePackage("com.suyh.controller.other"))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(ApiIgnore.class);
    }

    private ApiInfo apiInfo() {
        // 前端访问地址：http://localhost:8080/swagger-ui.html
        return new ApiInfoBuilder()
                .title("TITLE 显示标题")
                .description("description 描述说明")
                .version("v1.0")
                .build();
    }

}
