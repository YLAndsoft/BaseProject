package pro.base.com.baseproject.demo2;

import org.xutils.db.annotation.Column;

import fyl.base.db.AbstractDB;
import fyl.base.db.SColumDB;
import fyl.base.db.TableDB;

/**
 * Created by DN on 2018/1/18.
 */
//1.表名 2.数据库名称  3.版本号
@TableDB(table = "user",dbName="demo.db",version=1)
public class User extends AbstractDB{
    @SColumDB(str_column = "uID")
    public String uID;
    @SColumDB(str_column = "userName")
    public String userName;
    @SColumDB(str_column = "account")
    public String account;
    @SColumDB(str_column = "passWord")
    public String passWord;
    @SColumDB(str_column = "qLwvel")
    public String qLwvel;
    @SColumDB(str_column = "ansLevel")
    public String ansLevel;
    @SColumDB(str_column = "qTotal")
    public String qTotal;
    @SColumDB(str_column = "ansTotal")
    public String ansTotal;
    @SColumDB(str_column = "fastAnsed")
    public String fastAnsed;
    @SColumDB(str_column = "rightTimes")
    public String rightTimes;
    @SColumDB(str_column = "gold")
    public String gold;
    @SColumDB(str_column = "addTime")
    public String addTime;
    @SColumDB(str_column = "userHeadUrl")
    public String userHeadUrl;
    @SColumDB(str_column = "userSignature")
    public String userSignature;
    @SColumDB(str_column = "userTage")
    public String userTage;
    @SColumDB(str_column = "sex")
    public String sex;
    @SColumDB(str_column = "birthDay")
    public String birthDay;
    @SColumDB(str_column = "address")
    public String address;


}
