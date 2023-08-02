package com.cisdi.ext.link;

import com.fasterxml.jackson.annotation.JsonView;
import com.qygly.shared.end.Front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttLinkResult {
    @JsonView({Front.class})
    public Map<String, LinkedAtt> attMap = new HashMap<>();

    @JsonView({Front.class})
    public Map<String, Boolean> childCreatable = new HashMap<>();

    @JsonView({Front.class})
    public Map<String, Boolean> childClear = new HashMap<>();

    @JsonView({Front.class})
    public Map<String,Boolean> childShow = new HashMap<>();

    @JsonView({Front.class})
    public Map<String, List<LinkedRecord>> childData = new HashMap<>();

}
