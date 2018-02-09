package cn.edu.uzz.util;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class Push {
	
	public static void push(String account,String text){
		String masterSecret="43e424860e9e7925ecde5895";
		String appKey="baa9ac22da86f581723d96a6";
		JPushClient jPushClient=new JPushClient(masterSecret, appKey);
		
		PushPayload payload=PushPayload.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.all())
				.setNotification(Notification.alert(text))
				.setMessage(Message.content("zzxy"))
				.build();
		try {
			PushResult result=jPushClient.sendPush(payload);
			System.out.println("success");
			System.out.println(result.msg_id);
			System.out.println(result.sendno);
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
