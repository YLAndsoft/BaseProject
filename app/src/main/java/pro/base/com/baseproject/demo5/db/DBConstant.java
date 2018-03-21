package pro.base.com.baseproject.demo5.db;

/**
 * Created by DN on 2018/2/10.
 */

public class DBConstant {
    public static final String DATABASE_NAME = "db_chat";

    public static final String LEFT_PARENTHESIS = " (";
    public static final String RIGHT_PARENTHESIS = ")";
    public static final String COMMA = ",";
    public static final String TERMINATOR = ";";
    private static final String CREATE_TABLE = "CREATE TABLE ";

    public static final String TB_CHAT = "tb_chat";//表名
    public static final StringBuilder CREATETABLE_CHAT = new StringBuilder();
    public static final StringBuilder CREATEINDEX_CHAT = new StringBuilder();

    public static final String TB_ID = "_id";
    public static final String TB_RE_UID = "receive_iD";//接收用户ID
    public static final String TB_SE_USERNAME = "send_userName";//用户名称
    public static final String TB_SE_HEADIMAGE = "send_image";//用户头像
    public static final String TB_SE_MESSGAE = "send_message";//发送的消息
    public static final String TB_RE_NAME = "receive_name";//接收消息的用户名称
    public static final String TB_RE_IMAGE = "receive_image";//接收消息的头像
    public static final String TB_TIME = "send_time";//发送时间
    public static final String TB_TYPE = "type";//消息类型

    public static final int CHAT_INDEX_ID = 0;
    public static final int CHAT_INDEX_RE_UID = 1;
    public static final int CHAT_INDEX_SE_USERNAME = 2;
    public static final int CHAT_INDEX_SE_HEADIMAGE = 3;
    public static final int CHAT_INDEX_SE_MESSGAE = 4;
    public static final int CHAT_INDEX_RE_NAME = 5;
    public static final int CHAT_INDEX_RE_IMAGE = 6;
    public static final int CHAT_INDEX_TIME = 7;
    public static final int CHAT_INDEX_TYPE = 8;

    /**
     * 创建聊天信息表
     */
    static {
        CREATETABLE_CHAT.append(CREATE_TABLE);
		CREATETABLE_CHAT.append(TB_CHAT);
		CREATETABLE_CHAT.append(LEFT_PARENTHESIS);
		CREATETABLE_CHAT.append(TB_ID+" INTEGER PRIMARY KEY AUTOINCREMENT"+COMMA);
		CREATETABLE_CHAT.append(TB_RE_UID+" VARCHAR(255)"+COMMA);
		CREATETABLE_CHAT.append(TB_SE_USERNAME+" VARCHAR(255)"+COMMA);
		CREATETABLE_CHAT.append(TB_SE_HEADIMAGE+" VARCHAR(255)"+COMMA);
		CREATETABLE_CHAT.append(TB_SE_MESSGAE+" VARCHAR(255)"+COMMA);
		CREATETABLE_CHAT.append(TB_RE_NAME+" VARCHAR(255)"+COMMA);
		CREATETABLE_CHAT.append(TB_RE_IMAGE+" VARCHAR(255)"+COMMA);
        CREATETABLE_CHAT.append(TB_TIME+" VARCHAR(255)"+COMMA);
        CREATETABLE_CHAT.append(TB_TYPE+" INTEGER");
		CREATETABLE_CHAT.append(RIGHT_PARENTHESIS);
		CREATETABLE_CHAT.append(TERMINATOR);
    }



}
