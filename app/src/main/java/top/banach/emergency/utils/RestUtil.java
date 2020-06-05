package top.banach.emergency.utils;//package top.banach.emergency.utils;
//
////import java.awt.image.BufferedImage;
//import android.util.Log;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.security.interfaces.RSAPublicKey;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.UUID;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.internal.http.HttpHeaders;
//import okhttp3.internal.http.HttpMethod;
//
////import javax.imageio.ImageIO;
////
//
//
////
////import cn.com.hope.framework.util.FileUtil;
////import cn.com.hope.framework.util.RSAUtil;
////
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//
//import org.jetbrains.annotations.NotNull;
//
//public class RestUtil {
//	private final static String baseUrl = "http://chenyingjun.qicp.vip";
//	private static RSAPublicKey publicKey;//公钥
////	private static RestTemplate restTemplate = new RestTemplate();
////	private static String cid = "123456";//generateUUID()这个缓存到手机里
//	private final static String cid = UUIDGenerator.generateUUID();
//
//	static{
//		try {
//			//生成加密所用的公钥
//			File file = new File("D:/SVNData/Emergency/RSAPublic.dat");
//			FileInputStream keyFIS = new FileInputStream(file);
//	        ObjectInputStream OIS = new ObjectInputStream(keyFIS);
//	        publicKey = (RSAPublicKey) OIS.readObject();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 查询
//	 * @param url
//	 * @return
//	 */
//	 public static JSONObject get(String url) {
//		 try {
//			 return execget(url,  null);
//		 } catch (IOException e) {
//			 e.printStackTrace();
//		 }
//	 }
//
//
//	public static JSONObject execget(String url,Map<String,Object> params) throws IOException {
//		HttpHeaders headers = getHeaders();
//		Map<String, Object> hashMap = new HashMap<>();
//		if(params!=null){
//			for(Entry<String, Object> entry : params.entrySet()){
//				Object value = entry.getValue();
//				if(value == null){
//					continue;
//				}
//				if(value instanceof String){
//					hashMap.put(entry.getKey(), (String)value);
//				}else if(value instanceof String[]){
//					for(String val : (String[])value){
//						if(val!=null){
//							hashMap.put(entry.getKey(), val);
//						}
//					}
//				}else if(value instanceof List){
//					for(String val : (List<String>)value){
//						if(val!=null){
//							hashMap.put(entry.getKey(), val);
//						}
//					}
//				}else{
//					hashMap.put(entry.getKey(), value.toString());
//				}
//			}
//		}
//
//
//
////		HttpEntity requestEntity = new HttpEntity(multiValueMap, headers);
////		String httpUrl = baseUrl+url;
////
////		ResponseEntity<String> responseEntity = restTemplate.exchange(httpUrl,method, requestEntity, String.class);
////		JSONObject object = JSON.parseObject(responseEntity.getBody());
////		if("-1".equals(object.getString("code"))){
////			//-------------------------------
////			//未登录，跳转到登录页面
////		}
//
//
//		String httpUrl = baseUrl+url;
//		run(httpUrl);
//		return object;
//	}
//
//	private static void run(String url) throws IOException {
//		OkHttpClient client = new OkHttpClient();
//		Request request = new Request.Builder()
//				.url(url)
//				.build();
//
//		client.newCall(request).enqueue(new Callback() {
//			@Override
//			public void onFailure(@NotNull Call call, @NotNull IOException e) {
//				e.printStackTrace();
//			}
//
//			@Override
//			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//				if (!response.isSuccessful()) {
//					throw new IOException("Unexpected code " + response);
//				}
//
//				Log.i("HomeFragment", response.body().string());
//			}
//		});
//	}
//	 /**
//	  * 删除
//	  * @param url
//	  * @return
//	  */
//	 public static JSONObject delete(String url) {
//		 return exec(url, HttpMethod.DELETE, null);
//	 }
//	 /**
//	  * 保存或者提交
//	  * @param url
//	  * @param params
//	  * @return
//	  */
//	 public static JSONObject post(String url,Map<String,Object> params) {
//		 return exec(url, HttpMethod.POST, params);
//	 }
//	 /**
//	  * 更新
//	  * @param url
//	  * @param params
//	  * @return
//	  */
//	 public static JSONObject put(String url,Map<String,Object> params) {
//		 return exec(url, HttpMethod.PUT, params);
//	 }
//    public static JSONObject exec(String url,HttpMethod method,Map<String,Object> params) {
//  	  HttpHeaders headers = getHeaders();
//  	  MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap();
//  	  if(params!=null){
//  		for(Entry<String, Object> entry : params.entrySet()){
//    		  Object value = entry.getValue();
//    		  if(value == null){
//    			  continue;
//    		  }
//    		  if(value instanceof String){
//    			  multiValueMap.add(entry.getKey(), (String)value);
//    		  }else if(value instanceof String[]){
//    			  for(String val : (String[])value){
//    				  if(val!=null){
//    					  multiValueMap.add(entry.getKey(), val);
//    				  }
//    			  }
//    		  }else if(value instanceof List){
//    			  for(String val : (List<String>)value){
//    				  if(val!=null){
//    					  multiValueMap.add(entry.getKey(), val);
//    				  }
//    			  }
//    		  }else{
//    			  multiValueMap.add(entry.getKey(), value.toString());
//    		  }
//    	  }
//  	   }
//        HttpEntity requestEntity = new HttpEntity(multiValueMap, headers);
//        String httpUrl = baseUrl+url;
////        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(httpUrl,method, requestEntity, JSONObject.class);
////        JSONObject object = responseEntity.getBody();
////        if("-1".equals(object.getString("code"))){
////        	//-------------------------------
////        	//未登录，跳转到登录页面
////        }
////        return responseEntity.getBody();
//
//		ResponseEntity<String> responseEntity = restTemplate.exchange(httpUrl,method, requestEntity, String.class);
//		JSONObject object = JSON.parseObject(responseEntity.getBody());
//		if("-1".equals(object.getString("code"))){
//			//-------------------------------
//			//未登录，跳转到登录页面
//		}
//
//		return object;
//	}
//
//    /**
//     * 获取请求头总信息
//     * @return
//     */
//	private static HttpHeaders getHeaders() {
//		HttpHeaders headers = new HttpHeaders();
//		  Long timestamp = new Date().getTime();
//		  headers.add("timestamp", timestamp.toString());
//		  String signature = null;
//			try {
//				signature = RSAUtil.encryptByPublicKey(cid+";"+timestamp, publicKey);
//			} catch (Exception e) {
//				e.printStackTrace();
//		  }
//		  headers.add("signature", signature);
//		return headers;
//	}
//
//	private static final String generateUUID(){
//		String uuid = UUID.randomUUID().toString();
//		return uuid.replaceAll("-", "");
//	}
//
//
////    /**
////     * 获取图形验证码
////     * @return
////     * @throws IOException
////     */
////    public static BufferedImage getImgCode() throws IOException{
////    	JSONObject res = get("/app/imgCode");
////    	String imgBase64 = res.getString("imgBase64");
////    	byte[] bytes = Base64Utils.decodeFromString(imgBase64);
////    	ByteArrayInputStream ios = new ByteArrayInputStream(bytes);//io流
////    	BufferedImage img = ImageIO.read(ios);
////    	return img;
////    }
//
//    /**
//     * 获取手机注册验证码
//     * @param mobile 手机号
//     * @param imgCode 图形验证码
//     * @return
//     */
//    public static JSONObject getRegisterCode(String mobile,String imgCode){
//    	return get("/app/registerCode/"+mobile+"/"+imgCode);
//    }
//    /**
//     * 获取手机登录验证码
//     * @param mobile 手机号
//     * @return
//     */
//    public static JSONObject getLoginCode(String mobile){
//    	return get("/app/loginCode/"+mobile);
//    }
//
//    /**
//     * 获取签名信息
//     * @return
//     */
//    public static String getUsersig() {
//    	JSONObject res = get("/app/usersig");
//    	return res.getString("usersig");
//    }
//    /**
//     * 获取SdkAppId
//     * @return
//     */
//    public static String getSdkAppId(){
//    	JSONObject res = get("/app/sdkAppId");
//    	return res.getString("sdkAppId");
//    }
//    /**
//     * 登录
//     * @param mobile 手机号
//     * @param loginCode 手机登录验证码
//     * @return
//     */
//    public static JSONObject login(String mobile,String loginCode){
//    	Map<String,Object> params = new HashMap<String, Object>();
//    	params.put("mobile", mobile);
//    	params.put("loginCode", loginCode);
//    	JSONObject res = post("/app/login", params);
//    	return res;
//    }
//
//    /**
//     *
//     * @param mobile 手机号
//     * @param name 姓名
//     * @param idno 身份证号
//     * @param sex 性别
//     * @param addr 居住地址
//     * @param workUnit 工作单位
//     * @param registerCode 手机注册验证码
//     * @return
//     */
//    public static JSONObject register(Map<String,Object> userMap,String registerCode){
//    	/*params.put("mobile", "15017558205");
//    	params.put("name", "陈生");
//    	params.put("idno", "450503……");*/
//    	userMap.put("loginCode", registerCode);
//    	JSONObject res = post("/app/register", userMap);
//    	return res;
//    }
//
//
//
//    /**
//     * 获取紧急联系人列表
//     * @return JSONObject
//     * name 用户名
//     * faceUrl 头像
//     * workUnit 工作单位
//     * mobile 手机号
//     * sex 性别
//     * idno 身份证号
//     * addr 居住地址
//     * status 状态
//     */
//    public static JSONObject getUser(){
//    	JSONObject res = get("/app/user");
//    	return res.getJSONObject("data");
//    }
//
//    /**
//     *修改保存用户信息
//     * Map<String,Object> params
//     * name 用户名
//     * workUnit 工作单位
//     * sex 性别
//     * idno 身份证号
//     * addr 居住地址
//     *  @return JSONObject
//     */
//    public static JSONObject saveUser(Map<String,Object> userMap){
//    	JSONObject res = put("/app/user",userMap);
//    	return res;
//    }
//
//    /**
//     * 上传头像
//     * @param imgBase64压缩后的图形的base64
//     * @param ext图形后缀
//     * @return
//     */
//    public static JSONObject uploadPortrait(String imgBase64,String ext){
//    	Map<String,Object> params = new HashMap<String, Object>();
//    	JSONObject body = new JSONObject();
//    	body.put("imgBase64", imgBase64);
//    	body.put("ext", ext);
//    	System.out.println(body.toJSONString());
//    	String httpUrl = baseUrl+"/app/portrait";
//    	 URI uri = null;
// 		try {
// 			uri = new URI(httpUrl);
// 		} catch (URISyntaxException e) {
// 			e.printStackTrace();
// 		}
// 		HttpEntity requestEntity = new HttpEntity(body, getHeaders());
//    	ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(uri, requestEntity, JSONObject.class);
//    	return responseEntity.getBody();
//    }
//
//
//    /**
//     * 获取紧急联系人列表
//     * @return
//     */
//    public static JSONArray getContacts(){
//    	JSONObject res = get("/app/contacts");
//    	JSONArray contacts = res.getJSONArray("dataList");
//    	return contacts;
//    }
//
//    /**
//     * 保存联系人
//     * @param id 不为空，更新记录，id为空，新增一条记录
//     * @param name 姓名
//     * @param mobile 手机号
//     * @return
//     */
//    public static JSONObject saveContact(String id,String name,String mobile){
//    	Map<String,Object> params = new HashMap<String, Object>();
//    	if(id!=null){
//    		//id不为空，更新记录，id为空，新增一条记录
//    		params.put("id", id);
//    	}
//    	params.put("name", name);
//    	params.put("mobile", mobile);
//    	return post("/app/contact", params);
//    }
//
//    /**
//     * 删除紧急联系人列表
//     * @return
//     */
//    public static JSONObject deleteContact(String id){
//    	return get("/app/contact/"+id);
//    }
//
//
//
//    /**
//     *  发送求救
//     * @param type 类型
//     * @param lng 经度
//     * @param lat 纬度
//     * @return
//     */
//    public static JSONObject sos(String type,Double lng,Double lat){
//    	Map<String,Object> params = new HashMap<String, Object>();
//    	params.put("type", type);
//    	params.put("lng", lng);
//    	params.put("lat", lat);
//    	JSONObject res = post("/app/sos", params);
//    	return res;
//    }
//
//    /**
//     *  更新求救位置信息
//     * @param id
//     * @param lng 经度
//     * @param lat 纬度
//     * @return
//     */
//    public static JSONObject updateSos(String id,Double lng,Double lat){
//    	Map<String,Object> params = new HashMap<String, Object>();
//    	params.put("id", id);
//    	params.put("lng", lng);
//    	params.put("lat", lat);
//    	JSONObject res = put("/app/sos", params);
//    	return res;
//    }
//
//    /**
//     * 测试应用版本
//     * @param rev 版本序号,从1开始，第发布一个新版本就+1
//     * @return
//     */
//    public static JSONObject checkVersion(int rev){
//    	JSONObject res = get("/app/checkVersion/"+rev);
//    	if("0".equals(res.getString("code"))){
//    		//有新版本
//    		String path = res.getString("path");//新版本下载地址
//    		String name = res.getString("name");//版本名
//    		System.out.println("有新版本"+name+"，下载地址为"+path);
//    	}
//    	return res;
//    }
//
//    public static void main(String[] args){
//    	/*String base64 = Base64Utils.encodeToString(FileUtil.File2bytes("d:\\1.jpg"));
//    	System.out.println(uploadPortrait(base64,"jpg"));*/
//    	System.out.println(sos("110",23.135855,113.355934));
//    }
//}
