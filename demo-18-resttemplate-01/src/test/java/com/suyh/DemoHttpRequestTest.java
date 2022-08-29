package com.suyh;

import com.suyh.entity.DataBean;
import com.suyh.entity.Notice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationRestTemplate01.class},
        // 使用此参数的主要目的是要让 TestRestTemplate 正常且有效
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Slf4j
public class DemoHttpRequestTest {
    @Resource
    private TestRestTemplate restTemplate;

    @Value("http://localhost:${server.port}")
    private String preUrl;

    /**
     * 不带参数的get 请求
     */
    @Test
    public void testGetForObject01() {
        String uri = "/impl/get/info";
        String url = preUrl + uri;
        Notice resEntity = restTemplate.getForObject(url, Notice.class);

        log.info("response entity: {}", resEntity);
    }

    /**
     * 带参数(@RequestParam)的get 请求
     */
    @Test
    public void testGetForObject02() {
        String uri = "/impl/get/info/param";
        String url = preUrl + uri + "?id=1234&param=param-value";
        Notice resEntity = restTemplate.getForObject(url, Notice.class);

        log.info("response entity: {}", resEntity);
    }

    /**
     * 使用ForEntity 的方式调用，可以得到更多信息。
     * 一般我们可以处理请求返回状态值，用于校验。
     */
    @Test
    public void testGetForEntity01() {
        String uri = "/impl/get/info";
//        uri = "/impl/get/info/notfount";
        String url = preUrl + uri;
        // 的确可以得到404，但是它直接抛了异常，那这样似乎没什么用了。只能捕获异常再做处理。
        ResponseEntity<Notice> resp = restTemplate.getForEntity(url, Notice.class);
        String jsonResp = ToStringBuilder.reflectionToString(resp, ToStringStyle.JSON_STYLE);
        log.info("jsonResp: {}", jsonResp);
        if (resp.getStatusCode() == HttpStatus.OK) {
            log.info("request OK");
        } else if (resp.getStatusCode() == HttpStatus.NOT_FOUND) {
            log.info("not found");
        }
    }

    @Test
    public void tttt() {
        String url = "http://test.com/solarSystem/planets/{planet}/moons/{moon}";

        // 这里应该是路径参数
        // URI (URL) parameters
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("planet", "Mars");
        urlParams.put("moon", "Phobos");

        // 这里应该就是查询参数
        // Query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                // Add query parameter
                .queryParam("firstName", "Mark")
                .queryParam("lastName", "Watney");

            // 如果没有路径参数就直接构建就可以了。
        URI uri1 = builder.build().toUri();

        /*
         * Console output:
         * http://test.com/solarSystem/planets/Mars/moons/Phobos?firstName=Mark&lastName=Watney
         */
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Notice> reqEntity = new HttpEntity<>(new Notice(), headers);

        URI uri = builder.buildAndExpand(urlParams).toUri();
        // 返回值类型可以指定一个class 
        final ResponseEntity<String> responseEntity = restTemplate.exchange(
                uri, HttpMethod.PUT, reqEntity, String.class);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            return;
        }

        final String responseEntityBody = responseEntity.getBody();
    }
        
    /**
     * 随便生成一个请求参数
     *
     * @return entity
     */
    private static DataBean makeReqEntity() {
        Random random = new Random();
        DataBean resp = new DataBean();
        resp.setNoticeId(random.nextInt(900));
        resp.setNoticeTitle("title");
        resp.setNoticeContent(UUID.randomUUID().toString());
        resp.setNoticeCreateTime(new Date());

        return resp;
    }
}
