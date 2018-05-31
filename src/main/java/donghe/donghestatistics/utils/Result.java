package donghe.donghestatistics.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rico on 2016/3/3.
 */
public class Result {

    public static final int PAGE_SUCCESS = 1;
    public static final int PAGE_FAILURE = 0;
    public static final int GUEST_NOT_LOGIN = -1;
    public static final int WECHAT_NOT_LOGIN = -2;
    public static final int ADMIN_NOT_LOGIN = -3;

    private int status;

    @JSONField(serialzeFeatures = SerializerFeature.WriteNullStringAsEmpty)
    private String msg;

    @JSONField(serialzeFeatures = SerializerFeature.WriteNullListAsEmpty)
    private Object data;

    @JSONField(serialzeFeatures = SerializerFeature.WriteNullListAsEmpty)
    private Map<String, Object> extra = new HashMap<>();

    public Result(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        return new Result(PAGE_SUCCESS, "请求成功", null);
    }

    public static Result success(Object data){
        return new Result(PAGE_SUCCESS, "请求成功", data);
    }

    public static Result failed(String error) {
        return new Result(PAGE_FAILURE, error, null);
    }

    public static Result failed(int status, String errorMsg) {
        return new Result(status, errorMsg, null);
    }

    public static Result failed(int status, String errorMsg, Object data) {
        return new Result(status, errorMsg, data);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    public Result extra(String key, Object value) {
        extra.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("data", data);
        jsonObject.put("extra", extra);
        jsonObject.put("msg", msg);
        return jsonObject.toJSONString();
    }
}
