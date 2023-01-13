package com.cp.zf.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cp.util.*;
import com.cp.zf.constants.StaticModuleTypeEnum;
import com.cp.zf.entity.THisBackup;
import com.cp.zf.entity.TRyJbxx;
import com.cp.zf.entity.TRykJbxx;
import com.cp.zf.mapper.THisBackupMapper;
import com.cp.zf.output.DeviceCardOutput;
import com.cp.zf.output.DevicePositionOutput;
import com.cp.zf.service.AlarmService;
import com.cp.zf.service.THisBackupService;
import com.cp.zf.service.TRyJbxxService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 实时位置服务实现类
 * </p>
 *
 * @since 2021-01-11
 */
@Service
public class THisBackupServiceImpl extends ServiceImpl<THisBackupMapper, THisBackup> implements THisBackupService {

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
    public void uploadPosition() throws Exception {
        long timestamp = System.currentTimeMillis();
        //header参数
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("appKey", huaianParkConfig.getAppKey());
        headerMap.put("timestamp", String.valueOf(timestamp));
        String sign = getSign(headerMap);
        headerMap.put("sign", sign);

        //三分钟之内
        QueryWrapper<THisBackup> backupQueryWrapper = new QueryWrapper<>();
        backupQueryWrapper.ge("dt", LocalDateTime.now().minusMinutes(3));
        int totalSize = this.count(backupQueryWrapper);
        IPage<THisBackup> devicePage = null;
        int currentPage = 0;
        do {

            ++currentPage;
            //一次性最多批量新增10条数据
            devicePage = new Page<>(currentPage, 10, totalSize);
            List<THisBackup> backUpDbList = this.page(devicePage, backupQueryWrapper).getRecords();
            if (!backUpDbList.isEmpty()) {

                //获取dataList
                List<DevicePositionOutput> dataList = backUpDbList.stream().map(backUp -> {
                    DevicePositionOutput devicePositionOutput = new DevicePositionOutput();
                    devicePositionOutput.setEnterpriseId(huaianParkConfig.getOrgCode());
                    devicePositionOutput.setDeviceNo(String.valueOf(backUp.getCardSn()));
                    //0无效,1有效,2报警（我们这边0正常，1删除，如果为0，并同时在t_alarm中，那就是预警）
                    devicePositionOutput.setDeviceStat("1");
                    devicePositionOutput.setTimestampMillisecond(backUp.getDt().toInstant(ZoneOffset.of("+8")).toEpochMilli());
                    double[] arr = ConvertUtil.xy2lonlat(backUp.getXx(), backUp.getYy());
                    devicePositionOutput.setLongitude(arr[0]);
                    devicePositionOutput.setLatitude(arr[1]);
                    return devicePositionOutput;
                }).collect(Collectors.toList());

                Map<String, Object> devicePositionOutput = new HashMap<>();
                devicePositionOutput.put("dataList", dataList);
                devicePositionOutput.put("enterpriseId", huaianParkConfig.getOrgCode());
                parkHttpClient.doJsonPost("datasync/personLoc/reportRealData", headerMap, null, JSON.toJSONString(devicePositionOutput)).toCompletableFuture().get();

            }
        } while (((Page<THisBackup>) devicePage).hasNext());


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
