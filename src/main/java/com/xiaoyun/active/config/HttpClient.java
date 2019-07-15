package com.xiaoyun.active.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * @author al
 * @date 2019/7/15 13:33
 * @description
 */
public class HttpClient {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    public String getDataByCityName(String cityName) {
        String uri="http://wthrcdn.etouch.cn/weather_mini?city="+cityName;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(uri, HttpMethod.GET, entity,String.class).getBody();
    }

    public String getOrcResult(String imagePath, String userAppKey, String isFront){

        String url = "http://apiplatapi.kuduhz2018.lunz.cn/api/v1/idcardocr/GetInfo?userAppKey=ocradminright&isFront=true";
        FileSystemResource resource = new FileSystemResource(new File(imagePath));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("imageFile", resource);
        String res = restTemplate.postForObject(url, param, String.class);
        return res;
    }


    public static void main(String[] args) {

        String res = new HttpClient().getOrcResult("E:\\MyProject\\design_pattern\\src\\main\\resources\\image\\test_02.jpg","","");
        System.out.println(res);
    }


}
