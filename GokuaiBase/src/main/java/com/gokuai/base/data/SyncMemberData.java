package com.gokuai.base.data;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Brandon on 2014/8/8.
 */

//同步成员和组织架构
//
//SyncMemberData.Person person = new SyncMemberData.Person(0, "认证帐号", "显示名称", "邮箱", "联系电话");
//        ArrayList<Object> list = new ArrayList<Object>();
//        list.add(person);//添加成员
//        list.add(person);//添加成员
//        list.add(person);//添加成员
//        SyncMemberData.Group group = new SyncMemberData.Group(0, "分组名称", list);
//        ArrayList<Object> list2 = new ArrayList<Object>();
//        list2.add(group);//添加组
//        list2.add(person);//添加成员
//        SyncMemberData.Group group2 = new SyncMemberData.Group(0, "分组名称", list2);
//
//        deserializeReturn(mEntManger.syncMembers(new SyncMemberData(person, group2).toJsonString()));

public class SyncMemberData {

    private Person person;
    private Group group;

    public SyncMemberData(Person dataObject, Group group) {
        this.person = dataObject;
        this.group = group;
    }

    public static class Person {
        private int pid;
        private String account;
        private String name;
        private String email;
        private String phone;

        public Person(int pid, String account, String name, String email, String phone) {
            this.pid = pid;
            this.account = account;
            this.name = name;
            this.email = email;
            this.phone = phone;
        }
    }

    public static class Group {
        private int gid;
        private String name;
        ArrayList<Object> children = new ArrayList<Object>();

        public Group(int gid, String name, ArrayList<Object> children) {
            this.gid = gid;
            this.name = name;
            this.children = children;
        }

        public void addChildren(Object o) {
            this.children.add(o);
        }


    }

    public String toJsonString() {
        Gson g = new Gson();
        return g.toJson(this);
    }

}
