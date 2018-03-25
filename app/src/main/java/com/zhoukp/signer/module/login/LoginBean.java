package com.zhoukp.signer.module.login;

/**
 * @author zhoukp
 * @time 2018/3/18 13:20
 * @email 275557625@qq.com
 * @function
 */

public class LoginBean {

    /**
     * time : 2018-03-18 13:23:51
     * user : {"userPassword":"bsZsfaFzPXMh8nPVCBWT4A==","userClass":"2班","userGrade":"2014级","userDuty":"学生","userMajor":"软件工程","userName":"何凌轲","userId":"1425122012"}
     * status : 200
     */

    private String time;
    /**
     * userPassword : bsZsfaFzPXMh8nPVCBWT4A==
     * userClass : 2班
     * userGrade : 2014级
     * userDuty : 学生
     * userMajor : 软件工程
     * userName : 何凌轲
     * userId : 1425122012
     */

    private UserBean user;
    private int status;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class UserBean {
        private String userPassword;
        private String userClass;
        private String userGrade;
        private String userDuty;
        private String userMajor;
        private String userName;
        private String userId;

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getUserClass() {
            return userClass;
        }

        public void setUserClass(String userClass) {
            this.userClass = userClass;
        }

        public String getUserGrade() {
            return userGrade;
        }

        public void setUserGrade(String userGrade) {
            this.userGrade = userGrade;
        }

        public String getUserDuty() {
            return userDuty;
        }

        public void setUserDuty(String userDuty) {
            this.userDuty = userDuty;
        }

        public String getUserMajor() {
            return userMajor;
        }

        public void setUserMajor(String userMajor) {
            this.userMajor = userMajor;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
