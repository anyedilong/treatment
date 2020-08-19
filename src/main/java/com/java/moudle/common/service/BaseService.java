package com.java.moudle.common.service;

import java.util.List;

public interface BaseService<T>  {
	public T get(String id) ;

	/**
	 * 查询列表数据
	 * 
	 * @return
	 */
	public List<T> findList() ;

	/**
	 * 查询分页数据
	 * 
	 * @param page
	 *            分页对象
	 * @return
	 */
	//public void findPage(PageModel page);

	/**
	 * 数据是否存在
	 */
	public boolean exists(String id);

	/**
	 * 保存数据（插入或更新）
	 * 
	 * @param entity
	 */
	public void save(T entity);

	/**
	 * 删除数据
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 删除数据
	 * 
	 * @param entity
	 */
	public void delete(T entity);

	
}
