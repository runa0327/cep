package com.cisdi.ext.link;

import com.fasterxml.jackson.annotation.JsonView;
import com.qygly.shared.end.B;
import com.qygly.shared.end.F;
import com.qygly.shared.interaction.TypeValueText;

import java.util.Map;

public class AttLinkResult {
    @JsonView({F.class, B.class})
    public Map<String, TypeValueText> attMap;

}
