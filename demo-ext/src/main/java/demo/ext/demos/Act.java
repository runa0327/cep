package demo.ext.demos;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.ViewShowMode;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.ad.ext.ViewToOpen;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;

import java.util.ArrayList;
import java.util.List;

public class Act {

    /**
     * 增删改查（类型1）。
     * 挂载在“测试班级”的“测试改变班级下的学生”操作上。
     * 可在“测试班级学生（上下）”等视图查看效果。
     * 效果：显示自定义消息，重新获取页面数据，打开若干网址，打开若干视图。
     */
    public void act() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            // 遍历选择的每条记录并自行处理（略）。
        }

        if (System.currentTimeMillis() % 5 == 0) {
            throw new BaseException("故意的异常（数据库事务会自动回滚）！");
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        // 显示操作成功提示消息（也可不设置，默认为“操作成功。”）：
        invokeActResult.msg = "你的操作已执行成功。";
        // 若不相信显示任何消息：
        // invokeActResult.hideMsg=true;
        // 重新获取页面数据：
        invokeActResult.reFetchData = true;
        // 打开若干网址：
        invokeActResult.urlToOpenList = getUrlToOpenList();
        // 打开若干视图：
        invokeActResult.viewToOpenList = getViewToOpenList();

        ExtJarHelper.setReturnValue(invokeActResult);
    }

    private List<UrlToOpen> getUrlToOpenList() {
        List<UrlToOpen> urlToOpenList = new ArrayList<>();

        {
            UrlToOpen urlToOpen = new UrlToOpen();
            urlToOpenList.add(urlToOpen);

            urlToOpen.url = "https://www.163.com";
            urlToOpen.viewShowMode = ViewShowMode.TAB;
        }

        {
            UrlToOpen urlToOpen = new UrlToOpen();
            urlToOpenList.add(urlToOpen);

            urlToOpen.url = "https://www.baidu.com";
            urlToOpen.viewShowMode = ViewShowMode.DIALOG;
            urlToOpen.popupWidth = "500";
            urlToOpen.popupHeight = "80%";
        }


        {
            UrlToOpen urlToOpen = new UrlToOpen();
            urlToOpenList.add(urlToOpen);

            urlToOpen.url = "https://www.sina.com.cn";
            urlToOpen.viewShowMode = ViewShowMode.DRAWER;
            urlToOpen.drawerWidth = "500";
        }

        return urlToOpenList;
    }

    private List<ViewToOpen> getViewToOpenList() {

        List<ViewToOpen> viewToOpenList = new ArrayList<>();

        ViewToOpen viewToOpen = new ViewToOpen();
        viewToOpenList.add(viewToOpen);

        viewToOpen.viewId = "视图ID";
        viewToOpen.viewShowMode = ViewShowMode.DRAWER;
        viewToOpen.drawerWidth = "30%";

        return viewToOpenList;
    }

}
