/**
 *Copyright (c) 1997, 2015,BEST WONDER CO.,LTD. All rights reserved.
 */

package com.ldm.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
  * @ClassName: PubData_GS
  * @Description: 抓取企业工商类公共数据
  * @author Administrator
  * @date 2016年1月25日 下午5:05:12
  */
public class PubData_GS implements PageProcessor {
	
	Site site = Site.me().setRetryTimes(3).setSleepTime(1).setCharset("utf-8");


	/**
	  * @author Administrator
	  * @Description: TODO
	  * @param @param page  
	  * @throws
	  * @date 2016年1月25日 下午5:05:36span[@class='link_title']/
	  */
	public void process(Page page) {
		page.putField("title", page.getHtml().xpath("//h1/a[@class='category']/text()").toString());
		if (page.getResultItems().get("title") == null) {
			page.setSkip(true);
		}
//		page.addTargetRequests(page.getHtml().links().all());
		page.getHtml().links().all();
	}

	public Site getSite() {
		return this.site;
	}
	
	public static void main(String[] args) {
		Spider.create(new PubData_GS()).addUrl("http://blog.csdn.net/?ref=toolbar_logo").addPipeline(new ConsolePipeline()).thread(5).run();
		System.out.println("抓取结束");
	}

}
