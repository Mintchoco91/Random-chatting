package com.example.random_chatting.userList;

import java.util.List;

public class UserListDTO {
    public static class outputDTO {
        private String id;
        private String userName;
        private String gender;
        private String age;
        private String phoneNumber;
        private List<String> fileNameList;
        private String countIdx;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public List<String> getFileNameList() {
            return fileNameList;
        }

        public void setFileNameList(List<String> fileNameList) {
            this.fileNameList = fileNameList;
        }

        public String getCountIdx() {
            return countIdx;
        }

        public void setCountIdx(String countIdx) {
            this.countIdx = countIdx;
        }
    }
}
