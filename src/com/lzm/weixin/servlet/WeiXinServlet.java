package com.lzm.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.lzm.weixin.entity.AccessToken;
import com.lzm.weixin.entity.TextMessage;
import com.lzm.weixin.util.CheckUtil;
import com.lzm.weixin.util.MessageUtil;

public class WeiXinServlet extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 *
		 * 	微信加密签名，
		 * signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		 */
		String signature = req.getParameter("signature");	
		String timestamp = req.getParameter("timestamp");	//时间戳
		String nonce = req.getParameter("nonce");			//随机数
		String echostr = req.getParameter("echostr");		//随机字符串
		boolean flag = CheckUtil.checkSignature(signature, timestamp, nonce);
		PrintWriter out = resp.getWriter();
		if(flag){
			out.print(echostr);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String,String> map = MessageUtil.xmlToMap(req);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
			//String createTime = map.get("CreateTime");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			//String msgId = map.get("MsgId");
			String message = null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					message = MessageUtil.initText(
							fromUserName, toUserName, MessageUtil.firstMenu());
				}else if("2".equals(content)){
					message = MessageUtil.initText(
							fromUserName, toUserName, MessageUtil.scendMenu());
				}else if("?".equals(content)||"？".equals(content)){
					message = MessageUtil.initText(
							fromUserName, toUserName, MessageUtil.menuText());
				}else if("3".equals(content)){
					message = MessageUtil.initNewsMessage(fromUserName, toUserName);
				}else if("4".equals(content)){
					message = MessageUtil.initImageMessage(fromUserName, toUserName);
				}else if("5".equals(content)){
					message = MessageUtil.initMusicMessage(fromUserName, toUserName);
				}else if("6".equals(content)){
					message = MessageUtil.initVideoMessage(fromUserName, toUserName);
				}
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType = map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message = MessageUtil.initText(
							fromUserName, toUserName, MessageUtil.menuText());
				}else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
					message = MessageUtil.initText(
							fromUserName, toUserName, MessageUtil.menuText());
				}else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){
					String url = map.get("EventKey");
					message = MessageUtil.initText(
							fromUserName, toUserName, url);
				}
			}else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
				String label = map.get("Label");
				message = MessageUtil.initText(
						fromUserName, toUserName, label);
			}
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally{
			out.close();
		}
	}
	
	
	
	
}
