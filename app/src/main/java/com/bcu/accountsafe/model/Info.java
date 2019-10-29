package com.bcu.accountsafe.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Info {

        @PrimaryKey(autoGenerate = true)
        private int id;

        @ColumnInfo(name = "title")
        private String title;

        @ColumnInfo(name = "username")
        private String username;

        @ColumnInfo(name = "password")
        private String password;

        @ColumnInfo(name = "category")
        private String category;

        @ColumnInfo(name = "remark")
        private String remark;

        @ColumnInfo(name = "change_date")
        private String changeDate;

        public String getChangeDate() {
            return changeDate;
        }

        public void setChangeDate(String changeDate) {
            this.changeDate = changeDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

