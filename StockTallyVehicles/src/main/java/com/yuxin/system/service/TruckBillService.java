package com.yuxin.system.service;

import java.util.HashMap;
import java.util.List;

public interface TruckBillService {
    public List queryTruckBillOnFirstWeigh(HashMap queryInfoMap);
    public List queryAllBillType(HashMap queryInfoMap);
    public List queryMaterialName(HashMap queryInfoMap);
    public List queryTruckStatus(HashMap queryInfoMap);
    public List queryTruckBillByCondition(HashMap queryInfoMap);
    public int checkTruckBillByCondition(HashMap queryInfoMap);
    public List queryTruckBillByConditionAndPageCount(HashMap queryInfoMap);
}
