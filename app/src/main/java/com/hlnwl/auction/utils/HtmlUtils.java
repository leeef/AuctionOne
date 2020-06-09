package com.hlnwl.auction.utils;

import com.hlnwl.auction.app.MyApplication;

/**
 * 处理 html当中的图片自适应
 */
public class HtmlUtils {

    public static String getHtml(String url){


        String css = "<style type=\"text/css\"> img {" +
                "width:100%;" +
                "height:auto;" +
                "}" +
                "body {" +
                "margin-right:5px;" +
                "margin-left:5px;" +
                "margin-top:0px;" +
                "font-size:45px;" +
                "}" +
                "</style>";
         url = url.replaceAll("<img src=\"", "<img src=\"" );
        return "<html><header>" + css + "</header><body>" + url + "</body></html>";
    }

    public static String getHtml1(String url,int size){


        String css = "<style type=\"text/css\"> img {" +
                "width:100%;" +
                "height:auto;" +
                "}" +
                "body {" +
                "margin-right:15px;" +
                "margin-left:15px;" +
                "margin-top:px;" +
                "font-size:"+size+"px;" +
                "}" +
                "</style>";
        url = url.replaceAll("<img src=\"", "<img src=\""+ MyApplication.BaseUrl);
        return "<html><header>" + css + "</header><body>" + url + "</body></html>";
    }
    public static String getHtml2(String url){

        String css = "<style type=\"text/css\"> img {" +
                "width:100%;" +
                "height:auto;" +
                "}" +
                "body {" +
                "margin-right:5px;" +
                "margin-left:5px;" +
                "margin-top:0px;" +
                "font-size:30px;" +
                "}" +
                "</style>";
        url = url.replaceAll("<img src=\"", "<img src=\"" );
        return "<html><header>" + css + "</header><body>" + url + "</body></html>";
    }

}
