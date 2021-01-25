package com.example.a911month;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * �첽ͨ����
 * @author mingyue
 *
 */
public class HttpData extends AsyncTask<String, Void, String> {

	private HttpClient httpClient;
	private HttpGet httpGet;
	private HttpResponse httpResponse; //��ȡ���󷵻ص�����
	private HttpEntity httpEntity;//����httpʵ��
	private InputStream in; //����ȡ��������ת��Ϊ���ļ�
	
	private HttpGetDataListener listener;//ʵ���Զ����HttpGetDataListener�ӿ�,���ҹ��컯���ݲ���
	
	private String url;
	public HttpData(String url , HttpGetDataListener listener) {
		this.url = url;
		this.listener = listener;
	}
	
	@Override
	protected String doInBackground(String... params) {//ʵ�ֽӿں���д�˷���,�˷����������ǣ�����get����󣬻�ȡ����
		try {
			httpClient = new DefaultHttpClient();//ʵ�����ͻ���
			httpGet =  new HttpGet(url);//ʹ��get��ʽ��ͨ������URL������
			httpResponse = httpClient.execute(httpGet); //ͨ���ͻ��˷�������
			httpEntity = httpResponse.getEntity();//ͨ��httpResponse�����ȡ����
			
			Log.i("haha1", "----------"+httpEntity.toString());
			
			in = httpEntity.getContent();//��ȡʵ��ľ�������
			BufferedReader br = new BufferedReader(new InputStreamReader(in));//��ȡ���������ݺ�ͨ�����������ж�ȡ
			
			Log.i("haha2", "----------"+br.toString());
			
			String line = null; //��ȡ����
			StringBuffer sb = new StringBuffer();//������������
			while((line  = br.readLine()) != null){ //��ȡ������������
				sb.append(line); //�洢���ݵ�StringBuffer��
			}
			
			Log.i("haha3", "----------"+sb.toString());
			
			return sb.toString();//ת��ΪString����
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	/**����
	 * ��д�˷�����ͨ���ⷽ����ȡ����
	 */
	@Override
	protected void onPostExecute(String result) {
		listener.getDataUrl(result);//��������
		
		Log.i("haha4", "----------"+result);
		
		super.onPostExecute(result);
	}
	
	

}
