/**
 *Copyright (c) 1997, 2015,BEST WONDER CO.,LTD. All rights reserved.
 */

package com.ldm;

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

	
	Site site = Site.me().setRetryTimes(3).setSleepTime(1).setCharset("gb2312");
	/**
	  * @author Administrator
	  * @Description: TODO
	  * @param @return  
	  * @throws
	  * @date 2016年1月14日 上午10:19:43
	  */
	@Override
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
	@Override
	public void process(Page page) {
		
		page.putField("title", page.getHtml().xpath("//a/text()").toString());
//		System.out.println("------"+page.getHtml());
		if (page.getResultItems().get("title") == null) {

			// skip this page

			page.setSkip(true);

		}
		page.addTargetRequests(page.getHtml().links().regex("(/info.asp?infoid=(\\d+))").all());
		
	}
	
	public static void main(String[] args) {
		Spider.create(new PageSpider()).addUrl("http://www.hd12333.com/zhengzaibanli/").addPipeline(new ConsolePipeline()).thread(1).run();
		System.out.println("抓取结束！");
	}
	
	

}
