package pro.base.com.baseproject.demo5.utils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import pro.base.com.baseproject.demo5.entity.ChatMessage;

/**
 * Created by DN on 2018/2/27.
 */

public class ListUtils {
    /**
     * 按照时间对实体类集合排序
     * @param tList
     * @return
     */
    public static List<ChatMessage> upsideDownTime(List<ChatMessage> tList){
            if(tList==null||tList.size()<=0){
                return null;
            }
            //时间排序来显示ListViewList<HomeData> list
            Comparator<ChatMessage> itemComparator = new Comparator<ChatMessage>() {
                public int compare(ChatMessage info1, ChatMessage info2){
                    Date data1 = new Date(info1.getSend_time());
                    Date data2 = new Date(info2.getSend_time());
                    return data2.compareTo(data1);
                }
            };
            Collections.sort(tList, itemComparator);
            return tList;
    }
    /**
     * 按照时间对实体类集合排序
     * @param tList
     * @return
     */
    public static List<ChatMessage> upsideDownLong(List<ChatMessage> tList){
        if(tList==null||tList.size()<=0){
            return null;
        }
        Comparator<ChatMessage> itemComparator = new Comparator<ChatMessage>() {
            public int compare(ChatMessage info1, ChatMessage info2){
                Long l1 = info1.getSend_time();
                Long l2 = info2.getSend_time();
                return l2.compareTo(l1);
            }
        };
        Collections.sort(tList, itemComparator);
        return tList;
    }



}
