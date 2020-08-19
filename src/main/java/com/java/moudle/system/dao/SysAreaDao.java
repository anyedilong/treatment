package com.java.moudle.system.dao;

import com.java.moudle.common.domain.InitDictDto;
import com.java.moudle.system.dao.repository.SysAreaRepository;
import com.java.moudle.system.domain.SysArea;
import com.java.moudle.system.dto.SysAreaDto;
import com.java.until.dba.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
public class SysAreaDao extends BaseDao<SysAreaRepository, SysArea> {
	@Autowired
	private SysAreaRepository sysAreaRepository;

	public List<SysArea> getLowerAreaInfoById(String id) {
		String sql = "select * from SYS_AREA where parent_id = to_char(:parentId) and DELETE_FLG = '0'";
		Map<String, String> param = new HashMap<String, String>();
		param.put("parentId", id);
		return queryList(sql, param, SysArea.class);
	}
	public List<SysAreaDto> getAreaList(String id) {
		String sql =" select * from sys_area start with id=:id connect by prior id=parent_id and level<3 and delete_flg = '0' ";
		HashMap<String, String> map = new HashMap<>();
		map.put("id", id);
		return queryList(sql, map, SysAreaDto.class);
	}
	
	public SysArea getAreaTree(String areaId) {
		return sysAreaRepository.getOne(areaId);
	}

	public List<InitDictDto> queryAreaDictList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select 'areadict' as parentCode, a.area_name as name, a.id as code ");
		sql.append(" from sys_area a ");
		sql.append(" where a.delete_flg = '0' ");
		return queryList(sql.toString(), null, InitDictDto.class);
	}
	
	/**
	 * @Title : 初始地区
	 * @Description :
	 * @author : 皮雪平
	 * @date : 2020/1/9 13:28
	 * @param :
	 * @return :
	 * @throws
	 */
	public List<SysAreaDto> getOneAreaList() {
		String sql =" select * from sys_area where area_level = '1' and DELETE_FLG = '0'  ";
		return queryList(sql, null, SysAreaDto.class);
	}
}
