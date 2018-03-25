package com.zhoukp.signer.bean;

/**
 * Created by Administrator on 2018/3/11.
 */

public class SignBean {

    /**
     * code : 100
     * data : {"id":2,"schedule":{"clazz":{"college":{"id":1,"name":"计算机科学与技术学院"},"id":1,"name":"软件工程2班"},"course":{"credits":3,"hours":16,"id":4,"name":"高等代数"},"endAt":"1970-01-01T22:00:00","grade":2014,"id":7,"semester":"二","startAt":"1970-01-01T19:10:00","week":7,"year":"2017-2018"},"state":1,"time":"2018-03-11T20:11:05","user":null}
     * time : 2018-03-11T20:11:05
     * message : 签到成功！
     */

    private int code;
    private DataBean data;
    private String time;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * id : 2
         * schedule : {"clazz":{"college":{"id":1,"name":"计算机科学与技术学院"},"id":1,"name":"软件工程2班"},"course":{"credits":3,"hours":16,"id":4,"name":"高等代数"},"endAt":"1970-01-01T22:00:00","grade":2014,"id":7,"semester":"二","startAt":"1970-01-01T19:10:00","week":7,"year":"2017-2018"}
         * state : 1
         * time : 2018-03-11T20:11:05
         * user : null
         */

        private int id;
        private ScheduleBean schedule;
        private int state;
        private String time;
        private Object user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ScheduleBean getSchedule() {
            return schedule;
        }

        public void setSchedule(ScheduleBean schedule) {
            this.schedule = schedule;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Object getUser() {
            return user;
        }

        public void setUser(Object user) {
            this.user = user;
        }

        public static class ScheduleBean {
            /**
             * clazz : {"college":{"id":1,"name":"计算机科学与技术学院"},"id":1,"name":"软件工程2班"}
             * course : {"credits":3,"hours":16,"id":4,"name":"高等代数"}
             * endAt : 1970-01-01T22:00:00
             * grade : 2014
             * id : 7
             * semester : 二
             * startAt : 1970-01-01T19:10:00
             * week : 7
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
                 * credits : 3.0
                 * hours : 16
                 * id : 4
                 * name : 高等代数
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
}
