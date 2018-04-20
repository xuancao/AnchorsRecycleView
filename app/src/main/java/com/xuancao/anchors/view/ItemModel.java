package com.xuancao.anchors.view;

import java.io.Serializable;

public class ItemModel implements Serializable {

    public String id;
    public String name;
    public String imgUrl;
    public boolean isSelected; //true 选中（在header头部view中有此item）展示减号，false 未选中展示加号

    /** 是否为占位符 最后一个空的item*/
    public boolean isPlaceholder; //true 为占位符，false不是占位符
}
