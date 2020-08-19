package com.java.moudle.system.dao;

import java.util.List;

import javax.inject.Named;

import com.java.moudle.common.domain.InitDictDto;
import com.java.moudle.system.dao.repository.SysDictRepository;
import com.java.moudle.system.domain.SysDict;
import com.java.until.dba.BaseDao;

@Named
public class SysDictDao extends BaseDao<SysDictRepository, SysDict> {

	public List<InitDictDto> queryDictList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id as id, (select b.code from tm_dict b where a.parent_id = b.id) as parentCode, a.name, a.code ");
		sql.append(" from tm_dict a ");
		sql.append(" where a.delete_flg = '0' ");
		return queryList(sql.toString(), null, InitDictDto.class);
	}

}