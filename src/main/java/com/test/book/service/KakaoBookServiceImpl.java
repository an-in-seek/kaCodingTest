package com.test.book.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.test.common.util.PagingBean;

@Service
public class KakaoBookServiceImpl implements KakaoBookService {

	private final String ENCODING = "UTF-8";
	private final String REQUEST_URL = "https://dapi.kakao.com/v3/search/book";
	private final String REST_API_KEY = "94efd7a52e2efcfad361900c714ae3ad"; // REST API KEY
	private final String RESPONSE_BODY_DOCUMENTS = "documents";
	private final String RESPONSE_BODY_META = "meta";

	private HttpClient client = null;
	private ResponseHandler<String> handler = null;
	private HttpGet get = null;

	/**
	 * 카카오 Book Meta 정보 가져오기
	 * 
	 * @param keyWord
	 * @param responseBody
	 * @return
	 */
	@Override
	public JSONObject getKakaoBookMeta(String keyWord, String target, int pageNo) {
		String response = "";
		JSONObject jsonObj = null;

		try {

			Map<String, String> params = setParams(keyWord, target, pageNo);
			List<NameValuePair> paramList = convertParam(params);
			String url = REQUEST_URL + "?" + URLEncodedUtils.format(paramList, ENCODING);
			get = new HttpGet(url);
			get.addHeader("Authorization", "KakaoAK " + REST_API_KEY);// add header
			handler = new BasicResponseHandler();
			client = HttpClientBuilder.create().build();
			response = client.execute(get, handler);
			JSONParser jsonParser = new JSONParser();
			jsonObj = (JSONObject) jsonParser.parse(response);
			jsonObj = (JSONObject) jsonObj.get(RESPONSE_BODY_META);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return jsonObj;
	}

	/**
	 * 카카오 Book Detail 정보 가져오기
	 * 
	 * @param keyWord
	 * @param responseBody
	 * @return
	 */
	@Override
	public JSONArray getKakaoBookInfo(String keyWord, String target, int pageNo) {
		String response = "";
		JSONArray jsonArray = null;

		try {
			Map<String, String> params = setParams(keyWord, target, pageNo);
			List<NameValuePair> paramList = convertParam(params);
			String url = REQUEST_URL + "?" + URLEncodedUtils.format(paramList, ENCODING);
			get = new HttpGet(url);
			get.addHeader("Authorization", "KakaoAK " + REST_API_KEY);// add header
			handler = new BasicResponseHandler();
			client = HttpClientBuilder.create().build();
			response = client.execute(get, handler);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(response);
			jsonArray = (JSONArray) jsonObj.get(RESPONSE_BODY_DOCUMENTS);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return jsonArray;
	}

	/**
	 * 카카오 Book List 가져오기
	 * 
	 * @param keyWord
	 * @param responseBody
	 * @return
	 */
	@Override
	public JSONArray getKakaoBookList(String keyWord, String target, int pageNo) {
		String response = "";
		JSONArray jsonArray = null;

		try {
			Map<String, String> params = setParams(keyWord, target, pageNo);
			List<NameValuePair> paramList = convertParam(params);
			String url = REQUEST_URL + "?" + URLEncodedUtils.format(paramList, ENCODING);
			get = new HttpGet(url);
			get.addHeader("Authorization", "KakaoAK " + REST_API_KEY);// add header
			handler = new BasicResponseHandler();
			client = HttpClientBuilder.create().build();
			response = client.execute(get, handler);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(response);
			jsonArray = (JSONArray) jsonObj.get(RESPONSE_BODY_DOCUMENTS);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return jsonArray;
	}

	/**
	 * 파라미터 세팅 함수
	 * 
	 * @param keyWord
	 * @param target
	 * @param pageNo
	 * @return
	 */
	public Map<String, String> setParams(String keyWord, String target, int pageNo) {
		Map<String, String> params = new HashMap<String, String>();

		if (target == "isbn") {
			String[] keyWordArray = keyWord.split(" ");
			if (keyWordArray[0].length() == 0) {
				params.put("query", keyWordArray[1]);
			} else {
				params.put("query", keyWordArray[0]);
			}
		} else {
			params.put("query", keyWord);
		}
		params.put("target", target);
		params.put("page", Integer.toString(pageNo));
		params.put("size", Integer.toString(PagingBean.CONTENTS_PER_PAGE));

		return params;
	}

	/**
	 * 파라미터 List<NameValuePair>로 컨버팅하기
	 * 
	 * @param params
	 * @return
	 */
	private List<NameValuePair> convertParam(Map<String, String> params) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		Iterator<String> keys = params.keySet().iterator();

		while (keys.hasNext()) {
			String key = keys.next();
			paramList.add(new BasicNameValuePair(key, params.get(key).toString()));
		}

		return paramList;
	}
}