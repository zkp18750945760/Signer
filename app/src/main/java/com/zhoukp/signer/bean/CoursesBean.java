package com.zhoukp.signer.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/10.
 */

public class CoursesBean {

    /**
     * code : 100
     * data : [{"clazz":{"college":{"id":1,"name":"计算机科学与技术学院"},"id":1,"name":"软件工程2班"},"course":{"credits":1.5,"hours":18,"id":1,"name":"数据库系统基础教程"},"endAt":"1970-01-01T12:00:00","grade":2014,"id":1,"semester":"二","startAt":"1970-01-01T10:10:00","week":1,"year":"2017-2018"},{"clazz":{"college":{"id":1,"name":"计算机科学与技术学院"},"id":1,"name":"软件工程2班"},"course":{"credits":2,"hours":18,"id":2,"name":"计算机图形学"},"endAt":"1970-01-01T09:50:00","grade":2014,"id":2,"semester":"二","startAt":"1970-01-01T08:00:00","week":1,"year":"2017-2018"}]
     * time : 2018-03-11T19:47:41
     * message : 成功获取课表！
     */

    private int code;
    private String time;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * clazz : {"college":{"id":1,"name":"计算机科学与技术学院"},"id":1,"name":"软件工程2班"}
         * course : {"credits":1.5,"hours":18,"id":1,"name":"数据库系统基础教程"}
         * endAt : 1970-01-01T12:00:00
         * grade : 2014
         * id : 1
         * semester : 二
         * startAt : 1970-01-01T10:10:00
         * week : 1
         * year : 2017-2018
         */

        private ClazzBean clazz;
        private CourseBean course;
        private String endAt;
        private int grade;
        private int id;
        private String semester;
        private String startAt;
        private int week;
        private String year;

        public ClazzBean getClazz() {
            return clazz;
        }

        public void setClazz(ClazzBean clazz) {
            this.clazz = clazz;
        }

        public CourseBean getCourse() {
            return course;
        }

        public void setCourse(CourseBean course) {
            this.course = course;
        }

        public String getEndAt() {
            return endAt;
        }

        public void setEndAt(String endAt) {
            this.endAt = endAt;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSemester() {
            return semester;
        }

        public void setSemester(String semester) {
            this.semester = semester;
        }

        public String getStartAt() {
            return startAt;
        }

        public void setStartAt(String startAt) {
            this.startAt = startAt;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public static class ClazzBean {
            /**
             * college : {"id":1,"name":"计算机科学与技术学院"}
             * id : 1
             * name : 软件工程2班
             */

            private CollegeBean college;
            private int id;
            private String name;

            public CollegeBean getCollege() {
                return college;
            }

            public void setCollege(CollegeBean college) {
                this.college = college;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public static class CollegeBean {
                /**
                 * id : 1
                 * name : 计算机科学与技术学院
                 */

                private int id;
                private String name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

        public static class CourseBean {
            /**
             * credits : 1.5
             * hours : 18
             * id : 1
             * name : 数据库系统基础教程
             */

            private double credits;
            private int hours;
            private int id;
            private String name;

            public double getCredits() {
                return credits;
            }

            public void setCredits(double credits) {
                this.credits = credits;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
