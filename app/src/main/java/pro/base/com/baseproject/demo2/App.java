package pro.base.com.baseproject.demo2;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by DN on 2018/4/23.
 */
//建表
@Table(name = "APP_TABLE")//表名
public class App {
    /**
     * isId = true 代表该字段是这张表的主键，类型也可以是String (赋值为false就不是主键了)
     * autoGen = true 代表主键自增长，如果不是自增长，那就赋值false
     */
    @Column(name = "ID", isId = true, autoGen = true)
    private int _id;
    @Column(name = "APPID",property = "NOT NULL")
    private int appID;
    @Column(name = "APPICON",property = "NOT NULL")
    private String appIcon;
    @Column(name = "APPNAME",property = "NOT NULL")
    private String appName;
    @Column(name = "SCORE")
    private float score;
    @Column(name = "LABEL")
    private String label;
    @Column(name = "SHOWIMAGE")
    private String showImage;//展示图
    private String brif;
    private String size;
    private String image1;
    private String image2;
    private String image3;

    public String getBrif() {
        return brif;
    }

    public void setBrif(String brif) {
        this.brif = brif;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    private String image4;
    private String image5;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    public String toString(){
        return "{id="+_id+",\nappID="+appID+",\nname="+appName+",\nscore="+score+",\nlabel="+label+",\nshowImage="+showImage+"}";
    }
    public App(int appID,String appIcon,String appName,String label,String size,String image1){
        this.appID = appID;
        this.appIcon = appIcon;
        this.appName = appName;
        this.label = label;
        this.size = size;
        this.image1 = image1;
    }

    public App(){
    }
}
