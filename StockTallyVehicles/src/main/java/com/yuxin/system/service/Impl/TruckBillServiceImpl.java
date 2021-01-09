package com.yuxin.system.service.Impl;

import com.yuxin.system.bean.BillTypeBean;
import com.yuxin.system.dao.TruckBillDAO;
import com.yuxin.system.dao.Impl.TruckBillDAOImpl;
import com.yuxin.system.service.TruckBillService;

import java.util.HashMap;
import java.util.List;

public class TruckBillServiceImpl implements TruckBillService {
    @Override
    public List queryTruckBillOnFirstWeigh(HashMap queryInfoMap) {
        TruckBillDAO truckBillDAO = new TruckBillDAOImpl();
        return truckBillDAO.queryTruckBillOnFirstWeigh(queryInfoMap);
    }
    @Override
    public List queryAllBillType(HashMap queryInfoMap){
        TruckBillDAO truckBillDAO = new TruckBillDAOImpl();
        return truckBillDAO.queryAllBillType(queryInfoMap);
    }
    @Override
    public List queryMaterialName(HashMap queryInfoMap){
        TruckBillDAO truckBillDAO = new TruckBillDAOImpl();
        return truckBillDAO.queryMaterialName(queryInfoMap);
    }
    @Override
    public List queryTruckStatus(HashMap queryInfoMap){
        TruckBillDAO truckBillDAO = new TruckBillDAOImpl();
        return truckBillDAO.queryTruckStatus(queryInfoMap);
    }
    @Override
    public List queryTruckBillByCondition(HashMap queryInfoMap){
        TruckBillDAO truckBillDAO = new TruckBillDAOImpl();
        return truckBillDAO.queryTruckBillByCondition(queryInfoMap);
    }
    @Override
    public int checkTruckBillByCondition(HashMap queryInfoMap){
        TruckBillDAO truckBillDAO = new TruckBillDAOImpl();
        return truckBillDAO.checkTruckBillByCondition(queryInfoMap);
    }
    @Override
    public List queryTruckBillByConditionAndPageCount(HashMap queryInfoMap){
        TruckBillDAO truckBillDAO = new TruckBillDAOImpl();
        return truckBillDAO.queryTruckBillByConditionAndPageCount(queryInfoMap);
    }
}
