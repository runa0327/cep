package com.cisdi.pms.job.serviceImpl.base;

import com.cisdi.pms.job.domain.base.HrDept;
import com.cisdi.pms.job.mapper.base.HrDeptMapper;
import com.cisdi.pms.job.service.base.HrDeptService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class HrDeptServiceImpl implements HrDeptService {

    @Resource
    private HrDeptMapper hrDeptMapper;

    /**
     * 通过父级id查询所有子级id信息
     * @param parentId 父级id
     * @return 部门信息
     */
    @Override
    public List<HrDept> getDeptByParentId(String parentId) {
        List<HrDept> list = new ArrayList<>();
        addChild(list,parentId);
        HrDept hrDept = hrDeptMapper.getHrDeptById(parentId);
        list.add(hrDept);
        return list;
    }

    /**
     * 子级id存入返回信息
     * @param list 返回信息
     * @param parentId 父级id
     */
    private void addChild(List<HrDept> list, String parentId) {
        List<HrDept> childList = hrDeptMapper.getDeptIdByParentId(parentId);
        list.addAll(childList);
        if (!CollectionUtils.isEmpty(childList)){
            childList.forEach(p->{
                addChild(list,p.getId());
            });
        }
    }

    /**
     * 通过部门名称查询部门用户信息
     * @param nameList 部门名称集合
     * @return 部门人员信息
     */
    @Override
    public List<String> getDeptUserByDeptName(List<String> nameList) {
        return hrDeptMapper.getDeptUserByDeptNameList(nameList);
    }
}
