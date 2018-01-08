# BaseProject
此依赖是对于Activity的一个封装
=======
1：说明：
-----
  （1）:BaseActivity的部分方法: <br>
      1.initParms();初始化参数<br>
      2.bindView();绑定视图（可不设置）<br>
      3.bindLayout();绑定布局（必须设置）<br>
      4.initView();初始化控件<br>
      5.setListener();设置监听<br>
      6.doBusiness();业务操作<br>
      7.widgetClick();view的点击<br>
      8.showToast();吐司（可控制是否吐司）<br>
      9.showLog();打印日志（可控制是否打印日志）<br>
    
  （2）:BaseFragment的部分方法：<br>
      1.bindView();绑定视图（可不设置）<br>
      2.bindLayout();绑定布局（必须设置）<br>
      3.initView();初始化控件<br>
      4.initData();业务操作<br>
      5.widgetClick(View v);view的点击<br>
      6.showToast(String msg)吐司（可控制是否吐司）<br>
      7.showLog(String msg)打印日志（可控制是否打印日志）<br>
   (3):BaseFragmentActivity的部分方法:<br>
   (4):MenuFragmentActivity的部分方法：<br>
   
2.base的使用正在编写中...<br>
-----
3.工具类的使用说明：<br>
----
  使用前请先初始化：<br>
  Utils.init(this);<br>
  ClipUtils.init(this);<br>
  否则在使用过程中会出现空指针异常；<br>
 

使用：
========
Add it in your root build.gradle at the end of repositories:<br>
```java
 maven { url 'https://jitpack.io' }
 compile 'com.github.YLAndsoft:BaseProject:v1.2'
```
  
  
  
  
  
  
  
  




