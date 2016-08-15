package com.lzm.weixin.test;


import com.lzm.weixin.entity.AccessToken;
import com.lzm.weixin.util.WeiXinUtil;

import net.sf.json.JSONObject;

public class WeiXinTest {
	public static void main(String[] args) {
		AccessToken token = WeiXinUtil.getAccessToken();
		System.out.println(token.getToken());
		System.out.println(token.getExpiresIn());
		try {
			//String mediaId = WeiXinUtil.upload("D:\\apple.mp4", token.getToken(), "video");
			//System.out.println(mediaId);
			String menu = JSONObject.fromObject(WeiXinUtil.initMenu()).toString();
			int result = WeiXinUtil.createMenu(token.getToken(), menu);
			if(result==0){
				System.out.println("菜单创建成功！");
			}else{
				System.out.println("错误码为："+result);
			}
//			JSONObject jsonObject = WeiXinUtil.queryMenu(token.getToken());
//			System.out.println(jsonObject);
			
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
