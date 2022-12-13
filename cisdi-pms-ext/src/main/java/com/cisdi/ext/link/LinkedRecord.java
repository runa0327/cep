package com.cisdi.ext.link;

import com.fasterxml.jackson.annotation.JsonView;
import com.qygly.shared.end.Front;

import java.util.HashMap;
import java.util.Map;

public class LinkedRecord {
    @JsonView({Front.class})
    public Map<String, String> textMap = new HashMap<>();

    @JsonView({Front.class})
    public Map<String, Object> valueMap = new HashMap<>();
}
