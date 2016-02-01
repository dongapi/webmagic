/**
 *Copyright (c) 1997, 2015,BEST WONDER CO.,LTD. All rights reserved.
 */

package com.ldm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
  * @ClassName: PageSpider
  * @Description: TODO
  * @author Administrator
  * @date 2016年1月14日 上午10:18:47
  */
public class PageSpider implements PageProcessor {

	
	Site site = Site.me().setRetryTimes(3).setSleepTime(1).setCharset("utf-8");
	/**
	  * @author Administrator
	  * @Description: TODO
	  * @param @return  
	  * @throws
	  * @date 2016年1月14日 上午10:19:43
	  */
	public Site getSite() {
		return this.site;
	}

	/**
	  * @author Administrator
	  * @Description: TODO
	  * @param @param arg0  
	  * @throws
	  * @date 2016年1月14日 上午10:19:43
	  */
	public void process(Page page) {
//		page.putField("title", page.getHtml().xpath("//a/text()").toString());
//		if (page.getResultItems().get("title") == null) {
//			page.setSkip(true);
//		}
//		page.addTargetRequests(page.getHtml().links().regex("").all());
		
		
		java.util.List<String> result = page.getHtml().xpath("//td/text()").all();
		if(result != null && result.size() == 12){
			System.out.println("统一社会信用代码/注册号:"+result.get(0));
			System.out.println("名称:"+result.get(1));
			System.out.println("类型:"+result.get(2));
			System.out.println("负责人:"+result.get(3));
			System.out.println("营业场所:"+result.get(4));
			System.out.println("营业期限自:"+result.get(5));
			System.out.println("营业期限至 :"+result.get(6));
			System.out.println("经营范围:"+result.get(7));
			System.out.println("登记机关:"+result.get(8));
			System.out.println("核准日期:"+result.get(9));
			System.out.println("成立日期 :"+result.get(10));
			System.out.println("登记状态:"+result.get(11));
		}else if(result != null && result.size() == 14){
			System.out.println("统一社会信用代码/注册号:"+result.get(0));
			System.out.println("名称:"+result.get(1));
			System.out.println("类型:"+result.get(2));
			System.out.println("法定代表人:"+result.get(3));
			System.out.println("注册资本:"+result.get(4));
			System.out.println("成立日期:"+result.get(5));
			System.out.println("住所 :"+result.get(6));
			System.out.println("营业期限自:"+result.get(7));
			System.out.println("营业期限至:"+result.get(8));
			System.out.println("经营范围:"+result.get(9));
			System.out.println("登记机关:"+result.get(10));
			System.out.println("核准日期:"+result.get(11));
			System.out.println("登记状态:"+result.get(12));
		}
		try {
			Connection connect = DriverManager.getConnection("jdbc:mysql://192.168.6.6:3306/pubdata?useUnicode=true&characterEncoding=UTF-8","dbuser","123123123");
			Statement stat = connect.createStatement();
			String sql = "insert into pub_gs_base values()";
			stat.executeUpdate(sql);
			stat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * @author Administrator
	  * @Description: 企业基本信息（工商类抓取）
	  * @param @param djh 企业登记号
	  * @param @param qylx 企业类型	
	  * @return void  
	  * @throws
	  * @date 2016年1月28日 下午5:06:50
	  */
	private static void spider_jbxx(String djh,String qylx){
		Spider.create(new PageSpider())
		.addUrl("http://gsxt.jxaic.gov.cn/ECPS/qyxxgsAction_initQyjbqk.action?nbxh="+djh+"&qylx="+qylx)
		.addPipeline(new ConsolePipeline())
		.thread(1)
		.run();
	}
	
	public static void main(String[] args) {
		String djh = "3604002011030300038327";
		String qylx = "1130";
		spider_jbxx(djh, qylx);
		System.out.println("抓取结束！");
	}
}
