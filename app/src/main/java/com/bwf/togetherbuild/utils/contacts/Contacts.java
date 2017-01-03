package com.bwf.togetherbuild.utils.contacts;

import java.util.List;

/**
 * Created by Fanhy on 2016/11/29 0029.
 */

public class Contacts {
    private String displayName;                     // 姓名
    private List<String> phoneList;                 // 电话
    private List<String> emailList;                 // 邮箱电话
    private String companyName;                     // 公司或组织名
    private String companyTitle;                    // 岗位或职位
    private List<String> addressList;               // 地址

    public Contacts() {
    }

    public Contacts(String displayName, List<String> phoneList, List<String> emailList, String companyName, String companyTitle, List<String> addressList) {
        this.displayName = displayName;
        this.phoneList = phoneList;
        this.emailList = emailList;
        this.companyName = companyName;
        this.companyTitle = companyTitle;
        this.addressList = addressList;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyTitle() {
        return companyTitle;
    }

    public void setCompanyTitle(String companyTitle) {
        this.companyTitle = companyTitle;
    }

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "displayName='" + displayName + '\'' +
                ", phoneList=" + phoneList +
                ", emailList=" + emailList +
                ", companyName='" + companyName + '\'' +
                ", companyTitle='" + companyTitle + '\'' +
                ", addressList=" + addressList +
                '}';
    }
}
