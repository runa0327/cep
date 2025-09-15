package demo.ext.test;

import com.qygly.shared.ad.ViewShowMode;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.ad.ext.ViewToOpen;
import com.qygly.shared.interaction.InvokeActResult;

import java.util.ArrayList;

public class TestExt {
    public void changeVar() {
    }

    public void save() {
    }

    public void f1() {
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.urlToOpenList = new ArrayList<>();

        UrlToOpen urlToOpen = new UrlToOpen();
        urlToOpen.url = "https://www.baidu.com";
        urlToOpen.viewShowMode = ViewShowMode.DIALOG;
        urlToOpen.popupWidth = "500";
        urlToOpen.popupHeight = "80%";

        invokeActResult.urlToOpenList.add(urlToOpen);
    }

    public void f2() {
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.viewToOpenList = new ArrayList<>();

        ViewToOpen viewToOpen = new ViewToOpen();
        viewToOpen.viewId = "视图ID";
        viewToOpen.viewShowMode = ViewShowMode.DRAWER;
        viewToOpen.drawerWidth = "30%";

        invokeActResult.viewToOpenList.add(viewToOpen);
    }
}
