package com.yuxin.system.bean;

public class BillTypeBean {
    String billTypeId;
    String billTypeName;

    @Override
    public String toString() {
        return "BillTypeBean{" +
                "billTypeId='" + billTypeId + '\'' +
                ", billTypeName='" + billTypeName + '\'' +
                '}';
    }

    public String getBillTypeId() {
        return billTypeId;
    }

    public void setBillTypeId(String billTypeId) {
        this.billTypeId = billTypeId;
    }

    public String getBillTypeName() {
        return billTypeName;
    }

    public void setBillTypeName(String billTypeName) {
        this.billTypeName = billTypeName;
    }
}
