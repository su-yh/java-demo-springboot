package com.suyh5601.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class BaseResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        // 不处理直接返回ResponseEntity 的，这种可能处理的就是文件。
        return !ResponseEntity.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response) {

        // 遇到feign接口之类的请求, 不应该再次包装,应该直接返回
        // 上述问题的解决方案: 可以在feign拦截器中,给feign请求头中添加一个标识字段, 表示是feign请求
        // 在此处拦截到feign标识字段, 则直接放行 返回body.

        // System.out.println("响应拦截成功");

        if (body == null) {
            return BaseResponse.ok();
        }

        if (body instanceof BaseResponse) {
            return body;
        }

        // 这里需要对String 类型的做特别处理，因为它由StringHttpMessageConverter 类处理，
        // 如果返回的是BaseResponse 会发生类型转换异常。
        if (String.class.isAssignableFrom(body.getClass())) {
            // TODO: suyh - 需要使用json 序列化一下。将序列化的结果返回出去。
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(BaseResponse.ok(body));
            } catch (JsonProcessingException e) {
                // TODO: suyh - 这里能抛出异常吗？？？
                throw new RuntimeException(e);
            }
        }

        return BaseResponse.ok(body);
    }
}
