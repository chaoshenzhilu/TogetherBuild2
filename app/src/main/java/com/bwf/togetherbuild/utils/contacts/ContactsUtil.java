package com.bwf.togetherbuild.utils.contacts;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fanhy on 2016/11/29 0029.
 */

public class ContactsUtil {
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HashMap map = null;// 两个键值对: call-CallBack   data-List<Contacts>
            if(msg.obj instanceof List){// obj中携带一个List里面存了一个HashMap
                List list = (List) msg.obj;
                map = (HashMap) list.get(0);
            }
            if(map == null){
                return;
            }
            CallBack callBack = (CallBack) map.get("call");
            switch (msg.what){
                case 0:// 成功
                    List<Contacts> contactList = (List<Contacts>) map.get("data");
                    callBack.onSuccess(contactList);
                    break;
                case 1:// 失败
                    Throwable throwable = (Throwable) map.get("data");
                    callBack.onFailure(throwable);
                    break;
            }
        }
    };

    public interface CallBack{
        void onSuccess(List<Contacts> result);
        void onFailure(Throwable throwable);
    }
    public void getAllContacts(Context context, CallBack callBack){
        ReadContactsRunn run = new ReadContactsRunn(callBack, context);
        Thread th = new Thread(run);
        th.start();
    }

    class ReadContactsRunn implements Runnable{
        CallBack callBack;
        Context context;
        ProgressDialog dialog;

        public ReadContactsRunn(CallBack callBack, Context context) {
            this.callBack = callBack;
            this.context = context;
        }

        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog = new ProgressDialog(context);
                    dialog.setTitle("读取联系人");
                    dialog.setMessage("正在导入系统联系人,请耐心等待~");
                    dialog.show();
                }
            });
            Message msg = Message.obtain();
            ArrayList list = new ArrayList();
            HashMap map = new HashMap();
            map.put("call", callBack);
            try {
                List<Contacts> contactsList = testGetAllContact(context);
                map.put("data", contactsList);
                msg.what = 0;
            } catch (Throwable throwable) {
                map.put("data", throwable);
                msg.what = 1;
            } finally {
                list.add(map);
                msg.obj = list;
                handler.sendMessage(msg);
                dialog.dismiss();
            }
        }
    }

    private List<Contacts> testGetAllContact(Context context) throws Throwable
    {
        //获取联系人信息的Uri
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //获取ContentResolver
        ContentResolver contentResolver = context.getContentResolver();
        //查询数据，返回Cursor
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        List<Contacts> list = new ArrayList<Contacts>();
        while(cursor.moveToNext())
        {
            String displayName = "";                        // 姓名
            String companyName = "";                        // 公司或组织名
            String companyTitle = "";                       // 岗位或职位
            List<String> phoneList = new ArrayList<>();     // 电话列表
            List<String> emailList = new ArrayList<>();     // 邮箱列表
            List<String> addressList = new ArrayList<>();   // 地址列表

            Map<String,Object> map = new HashMap<String,Object>();
            StringBuilder sb = new StringBuilder();
            //获取联系人的ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人的姓名
            displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));//联系人ID


            //查询电话类型的数据操作
            Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
                    null, null);
            while(phones.moveToNext())
            {
                String phoneNumber = phones.getString(phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                //添加Phone的信息
                phoneList.add(phoneNumber);
            }
            phones.close();


            //查询Email类型的数据操作
            Cursor emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,
                    null, null);
            while (emails.moveToNext())
            {
                String emailAddress = emails.getString(emails.getColumnIndex(
                        ContactsContract.CommonDataKinds.Email.DATA));
                //添加Email的信息
                emailList.add(emailAddress);
            }
            emails.close();

            //查询==地址==类型的数据操作.StructuredPostal.TYPE_WORK
            Cursor address = contentResolver.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = " + contactId,
                    null, null);
            while (address.moveToNext())
            {
                String workAddress = address.getString(address.getColumnIndex(
                        ContactsContract.CommonDataKinds.StructuredPostal.DATA));


                //添加地址的信息
                addressList.add(workAddress);
            }
            address.close();

            //查询==公司名字==类型的数据操作.Organization.COMPANY  ContactsContract.Data.CONTENT_URI
            String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
            String[] orgWhereParams = new String[]{id,
                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
            Cursor orgCur = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                    null, orgWhere, orgWhereParams, null);
            if (orgCur.moveToFirst()) {
                //组织名 (公司名字)
                companyName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                //职位
                companyTitle = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
            }

            Contacts contacts = new Contacts(displayName, phoneList, emailList, companyName, companyTitle,addressList);
            orgCur.close();
            list.add(contacts);
        }

        cursor.close();

        return list;
    }
}
