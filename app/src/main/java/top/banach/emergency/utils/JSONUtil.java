package top.banach.emergency.utils;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Panguanchao
 * @version 1.0
 * @date 2019年11月16日
 */
public class JSONUtil {
    private static Gson gson = new Gson();

    public static <T> ArrayList<T> getList(JSONObject obj, String jsonArrayKey, Class<T> clazz) {

        ArrayList<T> mtds = new ArrayList<T>();

        try {
            JSONArray array = obj.getJSONArray(jsonArrayKey);

            Gson gson = new Gson();

            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject;
                jsonObject = array.getJSONObject(i);

                T t = gson.fromJson(jsonObject.toString(), clazz);

                mtds.add(t);
            }

//			mtds = gson.fromJson(array.toString(), new TypeToken<T>() {}.getType());


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Error err) {
            err.printStackTrace();
        }
        return mtds;
    }

    public static <T> List<T> getList(JSONArray array, Class<T> clazz) {

        ArrayList<T> mtds = new ArrayList<T>();

        try {
            Gson gson = new Gson();

            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonob;
                jsonob = array.getJSONObject(i);

                T t = gson.fromJson(jsonob.toString(), clazz);

                mtds.add(t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Error err) {
            err.printStackTrace();
        }
        return mtds;
    }

    public static <T> T getModel(JSONObject obj, Class<T> clazz) {
        return new Gson().fromJson(obj.toString(), clazz);
    }

    // *******************com.google.gson.Gson*************** //
    /**
     * 将对象转化为jsonString
     *
     * @param object
     * @return
     */
    public static String toGsonString(Object object) {
        return gson.toJson(object);
    }

    /**
     * 将jsonString转为指定的类型的实例
     *
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T fromGsonString(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }

    public static Object fromGsonString(String jsonString, String clazzName)
            throws Exception {
        return gson.fromJson(jsonString, Class.forName(clazzName).newInstance()
                .getClass());
    }

    /**
     * 转换Json字符串为List
     *
     * @param source
     *            json源字符串
     * @param type
     *            List类型
     * @return List
     *
     *         <p>
     *         示例用法： Type type = new TypeToken<List<AppInfo>>(){}.getType()
     *         </p>
     */
    public static List<? extends Object> convertJsonStringToList(String source,
                                                                 Type type) {
        List<? extends Object> l = null;
        if (source != null) {
            try {
                l = gson.fromJson(source.toString(), type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return l;
    }

    public static JsonElement getJsonElement(String jsonString) {
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(jsonString);
        return jsonElement;
    }

    public static JsonElement getJsonElement(JsonElement jsonElement, String key) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement keyElement = jsonObject.get(key);
        return keyElement;
    }

    public static boolean optBoolean(JsonElement jsonElement, String key)
            throws Exception {
        JsonElement keyElement = getJsonElement(jsonElement, key);
        if (keyElement == null) {
            throw new Exception(
                    "Call method optBoolean(JsonElement jsonElement, String key) failed. The key \""
                            + key + "\" is not exist.");
        }
        return keyElement.getAsBoolean();
    }

    public static boolean optBoolean(JsonElement jsonElement, String key,
                                     boolean fallback) {
        try {
            return optBoolean(jsonElement, key);
        } catch (Exception e) {
            return fallback;
        }
    }

    public static String optString(JsonElement jsonElement, String key)
            throws Exception {
        JsonElement keyElement = getJsonElement(jsonElement, key);
        if (keyElement == null) {
            throw new Exception(
                    "Call method optString(JsonElement jsonElement, String key) failed. The key \""
                            + key + "\" is not exist.");
        }
        return keyElement.getAsString();
    }

    public static String optString(JsonElement jsonElement, String key,
                                   String fallback) {
        try {
            return optString(jsonElement, key);
        } catch (Exception e) {
            return fallback;
        }
    }

    public static int optInt(JsonElement jsonElement, String key)
            throws Exception {
        JsonElement keyElement = getJsonElement(jsonElement, key);
        if (keyElement == null) {
            throw new Exception(
                    "Call method optInt(JsonElement jsonElement, String key) failed. The key \""
                            + key + "\" is not exist.");
        }
        return keyElement.getAsInt();
    }

    public static int optInt(JsonElement jsonElement, String key, int fallback) {
        try {
            return optInt(jsonElement, key);
        } catch (Exception e) {
            return fallback;
        }
    }

    public static long optLong(JsonElement jsonElement, String key)
            throws Exception {
        JsonElement keyElement = getJsonElement(jsonElement, key);
        if (keyElement == null) {
            throw new Exception(
                    "Call method optLong(JsonElement jsonElement, String key) failed. The key \""
                            + key + "\" is not exist.");
        }
        return keyElement.getAsLong();
    }

    public static long optLong(JsonElement jsonElement, String key,
                               long fallback) {
        try {
            return optLong(jsonElement, key);
        } catch (Exception e) {
            return fallback;
        }
    }

    public static double optDouble(JsonElement jsonElement, String key)
            throws Exception {
        JsonElement keyElement = getJsonElement(jsonElement, key);
        if (keyElement == null) {
            throw new Exception(
                    "Call method optDouble(JsonElement jsonElement, String key) failed. The key \""
                            + key + "\" is not exist.");
        }
        return keyElement.getAsDouble();
    }

    public static double optDouble(JsonElement jsonElement, String key,
                                   double fallback) {
        try {
            return optDouble(jsonElement, key);
        } catch (Exception e) {
            return fallback;
        }
    }

    public static boolean isJson(String content) {
        try {
            JSONObject jObj = new JSONObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

