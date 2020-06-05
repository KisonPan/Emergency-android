package top.banach.emergency.constants;

import java.io.File;

import android.os.Environment;

/**
 * <pre>
 * Title: 功能/模块 
 * Description: 类的描述
 * 常量配置
 * 修订历史： 日期          作者        参考         描述
 * </pre>
 * 
 * @author Kisonpan
 * @version 1.0
 */
public class C {

	public static final String tag = "Emergency";
	public static final int TIME_OUT = 5000;
	public static final String PHONE = "phone";
	public static final String IS_AUTO_LOGIN = "is_auto_login";
	public static final String ID = "id";
	public static final String LOGIN_NAME = "loginName";
	public static final String USER_ID = "user_id";
	public static final String TOKEN = "token";
	public static final String TITLE = "title";
	public static final String USER_NAME = "userName";
	public static final String LOGIN_PSW = "psw";
	public static final String DATA = "data";
	public static final String URL = "url";
	public static final String ITEM_ID = "item_id";
	public static final String ITEM = "item";
	public static final String TYPE = "type";
	public static final String PHOTOPATH = "photoPath";
	public static final String SYS_WIFI_SETTING = "sys_wifi_setting";
	public static final String LOGIN_STATE = "login_state";

	public static class Base {
		
		//测试和正式版本的切换步骤
		// 1. 修改包名
		// 2，修改APP_NAME
		// 3, 修改这里(看下面)

		//--------测试环境常量-------------//
		/**测试APP包名 */
		public static final String APP_PKG_NAME = "com.kisonpan.emergency";
		/**测试版本*/
		public static final String verisonName = "测试版本：";
		/**测试服务器API*/
//		public static final String BASE_URL = "http://120.24.251.185:890/YJGL"; //正式IP
//		public static final String BASE_URL_TEST = "http://gzant.vicp.io:16767/YJGL";
//		public static final String BASE_URL = "http://192.168.43.117:88/YJGL"; //英俊机子IP
//		public static final String BASE_URL = "http://gzant.vicp.io:16767/YJGL";   //英俊机子IP(外网）
//		public static final String BASE_URL = "http://120.77.207.87";   //正式IP(阿里云）
//		public static final String BASE_URL = "http://192.168.3.47:88/YJGL";
//		public static final String BASE_URL = "http://120.77.207.87/YJGL"; //最新正式IP
		public static final String BASE_URL = "http://app.banach.top/YJGL"; //最新正式域名
		

//		//-----------正式环境------------//
//		/**正式APP包名 */
//		public static final String APP_PKG_NAME = "com.kisonpan.emergency.r";
//		/**正式版本*/
//		public static final String verisonName = "版本：";
//		/**正式服务器API*/
//		public static final String BASE_URL = "http://";// 正式

		

//----------------------------------------------------------------
		/** 请求服务的前缀 */
		public static final String SERVER_PRE = "http://";
		/** 数据库文件名 */
		public static final String DB_FILENAME = "emergency.db";
		/** 默认线程池大小 */
		public static final int DEFAULT_THREAD_POOL_SIZE = 8;
		/** 默认缓冲区大小 */
		public static final int DEFAULT_BUFFER_SIZE = 4096;
		/** APP标签，日志前缀 */
		public static final String APP_TAG = "emergency_";
		/** APP的SharedPreferences的全局目录 */
		public static final String APP_PREF = APP_PKG_NAME + ".pref";

	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// core settings (important)

	public static final class dir {
		public static final String base = "";// SdCardUtil.getSdCardPath() +
												// "/seanmobile";
		public static final String faces = base + "/faces";
		public static final String images = base + "/images";

		/**缓存根目录路径*/
		public static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Base.APP_PKG_NAME;
		
		/**
		 * 缓存图片路径
		 */
		public static final String CACHE_IMAGE_PATH = CACHE_PATH + File.separator + "img" + File.separator;
		
		/**
		 * temp路径
		 */
		public static final String CACHE_TEMP_PATH = CACHE_PATH + File.separator + "temp" + File.separator;

		
		/**
		 * 缓存apk版本文件路径  
		 */
		public static final String CACHE_APK_VERSION_PATH = CACHE_PATH + File.separator + "version" + File.separator;
	}

	public static final class api {
//		/**
//		 * 版本更新,
//		 * 提交参数：{"info":{"token":"","imei":""},"postData":{"apkcode":"101",
//		 * "apkname":"Android1.01","apktype":"Android"}}
//		 */
//		public static final String VERSION_CHECK = Base.BASE_URL + "/roomacceptance/mobile/appUpdate/checkUpdate.do";
		
		public static final String register = Base.BASE_URL + "/app/register.do";  //用户注册
		public static final String loginOn = Base.BASE_URL + "/app/loginOn.do"; //用户登录
		public static final String userInfo = Base.BASE_URL + "/app/userInfo.do"; //获取用户信息
		public static final String updatePosition = Base.BASE_URL + "/app/updatePosition.do"; //更新位置信息
		public static final String upload = Base.BASE_URL + "/app/upload.do"; //上传文件
//		public static final String upload_test = Base.BASE_URL_TEST + "/app/upload.do"; //上传文件
		public static final String getEmergencyList = Base.BASE_URL + "/app/getEmergencyList.do"; //获取求救列表内容	
		public static final String getEmergencyInfo = Base.BASE_URL + "/app/getEmergencyInfo.do"; //获取求救详细内容
		public static final String delEmergencyInfo = Base.BASE_URL + "/app/delEmergencyInfo.do"; //删除求救信息
		public static final String askForHelp = Base.BASE_URL + "/app/askForHelp.do"; //发布求助	
		public static final String getHelpList = Base.BASE_URL + "/app/getHelpList.do"; //获取求助列表
		public static final String getAlarmList = Base.BASE_URL + "/app/getAlarmList.do"; //获取预警列表
		public static final String getMyEmergencyList = Base.BASE_URL + "/app/getMyEmergencyList.do"; //获取我发出的求救列表内容
		public static final String getMyHelpList = Base.BASE_URL + "/app/getMyHelpList.do"; //获取我发出的求助列表内容
		public static final String getMyAlarmList = Base.BASE_URL + "/app/getMyAlarmList.do"; //获取我发出的求助列表内容

		public static final String sendEmergency = Base.BASE_URL + "/app/sendEmergency.do"; //发送求救信号
		public static final String sendAlarm = Base.BASE_URL + "/app/sendAlarm.do"; //发送预警信号
		public static final String getHelperList = Base.BASE_URL + "/app/getHelperList.do";//获取帮助者列表
		public static final String getHelpReplyList = Base.BASE_URL + "/app/getHelpReplyList.do"; //获取回信息。
		public static final String getAlarmReplyList = Base.BASE_URL + "/app/getAlarmReplyList.do"; //获取回信息。
		public static final String getEmergencyReplyList = Base.BASE_URL + "/app/getEmergencyReplyList.do"; //获取回信息。
		public static final String replyForEmergency = Base.BASE_URL + "/app/replyForEmergency.do"; //发送回复
		public static final String replyForHelp = Base.BASE_URL + "/app/replyForHelp.do"; //发送回复
		public static final String replyForAlarm = Base.BASE_URL + "/app/replyForAlarm.do"; //发送回复
		public static final String getHelpInfo = Base.BASE_URL + "/app/getHelpInfo.do"; //获取帮助项详情
		public static final String getAlarmInfo = Base.BASE_URL + "/app/getAlarmInfo.do"; //获取预警项详情
		
		public static final String getContactList = Base.BASE_URL + "/app/getContactList.do"; //获取家庭联系人
		public static final String saveContact = Base.BASE_URL + "/app/saveContact.do"; //设置家庭联系人
		public static final String saveUserInfo = Base.BASE_URL + "/app/saveUserInfo.do"; //保存联系人信息
		public static final String updatePassword = Base.BASE_URL + "/app/updatePassword.do"; //修改密码
		public static final String uploadPortrait = Base.BASE_URL + "/app/uploadPortrait.do"; //上传头像
		public static final String gatherLocation = Base.BASE_URL + "/app/gatherLocation.do"; //实时位置
		public static final String getAllHelpReplyList = Base.BASE_URL + "/app/getAllHelpReplyList.do"; //获取回信息。
		public static final String protocol = Base.BASE_URL + "/app/protocol.do"; //协议
		public static final String getTitles = Base.BASE_URL + "/app/getTitles.do"; //
		public static final String getLoginCode = Base.BASE_URL + "/app/getLoginCode.do"; //获取登陆验证码
		public static final String getRegisterCode = Base.BASE_URL + "/app/getRegisterCode.do"; //获取注册验证码
	}
	
	public static final class params {
		public static final String name = "name";//用户全名
		public static final String loginName = "loginName";//登录名
		public static final String password = "password";//登录密码
		public static final String password_old = "password_old";//登录密码
		public static final String loginCode = "loginCode"; //登陆验证码
		public static final String registerCode = "registerCode"; //注册验证码
		public static final String phone_no = "phone_no";//手机号码
		public static final String jzdz = "jzdz";//居住地址
		public static final String gzdw = "gzdw";//工作单位
		public static final String portrait = "portrait";//肖像图片路径
		public static final String clientId = "clientId";//客户端ID
		public static final String appUserId = "appUserId";//用户ID
		public static final String userSig = "userSig";
		public static final String xAxis = "xAxis";//X轴座标（经）
		public static final String yAxis = "yAxis";//y轴座标（纬）
		public static final String pageNum = "pageNum";//页码
		public static final String pageSize = "pageSize";//页面记录数大小
		public static final String type = "type";//类型
		public static final String title = "title";//类型
		public static final String content = "content";//类型
		public static final String imagePath = "imagePath";//图片路径
		public static final String helpId = "helpId";//帮助项id
		public static final String helper = "helper";//帮助者
		public static final String alarmId = "alarmId";//预警项id
		public static final String emergencyId = "emergencyId";//预警项id
		public static final String location = "location"; //位置
		public static final String address = "address"; //地址
		public static final String latitude = "latitude"; //纬度
		public static final String longitude = "longitude"; //经度
		public static final String keyword = "keyword"; //关键词
		public static final String contactJson = "contactJson"; //联系人
		public static final String email = "email"; //邮箱
		public static final String relation = "relation"; //邮箱
	}

	public static final class task {
		public static final int index = 1001;
		public static final int login = 1002;
		public static final int logout = 1003;
	}

	public static final class err {
		public static final String network = "网络错误";
		public static final String message = "消息错误";
		public static final String jsonFormat = "消息格式错误";
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// intent & action settings

	public static final class intent {
		public static final class action {
			public static final String EDITTEXT = "com.sean.hayes.hdem.EDITTEXT";
			public static final String EDITBLOG = "com.sean.hayes.hdem.EDITBLOG";
		}
	}

	public static final class Bugly {
//		public static final String AppID = "c7bb19ff59";
//		public static final String AppKey = "779adabe-705c-40e1-a07d-c98ae7d2f627";
//		public static final String Version = "1.0";
//		public static final String Channel = "Channel20160812";
	}

	public static final class Type {
		public static final int TypeLogin = 0; // 已登录 
		public static final int TypeNoLogin = 1; // 未登录
		public static final String TypeAlarm = "alarm"; //预警
		public static final String TypeHelp = "help"; //求助
		public static final String TypeReply = "reply"; //回复
		public static final String TypePortrait = "portrait"; //回复
		public static final String TypeEmergency = "emergency"; //求救
		
		public static String CHAT_FROM = "from";
		public static String CHAT_TO = "to";
		
	}
	
	public static final class Code {
		public static int REQUEST_CODE_CAPTURE_CAMEIA = 1;
		public static int REQUEST_CODE_CAPTURE_CAMEIA2 = 2;
		public static int REQUEST_CODE_SEND_LOCACTION = 3;
		public static int REQUEST_CODE_PICK_PHOTO = 2;
		
	}

	public static final class Cfg {
		/** 请求成功 */
		public static String REQUEST_SUCCESS = "S";
		/** 请求失败 */
		public static String REQUEST_FAILED = "E";
		/** 请求返回值--成功 */
		public static int RETURN_SUCCESS = 1;
		/** 请求返回值--失败 */
		public static int RETURN_ERROR = -1;
	}
	
	public static final class TabIndex {
		public final static int TAB_HELP = 1;
		public final static int TAB_EMERGENCY = 2;
		public final static int TAB_ALARM = 3;
	}
	
	public static final class Key { 
		public final static String photoPath = "photoPath";
		public final static String title = "title";
		public final static String content = "content";
		public final static String id = "id";
		public final static String type = "type";
		public final static String name = "name";
		public final static String relation = "relation";
		public final static String mobile = "mobile";
		public final static String replyContent = "replyContent";
		public final static String sos_id = "sos_id";
		public final static String CURRENT_DATE = "CURRENT_DATE";
		public final static String LAST_LOGIN_DATE = "LAST_LOGIN_DATE";
		public final static String IMG_BASE64 = "LAST_LOGIN_DATE";

	}
}
