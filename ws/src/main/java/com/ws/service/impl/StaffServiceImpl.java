package com.ws.service.impl;

import com.ws.entity.StaffNew;
import com.ws.mapper.StaffMapper;
import com.ws.entity.Staff;
import com.ws.service.StaffService;
import com.ws.until.RestUntil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author isHello
 */
@Service
public class StaffServiceImpl implements StaffService {

    @Resource
    private StaffMapper staffMapper;

    @Override
    public RestUntil getStaffByMark(Integer mark) {
        RestUntil restUntil = new RestUntil();
        try {
            List list = new ArrayList();
            List<Staff> staffByMark = staffMapper.getStaffByMark(mark);
            if (staffByMark != null && staffByMark.size() > 0) {
                for (Staff staff : staffByMark) {
                    Staff newStaff = new Staff();
                    newStaff.setId(staff.getId());
                    newStaff.setGh(staff.getGh());
                    newStaff.setMc(staff.getMc());
//                    if (staff.getBmid() != null && !("".equals(staff.getBmid()))) {
//                        newStaff.setBmid(staff.getBmid());
//                        newStaff.setBmmc(staff.getBmmc());
//                    } else {
//                        newStaff.setBmid("");
//                        newStaff.setBmmc("");
//                    }
                    newStaff.setZwmc(staff.getZwmc());
                    List position = getPositionId(staff.getZwmc());
//                    newStaff.setZwid(position.get(0).toString());
                    newStaff.setZwmc(position.get(1).toString());
                    List workType = getWorkTypeId(staff.getGz());
                    newStaff.setGz(workType.get(0).toString());
                    newStaff.setGzmc(workType.get(1).toString());
                    String sex = staff.getSex();
                    if ("1".equals(sex)) {
                        newStaff.setSex("男");
                    } else if ("2".equals(sex)) {
                        newStaff.setSex("女");
                    } else {
                        newStaff.setSex("男");
                    }
                    newStaff.setSjh1(staff.getSjh1());
                    newStaff.setZjh(staff.getZjh());
                    list.add(newStaff);
                    if (mark == 1 || mark == 2) {
                        Integer staffId = staff.getId();
                        staffMapper.updateStaffById(staffId);
                    }
                }
                restUntil.setData(list);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            } else {
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg("请求接口错误");
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil getStaff() {
        RestUntil restUntil = new RestUntil();
        try {
            List<StaffNew> staff = staffMapper.getStaff();
            if (staff != null && staff.size() > 0) {
                restUntil.setData(staff);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            } else {
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg("请求接口错误");
            e.printStackTrace();
        }
        return restUntil;
    }

    private List getPositionId(String positionName) {
        List list = new ArrayList();
        switch (positionName) {
            case "安环部长":
                list.add("1112111113");
                list.add("安环部长");
                break;
            case "安全总监":
                list.add("1112111114");
                list.add("安全总监");
                break;
            case "班长":
                list.add("1112111115");
                list.add("班长");
                break;
            case "财务科长":
                list.add("1112111116");
                list.add("财务科长");
                break;
            case "仓库主任":
                list.add("1112111117");
                list.add("仓库主任");
                break;
            case "操作":
                list.add("1112111118");
                list.add("操作");
                break;
            case "叉车驾驶员":
                list.add("1112111119");
                list.add("叉车驾驶员");
                break;
            case "厨师":
                list.add("1112111120");
                list.add("厨师");
                break;
            case "电工":
                list.add("1112111121");
                list.add("电工");
                break;
            case "电气专业负责人":
                list.add("1112111122");
                list.add("电气专业负责人");
                break;
            case "电仪车间副主任":
                list.add("1112111123");
                list.add("电仪车间副主任");
                break;
            case "电仪车间主任":
                list.add("1112111124");
                list.add("电仪车间主任");
                break;
            case "董事长":
                list.add("1112111125");
                list.add("董事长");
                break;
            case "动力车间副主任":
                list.add("1112111126");
                list.add("动力车间副主任");
                break;
            case "动力车间主任":
                list.add("1112111127");
                list.add("动力车间主任");
                break;
            case "反应车间副主任":
                list.add("1112111128");
                list.add("反应车间副主任");
                break;
            case "反应车间主任":
                list.add("1112111129");
                list.add("反应车间主任");
                break;
            case "分离车间副主任":
                list.add("1112111130");
                list.add("分离车间副主任");
                break;
            case "分离车间主任":
                list.add("1112111131");
                list.add("分离车间主任");
                break;
            case "副操":
                list.add("1112111132");
                list.add("副操");
                break;
            case "副主任":
                list.add("1112111133");
                list.add("副主任");
                break;
            case "副总经理":
                list.add("1112111134");
                list.add("副总经理");
                break;
            case "岗位主管":
                list.add("1112111135");
                list.add("岗位主管");
                break;
            case "工程中心副主任":
                list.add("1112111136");
                list.add("工程中心副主任");
                break;
            case "供应副科长":
                list.add("1112111137");
                list.add("供应副科长");
                break;
            case "供应科长":
                list.add("1112111138");
                list.add("供应科长");
                break;
            case "行管部长":
                list.add("1112111139");
                list.add("行管部长");
                break;
            case "化验":
                list.add("1112111140");
                list.add("化验");
                break;
            case "机修":
                list.add("1112111141");
                list.add("机修");
                break;
            case "机修车间主任":
                list.add("1112111142");
                list.add("机修车间主任");
                break;
            case "基建主管":
                list.add("1112111143");
                list.add("基建主管");
                break;
            case "技术副科长":
                list.add("1112111144");
                list.add("技术副科长");
                break;
            case "驾驶员":
                list.add("1112111145");
                list.add("驾驶员");
                break;
            case "经营副部长":
                list.add("1112111146");
                list.add("经营副部长");
                break;
            case "科长":
                list.add("1112111147");
                list.add("科长");
                break;
            case "门卫":
                list.add("1112111148");
                list.add("门卫");
                break;
            case "设备副科长":
                list.add("1112111149");
                list.add("设备副科长");
                break;
            case "实验":
                list.add("1112111150");
                list.add("实验");
                break;
            case "食堂服务员":
                list.add("1112111151");
                list.add("食堂服务员");
                break;
            case "司炉":
                list.add("1112111152");
                list.add("司炉");
                break;
            case "项目部长":
                list.add("1112111153");
                list.add("项目部长");
                break;
            case "仪表":
                list.add("1112111154");
                list.add("仪表");
                break;
            case "营销部长":
                list.add("1112111155");
                list.add("营销部长");
                break;
            case "运管科长":
                list.add("1112111156");
                list.add("运管科长");
                break;
            case "主操":
                list.add("1112111157");
                list.add("主操");
                break;
            case "专业顾问":
                list.add("1112111158");
                list.add("专业顾问");
                break;
            case "总工办主任":
                list.add("1112111159");
                list.add("总工办主任");
                break;
            case "总经理":
                list.add("1112111160");
                list.add("总经理");
                break;
            case "总经理助理":
                list.add("1112111161");
                list.add("总经理助理");
                break;
            case "综合车间主任":
                list.add("1112111162");
                list.add("综合车间主任");
                break;
            case "环境管理":
                list.add("1112111163");
                list.add("环境管理");
                break;
            case "后门":
                list.add("1112111164");
                list.add("后门");
                break;
            default:
                list.add("");
                list.add("");
        }
        return list;
    }

    private List getWorkTypeId(String workTypeName) {
        List list = new ArrayList();
        if (workTypeName != null && !("".equals(workTypeName))) {
            switch (workTypeName) {
                case "电工":
                    list.add("1111111111");
                    list.add("电工");
                    break;
                case "焊接工":
                    list.add("1111111112");
                    list.add("焊接工");
                    break;
                case "驾驶员":
                    list.add("1111111113");
                    list.add("驾驶员");
                    break;
                case "押运员":
                    list.add("1111111114");
                    list.add("押运员");
                    break;
                default:
                    list.add("1111111115");
                    list.add("其他");
            }
        }
        return list;
    }
}
