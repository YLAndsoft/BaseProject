package pro.base.com.baseproject.dbDemo;

import fyl.base.db.AbstractDB;
import fyl.base.db.SColumDB;
import fyl.base.db.TableDB;

/**
 * Created by DN on 2018/1/18.
 */
//1.表名 2.数据库名称  3.版本号
@TableDB(table = "student",dbName="demo.db",version=1)
public class Studio extends AbstractDB{
    @SColumDB(str_column = "name")
    public String name;
    @SColumDB(str_column = "age")
    public String age;
    @SColumDB(str_column = "sex")
    public String sex;

}
