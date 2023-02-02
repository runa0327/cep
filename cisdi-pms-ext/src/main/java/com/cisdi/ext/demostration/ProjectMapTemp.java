package com.cisdi.ext.demostration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectMapTempExt
 * @package com.cisdi.ext.demostration
 * @description
 * @date 2023/2/1
 */
public class ProjectMapTemp {


    public static List<ProjectInfoTemp> getData(String type) {
        List<ProjectInfoTemp> res = new ArrayList<>();
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城创新研学谷（三期）项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("32055")).rate(new BigDecimal("0.2935")).build());
        res.add(ProjectInfoTemp.builder().name("南京农业大学大豆园艺作物种质创新中心项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("10630")).rate(new BigDecimal("0.93")).build());
        res.add(ProjectInfoTemp.builder().name("海南大学双创示范基地项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("8983")).rate(new BigDecimal("0.1")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城25号路道路工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("11476")).rate(new BigDecimal("0.6")).build());
        res.add(ProjectInfoTemp.builder().name("崖州湾作物创新品种示范中心农业种质资源平台项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("25515")).rate(new BigDecimal("0.45")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城国家大学科技园暨崖州湾科技城创新服务平台项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("106285")).rate(new BigDecimal("1")).build());
        res.add(ProjectInfoTemp.builder().name("深海科技城市政设施标准化一期").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("3827")).rate(new BigDecimal("0.36")).build());
        res.add(ProjectInfoTemp.builder().name("南山港科考服务体系一期项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("3781")).rate(new BigDecimal("0.30")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾中心渔港公共渔业码头项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("4048")).rate(new BigDecimal("0.63")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城生物科技创新实验室共享平台项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("2930")).rate(new BigDecimal("0.24")).build());


        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城滨海路至2号路海底隧道项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("6545")).rate(new BigDecimal("0.74")).build());
        res.add(ProjectInfoTemp.builder().name("深海科技城街道工程").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("37199.39")).rate(new BigDecimal("0.54")).build());
        res.add(ProjectInfoTemp.builder().name("南繁科技城街道工程").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("17723.45")).rate(new BigDecimal("0.63")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城再生水管道连通工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("12650")).rate(new BigDecimal("0.83")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城25号路道路工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("2006.89")).rate(new BigDecimal("0.37")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州区保港变电站至疏港大道电缆通道建设工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("985.86")).rate(new BigDecimal("0.43")).build());
        res.add(ProjectInfoTemp.builder().name("崖州湾南繁科技城起步区甘农大道道路工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("1842.44")).rate(new BigDecimal("0.28")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城城市道路标准化示范工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("14066")).rate(new BigDecimal("0.93")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城规划一路道路工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("42794")).rate(new BigDecimal("0.95")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城创元路道路工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("26858")).rate(new BigDecimal("0.89")).build());

        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城三角梅种质保存与应用基地").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("1962.73")).rate(new BigDecimal("0.16")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城采后热带保鲜应用研究实验室").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("1441.41")).rate(new BigDecimal("0.73")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州区保港变电站至疏港大道电缆通道建设工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("639.64")).rate(new BigDecimal("0.46")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾南繁科技城中部片区应急排水工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("103418.44")).rate(new BigDecimal("0.26")).build());
        res.add(ProjectInfoTemp.builder().name("种业专业研发外包服务公共平台工程(CRO)").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("73343.8")).rate(new BigDecimal("0.84")).build());
        res.add(ProjectInfoTemp.builder().name("三亚市中心渔港公租房").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("102965")).rate(new BigDecimal("0.67")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城南繁博物馆及广场项目").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("5096.52")).rate(new BigDecimal("0.37")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城综合服务中心地块电力增容工程").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("5028.79")).rate(new BigDecimal("0.84")).build());
        res.add(ProjectInfoTemp.builder().name("种业专业研发外包服务公共平台工程(CRO)").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("644")).rate(new BigDecimal("0.88")).build());
        res.add(ProjectInfoTemp.builder().name("崖州湾南繁科技城起步区甘农大道道路工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("5191")).rate(new BigDecimal("0.90")).build());


        return res.stream().filter(p -> type.equals(p.getType())).collect(Collectors.toList());

    }

    public static List<ProjectInfoTemp> getAllData() {
        List<ProjectInfoTemp> res = new ArrayList<>();
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城创新研学谷（三期）项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("32055")).rate(new BigDecimal("0.2935")).build());
        res.add(ProjectInfoTemp.builder().name("南京农业大学大豆园艺作物种质创新中心项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("10630")).rate(new BigDecimal("0.93")).build());
        res.add(ProjectInfoTemp.builder().name("海南大学双创示范基地项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("8983")).rate(new BigDecimal("0.1")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城25号路道路工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("11476")).rate(new BigDecimal("0.6")).build());
        res.add(ProjectInfoTemp.builder().name("崖州湾作物创新品种示范中心农业种质资源平台项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("25515")).rate(new BigDecimal("0.45")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城国家大学科技园暨崖州湾科技城创新服务平台项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("106285")).rate(new BigDecimal("1")).build());
        res.add(ProjectInfoTemp.builder().name("深海科技城市政设施标准化一期").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("3827")).rate(new BigDecimal("0.36")).build());
        res.add(ProjectInfoTemp.builder().name("南山港科考服务体系一期项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("3781")).rate(new BigDecimal("0.30")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾中心渔港公共渔业码头项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("4048")).rate(new BigDecimal("0.63")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城生物科技创新实验室共享平台项目").unit("三亚崖州湾科技城开发建设有限公司").type("民用建筑").amt(new BigDecimal("2930")).rate(new BigDecimal("0.24")).build());


        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城滨海路至2号路海底隧道项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("6545")).rate(new BigDecimal("0.74")).build());
        res.add(ProjectInfoTemp.builder().name("深海科技城街道工程").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("37199.39")).rate(new BigDecimal("0.54")).build());
        res.add(ProjectInfoTemp.builder().name("南繁科技城街道工程").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("17723.45")).rate(new BigDecimal("0.63")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城再生水管道连通工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("12650")).rate(new BigDecimal("0.83")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城25号路道路工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("2006.89")).rate(new BigDecimal("0.37")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州区保港变电站至疏港大道电缆通道建设工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("985.86")).rate(new BigDecimal("0.43")).build());
        res.add(ProjectInfoTemp.builder().name("崖州湾南繁科技城起步区甘农大道道路工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("1842.44")).rate(new BigDecimal("0.28")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城城市道路标准化示范工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("14066")).rate(new BigDecimal("0.93")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城规划一路道路工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("42794")).rate(new BigDecimal("0.95")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城创元路道路工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("市政道路").amt(new BigDecimal("26858")).rate(new BigDecimal("0.89")).build());

        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城三角梅种质保存与应用基地").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("1962.73")).rate(new BigDecimal("0.16")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城采后热带保鲜应用研究实验室").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("1441.41")).rate(new BigDecimal("0.73")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州区保港变电站至疏港大道电缆通道建设工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("639.64")).rate(new BigDecimal("0.46")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾南繁科技城中部片区应急排水工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("103418.44")).rate(new BigDecimal("0.26")).build());
        res.add(ProjectInfoTemp.builder().name("种业专业研发外包服务公共平台工程(CRO)").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("73343.8")).rate(new BigDecimal("0.84")).build());
        res.add(ProjectInfoTemp.builder().name("三亚市中心渔港公租房").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("102965")).rate(new BigDecimal("0.67")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城南繁博物馆及广场项目").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("5096.52")).rate(new BigDecimal("0.37")).build());
        res.add(ProjectInfoTemp.builder().name("三亚崖州湾科技城综合服务中心地块电力增容工程").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("5028.79")).rate(new BigDecimal("0.84")).build());
        res.add(ProjectInfoTemp.builder().name("种业专业研发外包服务公共平台工程(CRO)").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("644")).rate(new BigDecimal("0.88")).build());
        res.add(ProjectInfoTemp.builder().name("崖州湾南繁科技城起步区甘农大道道路工程项目").unit("三亚崖州湾科技城开发建设有限公司").type("其他").amt(new BigDecimal("5191")).rate(new BigDecimal("0.90")).build());


        return res;
    }


}
