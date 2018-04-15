package com.zhoukp.signer.module.functions.ascq.apply;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/4/12 16:15
 * @email 275557625@qq.com
 * @function
 */
public class ClazzStudentsBean {


    /**
     * data : [{"stuId":"1425122001","stuName":"蔡晓雷","isInvite":false},{"stuId":"1425122002","stuName":"曹巍曦","isInvite":false},{"stuId":"1425122003","stuName":"陈晨","isInvite":false},{"stuId":"1425122004","stuName":"陈虎","isInvite":false},{"stuId":"1425122005","stuName":"陈琦","isInvite":false},{"stuId":"1425122006","stuName":"陈清福","isInvite":false},{"stuId":"1425122007","stuName":"陈天增","isInvite":false},{"stuId":"1425122008","stuName":"陈颖","isInvite":false},{"stuId":"1425122009","stuName":"韩山林","isInvite":false},{"stuId":"1425122010","stuName":"韩世江","isInvite":false},{"stuId":"1425122011","stuName":"郝璐","isInvite":false},{"stuId":"1425122012","stuName":"何凌轲","isInvite":false},{"stuId":"1425122013","stuName":"金鹏","isInvite":false},{"stuId":"1425122014","stuName":"来建培","isInvite":false},{"stuId":"1425122015","stuName":"李敏","isInvite":false},{"stuId":"1425122016","stuName":"李思遥","isInvite":false},{"stuId":"1425122017","stuName":"李想","isInvite":false},{"stuId":"1425122018","stuName":"李忠文","isInvite":false},{"stuId":"1425122019","stuName":"梁富","isInvite":false},{"stuId":"1425122020","stuName":"梁静","isInvite":false},{"stuId":"1425122021","stuName":"刘佳奇","isInvite":false},{"stuId":"1425122022","stuName":"刘名桂","isInvite":false},{"stuId":"1425122023","stuName":"刘炎城","isInvite":false},{"stuId":"1425122024","stuName":"齐旭日","isInvite":false},{"stuId":"1425122025","stuName":"师永昕","isInvite":false},{"stuId":"1425122026","stuName":"史蓉","isInvite":false},{"stuId":"1425122027","stuName":"苏锦川","isInvite":false},{"stuId":"1425122028","stuName":"孙博煜","isInvite":false},{"stuId":"1425122029","stuName":"孙娟","isInvite":false},{"stuId":"1425122030","stuName":"覃仕标","isInvite":false},{"stuId":"1425122031","stuName":"田宇杰","isInvite":false},{"stuId":"1425122032","stuName":"吴承长","isInvite":false},{"stuId":"1425122033","stuName":"吴育淞","isInvite":false},{"stuId":"1425122034","stuName":"吴宗达","isInvite":false},{"stuId":"1425122035","stuName":"徐龙","isInvite":false},{"stuId":"1425122036","stuName":"徐楠","isInvite":false},{"stuId":"1425122037","stuName":"许乃茜","isInvite":false},{"stuId":"1425122038","stuName":"尤俊韬","isInvite":false},{"stuId":"1425122039","stuName":"游成婷","isInvite":false},{"stuId":"1425122040","stuName":"张洋铭","isInvite":false},{"stuId":"1425122041","stuName":"赵涵之","isInvite":false},{"stuId":"1425122042","stuName":"周开平","isInvite":false},{"stuId":"1425122043","stuName":"朱小雨","isInvite":false},{"stuId":"1425122044","stuName":"朱新月","isInvite":false},{"stuId":"1425141018","stuName":"林融杰","isInvite":false},{"stuId":"1425161001","stuName":"蔡婷婷","isInvite":false},{"stuId":"1425161002","stuName":"戴建晖","isInvite":false},{"stuId":"1425161004","stuName":"胡成文浩","isInvite":false},{"stuId":"1425161005","stuName":"黄浪","isInvite":false},{"stuId":"1425161007","stuName":"李奥嘉","isInvite":false},{"stuId":"1425161008","stuName":"李方舆","isInvite":false},{"stuId":"1425161009","stuName":"李文","isInvite":false},{"stuId":"1425161010","stuName":"*林小楷","isInvite":false},{"stuId":"1425161012","stuName":"罗智文","isInvite":false},{"stuId":"1425161013","stuName":"潘丽霖","isInvite":false},{"stuId":"1425161014","stuName":"潘雪姣","isInvite":false},{"stuId":"1425161016","stuName":"田琛","isInvite":false},{"stuId":"1425161017","stuName":"王晨曦","isInvite":false},{"stuId":"1425161018","stuName":"王香香","isInvite":false},{"stuId":"1425161019","stuName":"王紫情","isInvite":false},{"stuId":"1425161020","stuName":"吴志文","isInvite":false},{"stuId":"1425161021","stuName":"姚鈊严","isInvite":false},{"stuId":"1425161022","stuName":"于沫涵","isInvite":false},{"stuId":"1425161023","stuName":"张瑞书","isInvite":false}]
     * time : 2018-04-12 16:54:50
     * status : 200
     */

    private String time;
    private int status;
    /**
     * stuId : 1425122001
     * stuName : 蔡晓雷
     * isInvite : false
     */

    private List<DataBean> data;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String stuId;
        private String stuName;
        private boolean isInvite;

        public String getStuId() {
            return stuId;
        }

        public void setStuId(String stuId) {
            this.stuId = stuId;
        }

        public String getStuName() {
            return stuName;
        }

        public void setStuName(String stuName) {
            this.stuName = stuName;
        }

        public boolean isIsInvite() {
            return isInvite;
        }

        public void setIsInvite(boolean isInvite) {
            this.isInvite = isInvite;
        }
    }
}
