package com.java.moudle.system.dto;

import java.util.List;

public interface TreeEntity<E> {

    public String getId();  
    public String getParentId();  
    public void setChildList(List<E> childList);  
    
    
}
