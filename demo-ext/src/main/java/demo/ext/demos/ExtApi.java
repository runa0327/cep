package demo.ext.demos;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.util.DateTimeUtil;
import com.qygly.shared.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class ExtApi {
    public void extApi() {
        // 输入：
        Map<String, Object> extApiParamMap = ExtJarHelper.getExtApiParamMap();
        log.info(JsonUtil.toJson(extApiParamMap));

        // 处理：自行编写（略）。

        // 输出：
        Map<String, Object> retMap = new LinkedHashMap<>();
        retMap.put("a", 123);
        Map<String, Object> childMap = new LinkedHashMap<>();
        retMap.put("b", childMap);
        childMap.put("b1", "aaaa");
        childMap.put("b2", true);
        childMap.put("b3", DateTimeUtil.dttmToString(LocalDateTime.now()));

        ExtJarHelper.setReturnValue(retMap);
    }
}
