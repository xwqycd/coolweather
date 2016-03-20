package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpUtil {
	
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection=null;//初始化操作
				
				try {
					URL url = new URL(address);
					connection=(HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in=connection.getInputStream();
					
					//读取服务器返回的输入流
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line=reader.readLine())!=null) {
						response.append(line);
					}
					if (listener!=null) {
						listener.onFinish(response.toString());
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					if (listener!=null) {
						listener.onError(e);
					}
				}finally{
					if (connection!=null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
