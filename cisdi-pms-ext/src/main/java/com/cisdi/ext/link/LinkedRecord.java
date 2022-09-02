package com.cisdi.ext.link;

import com.fasterxml.jackson.annotation.JsonView;
import com.qygly.shared.end.B;
import com.qygly.shared.end.F;

import java.util.HashMap;
import java.util.Map;

public class LinkedRecord {
    @JsonView({F.class, B.class})
    public Map<String, String> textMap = new HashMap<>();

    @JsonView({F.class, B.class})
    public Map<String, Object> valueMap = new HashMap<>();
}
