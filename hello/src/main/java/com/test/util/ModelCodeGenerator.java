package com.test.util;

import org.apache.ibatis.jdbc.SQL;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 实体类代码生成器
 */
public class ModelCodeGenerator {


    private String packageOutPath = "com.test.entity";//指定实体类生成所在包的路径

    private String authorName = "yjb";//作者名字

    private String tableName = "T_car_scj";//表名

    private String modelName = "TCarScj";//实体类名

    private String[] colnames;//列名数组

    private String[] colTypes;//列名类型数组

    private String version = "V1.0";//版本

    private int[] colSize;//列名大小数组

    private boolean util = false;//是否导入java.util.*

    private boolean f_sql = false;//是否道路java.sql.*

    private boolean f_lang = false;//是否导入java.sql.*

    private String defaultPath = "/src/main/java/";

    //数据库连接
    private static final String URL = "jdbc:mysql://localhost:3306/zf?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC&useSSL=false";
    private static final String NAME = "root";
    private static final String PASS = "123456";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    /**
     * 构造函数
     */
    public ModelCodeGenerator() {
        //创建连接
        Connection conn;
        //查看要生成的实体类
        String sql = "select * from "+tableName;
        PreparedStatement pst = null;
        try {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            conn = DriverManager.getConnection(URL,NAME,PASS);
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsmd = pst.getMetaData();
            // 统计列
            int size = rsmd.getColumnCount();
            colnames = new String[size];
            colTypes = new String[size];
            colSize = new int[size];
            for (int i = 0; i < size; i++) {
                colnames[i] = UnderLineToHump(rsmd.getColumnName(i+1).toLowerCase());
                colTypes[i] = rsmd.getColumnTypeName(i+1);
                // 自动生成包配置
                // if(colTypes[i].equalsIgnoreCase(datetime)){
                // f_util = true;
                // }
                if(colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")
                || colTypes[i].equalsIgnoreCase("datetime") || colTypes[i].equalsIgnoreCase("time")
                ||colTypes[i].equalsIgnoreCase("date") ||colTypes[i].equalsIgnoreCase("datetime2")){
                    f_sql = true;
                }
                // if(colTypes[i].equalsIgnoreCase("int")){
                // f_lang = true;
                // }
                colSize[i] = rsmd.getColumnDisplaySize(i+1);
            }
            try {
                String content = parse(colnames,colTypes,colSize);
                File directory = new File("");
                String path = this.getClass().getResource("").getPath();
                System.out.println(path);
                String outputPath = directory.getAbsolutePath()+this.defaultPath+this.packageOutPath.replace(".","/")+
                        "/"+initcap(modelName)+".java";
                System.out.println("执行完毕，生成路径为："+outputPath);
                FileWriter fw = new FileWriter(outputPath);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(content);
                pw.flush();
                pw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 出口
     * @param args
     */
    public static void main(String[] args) {
        new ModelCodeGenerator();
    }

    /***
     * 下划线命名为驼峰命名
     * @param para
     * 下划线命名的字符串
     */
    public static String UnderLineToHump(String para) {
        StringBuilder sb = new StringBuilder();
        String a[] = para.split("_");
        for (String s : a) {
            if(!para.contains("_")) {
                sb.append(s);
                continue;
            }
            if(sb.length()==0){
                sb.append(s.toLowerCase());
            }else {
                sb.append(s.substring(0,1).toUpperCase());
                sb.append(s.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
    /***
     * 驼峰命名转下划线命名
     * @param para
     * 驼峰命名的字符串
     */
    public static String HumpToUnderLine(String para) {
        StringBuilder sb = new StringBuilder();
        int tmp = 0;
        if(!para.contains("_")){
            for (int i = 0; i < para.length(); i++) {
                if(Character.isUpperCase(para.charAt(i))){
                    sb.insert(i+tmp,"_");
                    tmp += 1;
                }
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 将输入字符串的首字母改成大写
     * @param str
     * @return
     */
    private String initcap(String str){
        char[] ch = str.toCharArray();
        if(ch[0] >= 'a' && ch[0] <= 'z'){
           ch[0] =(char) (ch[0] -32);
        }
        return new String(ch);
    }

    /**
     * 生成实体类主类代码
     * @param colnames
     * @param colTypes
     * @param colSize
     * @return
     */
    private String parse(String[] colnames,String[] colTypes,int[] colSize) {
        StringBuilder sb = new StringBuilder();
        //生成package包路径
        sb.append("package\r"+this.packageOutPath+";\r\n");
        //判断是否导入工具包
        if(util){
            sb.append("import java.util.Date;\r\n");
        }
        if(f_sql){
            sb.append("import java.sql.*;\r\n");
        }
        if(f_lang){
            sb.append("import java.lang.*;\r\n");
        }
        sb.append("\r\n");
        //注释部分
        sb.append("  /**\r\n");
        sb.append("   *@文件名称："+this.modelName+".java\r\n");
        sb.append("   *@创建时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\r\n");
        sb.append("   * @创  建  人：" + this.authorName + " \r\n");
        sb.append("   * @文件描述：" + modelName + " 实体类\r\n");
        sb.append("   * @文件版本：" + this.version + " \r\n");
        sb.append("   */ \r\n");
        //实体部分
        sb.append("\r\n\r\npublic class " + initcap(modelName) + "{\r\n");
        processAllAttrs(sb);//属性
        processAllMethod(sb);//get set方法
        sb.append("}\r\n");
        return sb.toString();
    }

    /**
     * 生成所有方法
     * @param sb
     */
    private void processAllMethod(StringBuilder sb) {
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tpublic void set" + initcap(colnames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " ");
        }
    }

    /**
     * 生成所有属性
     * @param sb
     */
    private void processAllAttrs(StringBuilder sb) {
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tprivate "+sqlType2JavaType(colTypes[i])+" "+colnames[i]+";\r\n");
        }
    }

    /**
     * 功能：获得列的数据类型
     * @param sqlType
     * @return
     */
    private String sqlType2JavaType(String sqlType) {
        if(sqlType.equalsIgnoreCase("binary double")){
            return "double ";
        }else if(sqlType.equalsIgnoreCase("binary float")){
            return "float ";
        }else if(sqlType.equalsIgnoreCase("blob")){
            return "byte[] ";
        }else if (sqlType.equalsIgnoreCase("char")
        || sqlType.equalsIgnoreCase("nvarchar2")
        || sqlType.equalsIgnoreCase("varchar")){
            return "String ";
        }else if(sqlType.equalsIgnoreCase("date")
        || sqlType.equalsIgnoreCase("timestamp")
        || sqlType.equalsIgnoreCase("timestamp with local time zone")
        || sqlType.equalsIgnoreCase("timestamp with time zone")){
            return "Date ";
        }else if(sqlType.equalsIgnoreCase("number")){
            return "Double ";
        }
        return "String ";
    }

}
