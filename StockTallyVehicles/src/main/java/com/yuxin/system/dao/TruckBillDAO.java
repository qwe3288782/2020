package com.yuxin.system.dao;

import java.util.HashMap;
import java.util.List;

public interface TruckBillDAO {
    public List queryTruckBillOnFirstWeigh(HashMap<String, String> queryInfoMap);
    public List queryAllBillType(HashMap<String,String> queryInfoMap);
    public List queryMaterialName(HashMap<String,String> queryInfoMap);
    public List queryTruckStatus(HashMap<String,String> queryInfoMap);
    public List queryTruckBillByCondition(HashMap<String,String> queryInfoMap);
    public int checkTruckBillByCondition(HashMap<String,String> queryInfoMap);
    public List queryTruckBillByConditionAndPageCount(HashMap<String,String> queryInfoMap);
}
