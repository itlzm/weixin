package com.lzm.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lzm.weixin.entity.Image;
import com.lzm.weixin.entity.ImageMessage;
import com.lzm.weixin.entity.Music;
import com.lzm.weixin.entity.MusicMessage;
import com.lzm.weixin.entity.News;
import com.lzm.weixin.entity.NewsMessage;
import com.lzm.weixin.entity.TextMessage;
import com.lzm.weixin.entity.Video;
import com.lzm.weixin.entity.VideoMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	public static String MESSAGE_TEXT = "text";
	public static String MESSAGE_NEWS = "news";
	public static String MESSAGE_IMAGE = "image";
	public static String MESSAGE_MUSIC = "music";
	public static String MESSAGE_VOICE = "voice";
	public static String MESSAGE_VIDEO = "video";
	public static String MESSAGE_LINK = "link";
	public static String MESSAGE_LOCATION = "location";
	public static String MESSAGE_EVENT = "event";
	public static String MESSAGE_SUBSCRIBE = "subscribe";
	public static String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static String MESSAGE_CLICK = "CLICK";
	public static String MESSAGE_VIEW = "VIEW";
	/**
	 * xml转换为map集合
	 * @param req
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		InputStream ins = req.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element e : list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	/**
	 * 将TextMessage对象转换为xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	public static String initText(String fromUserName,String toUserName,String content){
		TextMessage text = new TextMessage();
		text.setToUserName(fromUserName);
		text.setFromUserName(toUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}
	/**
	 * 主菜单
	 * @return
	 */
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎关注此果粉之家，请按照菜单提示操作：\n\n");
		sb.append("1.公众号介绍\n\n");
		sb.append("2.功能介绍\n\n");
		sb.append("3.图文消息\n\n");
		sb.append("4.图片消息\n\n");
		sb.append("5.音乐消息\n\n");
		sb.append("6.视频消息\n\n");
		sb.append("回复?调出此菜单.");
		return sb.toString();
	}
	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("该公众号持有者lzm,注册时间2016-08-08.");
		return sb.toString();
	}
	public static String scendMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("1.Apple周边新奇特产品，让您迅速了解到高科技东东。\n\n"
				+ "2.大卖场产品促销等咨询，为您省钱，省心，省时间。\n\n"
				+ "3.Apple产品知识大全，保修期查询等贴心服务。");
		return sb.toString();
	}
	/**
	 * 将图文消息转换为XML
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}
	/**
	 * 将图片消息转换为XML
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	/**
	 * 将音乐消息转换为XML
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	/**
	 * 将视频消息转换为XML
	 * @param videoMessage
	 * @return
	 */
	public static String videoMessageToXml(VideoMessage videoMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}
	/**
	 * 图文消息的组装
	 * @param fromUser
	 * @param toUser
	 * @return
	 */
	public static String initNewsMessage(String fromUser,String toUser){
		String message = null;
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();
		//设置消息内容
		News news = new News();
		news.setTitle("测试");
		news.setDescription("测试用例，谢谢！");
		news.setPicUrl("http://lzm.tunnel.qydev.com/weixin/images/safari-big.jpg");
		news.setUrl("www.apple.com");
		newsList.add(news);
		
		newsMessage.setFromUserName(toUser);
		newsMessage.setToUserName(fromUser);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		//将newsMessage转换为XML
		message = MessageUtil.newsMessageToXml(newsMessage);
		return message;
	}
	/**
	 * 图片消息的组装
	 * @param fromUser
	 * @param toUser
	 * @return
	 */
	public static String initImageMessage(String fromUser,String toUser){
		
		String message = null;
		Image image = new Image();
		image.setMediaId("7ren4MFMxtWh89EE1w6wJzsJ5cJF5opX9SPXY9fVuzBkv_EApQ0eLzC-wnCLpo76");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUser);
		imageMessage.setToUserName(fromUser);
		imageMessage.setMsgType(MessageUtil.MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = MessageUtil.imageMessageToXml(imageMessage);
		return message;
	}
	/**
	 * 音乐消息的组装
	 * @param fromUser
	 * @param toUser
	 * @return
	 */
	public static String initMusicMessage(String fromUser,String toUser){
		String message = null;
		Music music = new Music();
		music.setTitle("MAYDAY");
		music.setDescription("我不愿让你一个人");
		music.setThumbMediaId("-gNCCGE_G0jjSb8RCncfWk7-CVuISRb-6AW3ycXehMM37CmB03_xILGVIy9444pJ");
		music.setHQMusicUrl("http://lzm.tunnel.qydev.com/weixin/music/我不愿让你一个人.mp3");
		music.setMusicUrl("http://lzm.tunnel.qydev.com/weixin/music/我不愿让你一个人.mp3");
		
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUser);
		musicMessage.setToUserName(fromUser);
		musicMessage.setMsgType(MessageUtil.MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		
		message = MessageUtil.musicMessageToXml(musicMessage);
		return message;
	}
	/**
	 * 视频消息的组装
	 * @param fromUser
	 * @param toUser
	 * @return
	 */
	public static String initVideoMessage(String fromUser,String toUser){
		String message = null;
		Video video = new Video();
		video.setDescription("Change is in the Air");
		video.setTitle("IPAD Air2");
		video.setMediaId("J5ImrduuMur1kIkoOlT7YO9Ez2mtHjMRLVMdBmNc7esm7k20jBsDVMFvcIfm0Jyb");
		
		VideoMessage videoMessage = new VideoMessage();
		videoMessage.setFromUserName(toUser);
		videoMessage.setToUserName(fromUser);
		videoMessage.setMsgType(MessageUtil.MESSAGE_VIDEO);
		videoMessage.setCreateTime(new Date().getTime());
		videoMessage.setVideo(video);
		message = MessageUtil.videoMessageToXml(videoMessage);
		return message;
	}
}
