package com.cp.zf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cp.zf.entity.TCar;
import com.cp.zf.mapper.TCarMapper;
import com.cp.zf.service.TCarService;
import org.springframework.stereotype.Service;

@Service
public class TCarServiceImpl extends ServiceImpl<TCarMapper, TCar> implements TCarService {

}
