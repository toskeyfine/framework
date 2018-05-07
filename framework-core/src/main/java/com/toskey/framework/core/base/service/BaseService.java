package com.toskey.framework.core.base.service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * BaseService
 * 在mybatisplus提供的serviceImpl基础上增加公共方法
 * @param <M>
 * @param <T>
 */
public abstract class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IService<T> {

}
