package base;


/**
 * ������
 * ��װ�����ַ�ͳ���
 * @author Administrator
 *
 */
public class Constant {
//	public static final String PATH_NEWS_LIST=PATH+"/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
	/** get访问模式*/
	public static final int METHOD_GET=1;
	/** post访问模式 */
	public static final int METHOD_POST=2;
	/**���ſͻ���������ʵĻ��ַ*/
	public static final String PATH="http://118.244.212.82:9092/newsClient";
	/**�����б�ĵ�ַ*/
	public static final String PATH_NEWS_LIST=PATH+"/news_list";
	/**登录网址*/
	public  static final String PATH_LOGIN=PATH+"/user_login";
	/**注册网址*/
	public  static final String PATH_REGSITER=PATH+"/user_register";
	/**用户中心网址*/
	public  static final String PATH_USER_HOME=PATH+"/user_home";
	/**密码找回网址*/
	public  static final String PATH_PWD_FIND=PATH+"/user_forgetpass";
	/**显示评论网址
	 * http://118.244.212.82:9092/newsClient/cmt_list?ver=1&nid=14&type=1&stamp=20140321&cid=1&dir=1&cnt=20*/
	public  static final String PATH_COMMENT_SHOW=PATH+"/cmt_list";
	/**发送评论网址*/
	public  static final String PATH_COMMENT_SEND=PATH+"/cmt_commit";
	/**评论数量网址*/
	public  static final String PATH_COMMENT_NUM=PATH+"/cmt_num";
	/**版本请求网址*/
	public  static final String PATH_VERSION=PATH+"/update";
	/**新闻类型请求网址*/
	public  static final String PATH_NEW_TYPE=PATH+"/news_sort";
}
