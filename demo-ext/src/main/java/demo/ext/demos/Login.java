package demo.ext.demos;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.LoginExtResult;

import java.util.LinkedHashMap;

public class Login {
    public void login() {
        LoginExtResult result = new LoginExtResult();
        result.changedGlobalVarMap = new LinkedHashMap<>();
        result.changedGlobalVarMap.put("P_PRJ_IDS", "123,456,789");
        result.changedGlobalVarMap.put("P_BOOL", true);
        result.changedGlobalVarMap.put("P_INT", 123);
        ExtJarHelper.setReturnValue(result);
    }
}
