package com.ldm;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class MyPageProcessor implements PageProcessor {

	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等

	private Site site = Site.me().setRetryTimes(3).setSleepTime(0);

	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑


	public void process(Page page) {

		// 部分二：定义如何抽取页面信息，并保存下来

		// <h1>

		// <span class="link_title"><a
		// href="/u010670689/article/details/41576647">

		// 启动hive metastore命令

		// </a></span>

		// </h1>

		// csdn文章标题的html代码

		page.putField("title", page.getHtml().xpath("//h1/span[@class='link_title']/a/text()").toString());
//		page.putField("title", page.getHtml().xpath("a/text()").toString());

		if (page.getResultItems().get("title") == null) {

			// skip this page

			page.setSkip(true);

		}

		// 部分三：从页面发现后续的url地址来抓取

		// <span class="link_title"><a
		// href="/u010670689/article/details/41704239">

		// webmagic总体介绍

		// </a></span>

		page.addTargetRequests(page.getHtml().links().regex("(/u010670689/article/details/(\\w+))").all());

	}


	public Site getSite() {

		return site;

	}

	public static void main(String[] args) {

		Spider.create(new MyPageProcessor())

				// 从"https://github.com/code4craft"开始抓

				.addUrl("http://blog.csdn.net/u010670689?viewmode=contents")

				// 保存到json文件里

				.addPipeline(new ConsolePipeline())

				// 开启5个线程抓取

				.thread(5)

				// 启动爬虫

				.run();
		
//		BaiduBaike.main(null);
		
	}

}