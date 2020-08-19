package com.java.moudle.common.service.impl;

import com.java.until.StringUtils;
import com.java.until.UUIDUtil;
import com.java.until.dba.BaseDao;
import com.java.until.dba.BaseDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 */
@Transactional(readOnly = true)
public abstract class BaseServiceImpl<R extends BaseDao, T extends BaseDomain> {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 持久层对象
	 */
	@Inject
	protected R dao;

	/**
	 * 获取单条数据
	 * 
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return (T) dao.get(id);
	}

	/**
	 * 查询列表数据
	 * 
	 * @return
	 */
	public List<T> findList() {
		return dao.findList();
	}
	

	/**
	 * 保存数据（插入或更新）
	 * 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (StringUtils.isNull(entity.getId())) {
			entity.setId(UUIDUtil.getUUID());
		}
		dao.save(entity);
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		dao.delete(id);
	}

	/**
	 * 删除数据
	 * 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		dao.delete(entity);
	}

	/**
	 * 数据是否存在
	 * 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public boolean exists(String id) {
		BaseDomain optional = dao.get(id);
		if (optional != null)
			return true;
		return false;
	}
	
}
