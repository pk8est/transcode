/**
 * 
 */
package com.pkest.netty2.util;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 *  字符串的工具类
 * @author coolcooldee
 *
 */
public class StringUtil {
    
    private static DecimalFormat df2 = new DecimalFormat("0.00");
    public static String format2Show(int i){
        return df2.format(i / 1024.0);
    }

    /**
     * 该方法是trim一个字符串，返回trim后的字符串
     * @param s String
     * @return 如果传入一个null，则返回一个null，如果是字符串，则返回trim后的字符串
     */
    public static String trim(String s) {
        return s == null ? null : s.trim();
    }

    public static boolean isEmpty(String s) {
        if (s == null || s.trim().length() == 0 || "null".equals(s))
            return true;
        else
            return false;
    }
    
    public static boolean hasOneEmpty(String...strings) {
        for(String str : strings){
            if(isEmpty(str))
                return true;
        }
        return false ;
    }
    
    public static boolean isAllEmpty(String...strings){
        for(String str : strings){
            if(!isEmpty(str)){
                return false;
            }
        }
        return true;
    }

    /**
     * 获取36位长度的UUID字符串
     * @return
     */
    public static String getUUID36() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取32位长度的UUID字符串
     * @return
     */
    public static String getUUID32() {
        return getUUID36().replaceAll("-", "");
    }

    /**
    * 方法用途和描述: 判断字符是否为数字
    * @param c
    * @return
    */
    public static boolean isNumber(char c) {
        return (c > 48 && c < 57);
    }
    
    /**
     * 判断一个字符串是否是数字
     * @param numStr
     * @return
     */
    public static boolean isNumber(String numStr) {
        if (numStr == null)
            return false;
        Pattern pattern = Pattern.compile("(-?)[0-9]+");
        return pattern.matcher(numStr).matches();
    }

    /**
    * 方法用途和描述: 判断字符是否为字母
    * @param c
    * @return
    */
    public static boolean isLetter(char c) {
        return (c > 65 && c < 90) || (c > 97 && c < 122);
    }

    /**
     * 把一些html标签转成转义字符（比如把"<"转成"&lt;"）
     * @param html
     * @return
     */
    public static String htmlToWeb(String html) {
        if (html == null || html.length() == 0)
            return "";
        char[] c = html.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < c.length; i++) {
            switch (c[i]) {
            case '>':
                sb.append("&gt;");
                break;
            case '<':
                sb.append("&lt;");
                break;
            case '&':
                sb.append("&amp;");
                break;
            default:
                sb.append(c[i]);
            }
        }
        return sb.toString();
    }
    
    public static String clearHTML(String str) {
        if (str == null)
            return "";
        //str = str.replaceAll(";", "");
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("'", "");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("chr(60)", "&lt;");
        str = str.replaceAll("chr(37)", "&gt;");
        str = str.replaceAll("\"", "&quot;");
        str = str.replaceAll("\n", "<br/>");
        //str = str.replaceAll(" ", "&nbsp;");
        return str;
    }
    
    /**
     * 解析出文件名
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        if (path == null) {
            return null;
        }
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }
    /**
     * 解析出扩展名
     * @param path
     * @return
     */
    public static String getFileNameExtension(String path) {
        if (path == null) {
            return null;
        }
        int extIndex = path.lastIndexOf(".");
        if (extIndex == -1) {
            return null;
        }
        int folderIndex = path.lastIndexOf("/");
        if (folderIndex > extIndex) {
            return null;
        }
        return path.substring(extIndex + 1);
    }
    /**
     * 解析出图片名
     * @param path
     * @return
     */
    public static String getPicName(String path) {
        
        String ex = getFileNameExtension(path);
        if(isPicExtension(ex))
            return getFileName(path);
        else
            return "";
    }
    
    public static boolean isPicExtension(String exName){
        //exName = exName.toLowerCase();
        return "gif".equalsIgnoreCase(exName) || "jpg".equalsIgnoreCase(exName) || "jpeg".equalsIgnoreCase(exName) || "png".equalsIgnoreCase(exName);
    }
    
    public static void main(String[] args) {
        //System.out.println(isAllEmpty("","   ",null));
        String html = "<p>&nbsp;<span style\"color: rgb(85, 85, 85); font-family: tahoma, sans-serif; font-size: 14px; line-height: 24px;\">【</span><a href=\"http://newgame.duowan.com/\" target=\"_blank\" style=\"font-family: tahoma, sans-serif; font-size: 14px; line-height: 24px; color: rgb(43, 43, 43); outline: 0px; text-decoration: none;\">多玩</a><span style=\"color: rgb(85, 85, 85); font-family: tahoma, sans-serif; font-size: 14px; line-height: 24px;\">新游戏频道】"+
        "2013年11月25日-12月1日，本周共有10款游戏开测，其中U7平台运营的《罗德岛战记》1款游戏开启首测。此外，已经经历过四次测试的《</span><a href=\"http://bns.duowan.com/\" target=\"_blank\" style=\"font-family: tahoma, sans-serif; font-size: 14px; line-height: 24px; color: rgb(43, 43, 43); outline: 0px; text-decoration: none;\">剑灵</a><span style=\"color: rgb(85, 85, 85); font-family: tahoma, sans-serif; font-size: 14px; line-height: 24px;\">》将在本周四第五次测试中&ldquo;开放注册&rdquo;，"+
        "不需要激活码就可以进入游戏体验。更多游戏详情请继续翻页查看游戏简介。</span></p>" + 
        "<p style=\"margin: 20px 0px; padding: 0px; color: rgb(85, 85, 85); font-family: tahoma, sans-serif; font-size: 14px; line-height: 24px;\">　　详细每日测试内容请点击导航查看。【<a href=\"http://newgame.duowan.com/\" target=\"_blank\" style=\"color: rgb(43, 43, 43); outline: 0px; text-decoration: none;\">多玩</a>新游戏频道】2013年11月25日-12月1日，本周共有10款游戏开测，其中U7平台运营的《罗德岛战记》1款游戏开启首测。此外，已经经历过四次测试的《<a href=\"http://bns.duowan.com/\" target=\"_blank\" style=\"color: rgb(43, 43, 43); outline: 0px; text-decoration: none;\">剑灵</a>》"+
        "将在本周四第五次测试中&ldquo;开放注册&rdquo;，不需要激活码就可以进入游戏体验。更多游戏详情请继续翻页查看游戏简介。</p>";
        int start = html.indexOf("<p>");
        int end = html.indexOf("</p>");
        String _content = html.substring(start + 3, end);
        _content = clearHTMLTags(_content).trim();
        System.out.println(_content);
    }
    
  //得到二十位随机字符串
    public static String getRandomStr(int length){
        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i ++)
        { 
            sb.append(buffer.charAt(r.nextInt(range))); 
        } 
        return sb.toString(); 
    }
    
    public static String escapeHTMLTags(String str) {
        if (isEmpty(str)) {
            return "";
        }
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quot;");
        return str;
    }
    
    public static String clearHTMLTags(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.replaceAll("<.*?>", "");
    }
    
    public static String clearWhitespace(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.replaceAll("\\s+", "");
    }

}
