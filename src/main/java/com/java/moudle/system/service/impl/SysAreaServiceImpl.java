package com.java.moudle.system.service.impl;

import com.java.moudle.system.dao.SysAreaDao;
import com.java.moudle.system.domain.SysArea;
import com.java.moudle.system.dto.SysAreaDto;
import com.java.moudle.system.dto.TreeEntity;
import com.java.moudle.system.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@Transactional(readOnly=false)
public class SysAreaServiceImpl implements SysAreaService{

	@Autowired
	SysAreaDao sysAreaDao;
	
	@Override
	public List<SysArea> getLowerAreaInfoById(String id) {
		return sysAreaDao.getLowerAreaInfoById(id);
	}

	@Override
	public List<SysAreaDto> getAreaTree(String areaId) {
		SysArea sysArea = sysAreaDao.getAreaTree(areaId);
		//获取自己与下一级区划
		List<SysAreaDto> getareaList = sysAreaDao.getAreaList(sysArea.getId());
		//只有一条信息直接返回
		if(getareaList.size()==1){
			List<SysAreaDto> arrayList = new ArrayList<>();
			SysAreaDto sysArea2 = getareaList.get(0);
			arrayList.add(sysArea2);
			return arrayList ;
		}
		//进行树结构组装
		SysAreaDto parentArea = getareaList.get(0);
		String id = parentArea.getId();
		List<SysAreaDto> treeList = getTreeList(id,getareaList);
		parentArea.setChildList(treeList);
		List<SysAreaDto> list = new ArrayList<>();
		list.add(parentArea);
		return list;
	}

    @Override
    public List<SysAreaDto> getOneAreaList() {
        return sysAreaDao.getOneAreaList();
    }

    /**
     * 解析树形数据 
     * @param topId 
     * @param entityList 
     * @return 
     * @author 
     * @date
     */  
    public static <E extends TreeEntity<E>> List<E> getTreeList(String topId, List<E> entityList) {  
        List<E> resultList=new ArrayList<>();  
          
        //获取顶层元素集合  
        String parentId;  
        for (E entity : entityList) {  
            parentId=entity.getParentId();  
            if(parentId==null||topId.equals(parentId)){  
                resultList.add(entity);  
            }  
        }  
          
        //获取每个顶层元素的子数据集合  
        for (E entity : resultList) {  
            entity.setChildList(getSubList(entity.getId(),entityList));  
        }  
          
        return resultList;  
    }  
    
    /** 
     * 获取子数据集合 
     * @param id 
     * @param entityList 
     * @return 
     * @author 
     * @date 
     */  
    private  static  <E extends TreeEntity<E>>  List<E> getSubList(String id, List<E> entityList) {  
        List<E> childList=new ArrayList<>();  
        String parentId;  
          
        //子集的直接子对象  
        for (E entity : entityList) {  
            parentId=entity.getParentId();  
            if(id.equals(parentId)){  
                childList.add( entity);  
            }  
        }  
          
        //子集的间接子对象  
        for (E entity : childList) {  
            entity.setChildList(getSubList(entity.getId(), entityList));  
        }  
          
        //递归退出条件  
        if(childList.size()==0){  
            return null;  
        }  
          
        return childList;  
    }

}
