package org.smr.ministore.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smr.ministore.entities.ResponseVo;


import java.util.HashMap;
import java.util.Map;

public class BodyUtils {


    private static Logger log = LoggerFactory.getLogger(BodyUtils.class);

    private final static int OK = 0;


    public final static String transforBody(String body) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(OK);
        if (StringUtils.isEmpty(body)) {
            responseVo.setData(body);
            return JSON.toJSONString(responseVo);
        }
        if (!(body.startsWith("{") && body.endsWith("}")) && !(body.startsWith("[") && body.endsWith("]"))) {
            responseVo.setData(body);
            return JSON.toJSONString(responseVo);
        }
        Object jsonObject = JSON.parse(body);
        if (jsonObject instanceof JSONObject) {
            Object code = ((JSONObject) jsonObject).get("code");
            if (code != null) {
                if (((JSONObject) jsonObject).get("data") != null) {
                    if (((JSONObject) jsonObject).get("data") instanceof JSONObject) {
                        JSONObject data = (JSONObject) ((JSONObject) jsonObject).get("data");
                        Object total = data.get("total");
                        Object result = data.get("result");
                        if (total != null && result != null) {
                            Map response = new HashMap<>();
                            for (Map.Entry<String, Object> entry : data.entrySet()) {
                                if (entry.getKey().equals("countColumn")
                                        || entry.getKey().equals("startRow")
                                        || entry.getKey().equals("reasonable")
                                        || entry.getKey().equals("count")
                                        || entry.getKey().equals("endRow")
                                        || entry.getKey().equals("orderBy")
                                        || entry.getKey().equals("pageSize")
                                        || entry.getKey().equals("pageNum")
                                        || entry.getKey().equals("empty")
                                        || entry.getKey().equals("pages")
                                        || entry.getKey().equals("orderByOnly")
                                        || entry.getKey().equals("pageSizeZero")
                                        ) {
                                    continue;
                                } else {
                                    response.put(entry.getKey(), entry.getValue());
                                }
                            }
                            responseVo.setData(response);
                            return JSON.toJSONString(responseVo);
                        } else {
                            return body;
                        }
                    } else {
                        return body;
                    }
                } else {
                    return body;
                }
            } else {
                responseVo.setData(jsonObject);
                return JSON.toJSONString(responseVo);
            }
        } else {
            responseVo.setData(jsonObject);
            return JSON.toJSONString(responseVo);
        }
    }

}