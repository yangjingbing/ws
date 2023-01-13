package com.cp.zf.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cp.util.CheckUtil;
import com.cp.util.Digests;
import com.cp.util.Encodes;
import com.cp.util.ParkHttpClient;
import com.cp.zf.constants.StaticModuleTypeEnum;
import com.cp.zf.entity.Alarm;
import com.cp.zf.entity.TRyJbxx;
import com.cp.zf.entity.TRykJbxx;
import com.cp.zf.mapper.TRykJbxxMapper;
import com.cp.zf.output.DeviceCardOutput;
import com.cp.zf.service.AlarmService;
import com.cp.zf.service.TRyJbxxService;
import com.cp.zf.service.TRykJbxxService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备卡服务实现类
 * </p>
 *
 * @author cpgu
 * @since 2021-01-11
 */
@Service
public class TRykJbxxServiceImpl extends ServiceImpl<TRykJbxxMapper, TRykJbxx> implements TRykJbxxService {

    @Autowired
    private HuaianParkConfig huaianParkConfig;

    @Autowired
    private TRyJbxxService tRyJbxxService;
    @Autowired
    private AlarmService alarmService;

    private ParkHttpClient parkHttpClient = null;

    @PostConstruct
    void init() {
        ParkHttpClient.FickClientConfig fickClientConfig = new ParkHttpClient.FickClientConfig();
        fickClientConfig.setUrl(huaianParkConfig.getStaticUrl());
        parkHttpClient = new ParkHttpClient(fickClientConfig);
    }

    @Override
    public boolean uploadDeviceCard() throws Exception {

        long timestamp = System.currentTimeMillis();
        //header参数
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("appKey", huaianParkConfig.getAppKey());
        headerMap.put("timestamp", String.valueOf(timestamp));
        String sign = getSign(headerMap);
        headerMap.put("sign", sign);


        int totalSize = this.count();
        IPage<TRykJbxx> devicePage = null;
        int currentPage = 0;
        do {

            ++currentPage;
            //一次性最多批量新增10条数据
            devicePage = new Page<>(currentPage, 10, totalSize);
            List<TRykJbxx> deviceDbList = this.page(devicePage).getRecords();
            if (!deviceDbList.isEmpty()) {


                //用户id和姓名map
                Map<Integer, String> userIdWithNameMap = tRyJbxxService.list().stream().collect(Collectors.toMap(TRyJbxx::getId, TRyJbxx::getMc));

                //获取dataList
                List<DeviceCardOutput.Device> dataList = deviceDbList.stream().map(jbxx -> {
                    DeviceCardOutput.Device device = new DeviceCardOutput.Device();
                    device.setEnterpriseId(huaianParkConfig.getOrgCode());
                    device.setDeviceId(String.valueOf(jbxx.getId()));
                    device.setDeviceNo(String.valueOf(jbxx.getSn()));

                    //0无效,1有效,2报警（我们这边0正常，1删除，如果为0，并同时在t_alarm中，那就是预警）
                    device.setDeviceStat(Objects.equals("0", jbxx.getZt()) ? "1" : "0");
                    if (Objects.equals("0", jbxx.getZt()) && CheckUtil.isNotEmpty(alarmService.byCardNo(String.valueOf(jbxx.getSn()))))
                        device.setDeviceStat("2");
                    //关联的姓名
                    device.setPersonName(userIdWithNameMap.getOrDefault(jbxx.getRyid(), ""));

                    device.setPlaceAreaId(huaianParkConfig.getPlaceAreaId());
                    device.setDataId(String.valueOf(jbxx.getId()));
                    return device;
                }).collect(Collectors.toList());

                DeviceCardOutput deviceCardOutput = new DeviceCardOutput();
                deviceCardOutput.setDataList(dataList);
                deviceCardOutput.setEnterpriseId(huaianParkConfig.getOrgCode());
                deviceCardOutput.setServiceCode(StaticModuleTypeEnum.DEVICE_CARD_INFO.getCode());
                //批量新增
                parkHttpClient.doJsonPost("datasync/save", headerMap, null, JSON.toJSONString(deviceCardOutput)).toCompletableFuture().get();

            }
        } while (((Page<TRykJbxx>) devicePage).hasNext());


        return true;
    }

    private String getSign(Map<String, String> headerMap) {
        //把字典按Key的字母顺序排序
        Map<String, String> sortedParams = new TreeMap<String, String>(headerMap);
        Set<Map.Entry<String, String>> paramSet = sortedParams.entrySet();

        // 把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        query.append(huaianParkConfig.getAppSecret());
        for (Map.Entry<String, String> param : paramSet) {
            if (StringUtils.isNotBlank(param.getKey()) && StringUtils.isNotBlank(param.getValue())) {
                query.append(param.getKey()).append(param.getValue());
            }
        }

        // 使用sha1加密,最终生成sign参数值
        query.append(huaianParkConfig.getAppSecret());
        return Encodes.encodeHex(Digests.sha1(query.toString().getBytes())).toUpperCase();
    }
}
