package com.yq.api;
import com.yq.util.impl.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import java.util.*;

public class AliApi {
    public String getJson() {
        String host = "http://ncovdata.market.alicloudapi.com";
        String path = "/ncov/cityDiseaseInfoWithTrend";
        String method = "GET";
        String appcode = "caaf6700d2f448a0b6e8959b68548278";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println(EntityUtils.toString(response.getEntity()));
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return "获取疫情api出错了";
        }
    }
}
