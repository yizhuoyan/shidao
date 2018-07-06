package com.yizhuoyan.shidao.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by Administrator on 2017/10/27.
 */
public class Tree{
public static final <V extends Node, T> List<T> of(List<V> list, Function<V,T> transfer, AppendChild<T> appendChild){
  List<T> roots = new ArrayList<>();

  Map<Serializable,T> map = new HashMap<>(list.size());
  Serializable id = null, parentId = null;
  T node = null, parentNode = null;
  for(V m : list){
    id = m.getId();
    parentId = m.getParentId();
    node = transfer.apply(m);
    map.put(id, node);
    if(parentId==null){
      roots.add(node);
    }
  }
  for(V m : list){
    id = m.getId();
    parentId = m.getParentId();
    if(parentId!=null){
      if((parentNode = map.get(parentId))!=null){
        appendChild.apply(parentNode, map.get(id));
      }
    }
  }
  return roots;
}

public interface Node<ID extends Serializable>{
  ID getId();

  ID getParentId();
}

@FunctionalInterface
public interface AppendChild<T>{
  void apply(T parent, T child);
}


}

