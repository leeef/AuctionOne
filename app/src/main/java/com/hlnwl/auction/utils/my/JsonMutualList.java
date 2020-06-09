package com.hlnwl.auction.utils.my;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**

 * . 类描述： json和list之间的相互转换
 */

public class JsonMutualList {
    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }


    /**
     * 将对象转换成json格式
     *
     * @param ts
     * @return
     */
    public static String objectToJson(Object ts) {
        String jsonStr = null;
        if (gson != null) {
            jsonStr = gson.toJson(ts);
        }
        return jsonStr;
    }

    /**
     * 将json格式转换成list对象，并准确指定类型
     * <p>
     * new TypeToken<ArrayList<String>>() {}.getType()  TODO 这个是类型 可以使用其他的  可以是对象 这边只是显示String
     *
     * @param jsonStr
     * @return
     */
    public static ArrayList<String> jsonToList(String jsonStr) {
        ArrayList<String> objList = null;
        if (gson != null && (jsonStr != null && jsonStr.length() > 0)) {
            objList = gson.fromJson(jsonStr, new TypeToken<ArrayList<String>>() {
            }.getType());
        }
        return objList;
    }
}
