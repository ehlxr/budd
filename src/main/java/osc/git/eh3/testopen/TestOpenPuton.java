package osc.git.eh3.testopen;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import osc.git.eh3.utils.AESTool;
import osc.git.eh3.utils.Base64;
import osc.git.eh3.utils.HttpClientUtil;

public class TestOpenPuton {
//	public static String URL = "http://111.235.158.25:8080/dsp-open/pxene/dsp.do";
	public static String URL = "http://127.0.0.1:8080/dsp-open/pxene/dsp.do";
	private static String key = "adjdjfjfjfjdkdkd";//
	private static String appid = "t123456";// 用户
	private static String token = "cst123456";// 令牌

	public static void main(String[] args) throws Exception {

//		String sendPostParam = HttpClientUtil.sendPostParam(URL, getPostParam("setAdxProp"));// 获得数据并且发送请求
//		String data = getData(sendPostParam);
//		System.out.println(JSONObject.fromObject(data));
		
		
		String str = "{\"order_info\":{\"order_id\":300001,\"order_name\":\"20160506Amnet\",\"brand_zh\":\"复歌Amnet\",\"brand_en\":\"fugetech\",\"note\":\"note\",\"industry\":\"个人护理/药品>护发品\",\"ad_owner\":{\"owner_id\":201,\"owner_name\":\"复歌\",\"owner_category\":\"个人护理/药品>护发品\",\"organization_code\":\"123456789\",\"website_url\":\"www.fugetech.com\",\"owner_qualification\":[{\"url\":\"http://img.pxene.com/basic/221dab14-e401-446d-bd59-5e0a798a3eb5/366f3ad4156c4625aae8202e03f1f01d.jpg\",\"qualification_name\":\"企业营业执照\"},{\"url\":\"http://img.pxene.com/basic/221dab14-e401-446d-bd59-5e0a798a3eb5/366f3ad4156c4625aae8202e03f1f01d.jpg\",\"qualification_name\":\"ICP证书\"}]},\"start_time\":\"2015-12-01\",\"end_time\":\"2016-12-30\",\"buy_type\":1,\"plat\":1,\"type\":1,\"landing_page\":\"www.fugetech.com\",\"order_area\":[{\"first_level\":\"山东\",\"second_level\":[\"烟台\",\"青岛\"]},{\"first_level\":\"河北\"}],\"order_budget\":{\"budget_all\":20000000,\"budget_day\":200000},\"order_goal\":{\"order_day_show\":200,\"order_total_show\":20000,\"order_day_click\":100,\"order_total_click\":10000,\"order_day_conversion\":50,\"order_total_conversion\":5000,\"order_day_ctr\":0.5,\"order_total_ctr\":0.5},\"order_freq\":{\"imp_day_req\":2000,\"imp_total_req\":10000,\"click_day_freq\":100,\"click_total_freq\":500},\"order_pmp\":[{\"deal_id\":123456,\"ratio\":0.12,\"purcharse\":12345,\"media_domain\":\"www.fugetech.com\"}],\"media_white\":[\"111\"],\"media_black\":[\"222\"],\"creative_group\":[{\"group_id\":1,\"group_name\":\"创意分组A\",\"landing_page\":\"www.fugetech.com\",\"impression_code\":\"www.123.com\",\"click_code\":\"www.456.com\",\"ratio\":\"40%\",\"creative\":[{\"id\":301,\"name\":\"test\",\"plat\":1,\"width\":580,\"height\":90,\"type\":\"jpg\",\"download\":true,\"url\":\"http://img.pxene.com/basic/221dab14-e401-446d-bd59-5e0a798a3eb5/366f3ad4156c4625aae8202e03f1f01d.jpg\"}]},{\"group_id\":2,\"group_name\":\"创意分组B\",\"landing_page\":\"www.fugetech.com\",\"impression_code\":\"www.789.com\",\"click_code\":\"www.963.com\",\"ratio\":\"60%\",\"creative\":[{\"id\":302,\"name\":\"test\",\"plat\":1,\"width\":580,\"height\":90,\"type\":\"jpg\",\"download\":true,\"url\":\"http://img.pxene.com/basic/221dab14-e401-446d-bd59-5e0a798a3eb5/366f3ad4156c4625aae8202e03f1f01d.jpg\"}]}]}}";
		
//		String str = "{\"order_info\":{\"note\":\"媒体类型定向：\\nnull\\n\\n广告素材轮播：\\n平均\\n\\n\",\"put_time\":[],\"buy_type\":1,\"cost_type\":2,\"ad_owner\":{\"owner_name\":\"Fuge-test\",\"owner_category\":\"技术，媒体，通讯类>计算机服务和软件业\",\"website_url\":\"http://www.fugetech.com/\",\"owner_id\":180,\"organization_code\":null,\"owner_qualification\":[]},\"end_time\":\"2016-05-31\",\"order_budget\":{\"budget_all\":11100.0},\"landing_page\":\"www.fugetech.com\",\"industry\":\"技术，媒体，通讯类>计算机服务和软件业\",\"type\":1,\"cost_single\":100.0,\"order_name\":\"Pxene ExecBaics50Log\",\"start_time\":\"2016-05-08\",\"order_goal\":{\"order_total_show\":111000},\"brand_zh\":\"Fuge\",\"plat\":1,\"order_id\":14578,\"brand_en\":null,\"creative_group\":[{\"group_id\":380,\"group_name\":\"test1_ttt\",\"landing_page\":\"www.123.com\",\"plat\":1,\"impression_code\":\"www.abc.com\",\"click_code\":\"www.456.com\",\"creative\":[{\"download\":true,\"name\":\"3218191555260729151.png\",\"width\":960,\"id\":653,\"type\":\"png\",\"url\":\"http://rimix.fugetech.com/sending/14578/20160509171537/creative/3218191555260729151.png\",\"height\":60}],\"ratio\":\"-\"}]}}";
		
		System.out.println(HttpClientUtil.sendPostJSONData(URL, str));
	}

	public static String getData(String encryptString) throws Exception {
		byte[] decode = Base64.decode(encryptString.getBytes());
		String aString = new String(decode, "utf-8");
		String decrypt = AESTool.decrypt(aString, key);
		return decrypt;
	}

	public static Map<String, String> getPostParam(String content) throws Exception {
		Map<String, String> postParam = new HashMap<String, String>();
		content = getContent(content);

		// 业务数据
		long millis = System.currentTimeMillis();// 时间戳j
		content = AESTool.encrypt(content, key);// 使用aes加密
		String lol = SignatureUtil.digest(content, "MD5");// 摘要
		String signature = SignatureUtil.generateSignature(appid, token, lol, millis);// 签名

		// 准备提交数据
		postParam.put("appid", appid);
		postParam.put("content", content);
		postParam.put("lol", lol);
		postParam.put("signature", signature);
		postParam.put("millis", millis + "");

		return postParam;
	}

	// 在这里写请求数据
	public static String getContent(String contentName) {
		JSONObject content = new JSONObject();
		JSONObject param = new JSONObject();
		content.put("servicename", "putonServiceCall");

		switch (contentName) {
		case "putOnByCreative":
			param.put("campaignid", "26861f62-5cd7-4073-9186-676f8f5d7b24");
			param.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			param.put("adxid", "1fed4171-9925-4834-aa7b-9b4d3a58841b");
			
			JSONArray mapIds = new JSONArray();
			mapIds.add("28f13909-dbbe-42e4-b9fd-edd97a31d6ce");
			mapIds.add("8b7b1b4a-eb3a-4be0-809b-b497c58a14f6");
			mapIds.add("b7f39e0c-3025-4fa3-8e83-ef1f492fe358");
			param.put("mapids", mapIds);

			content.put("funcname", "putOnByCreative");
			content.put("methodparam", param);
			break;
		case "pauseByCreative":
			param.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			param.put("adxid", "1fed4171-9925-4834-aa7b-9b4d3a58841b");
			
			mapIds = new JSONArray();
			mapIds.add("28f13909-dbbe-42e4-b9fd-edd97a31d6ce");
			mapIds.add("8b7b1b4a-eb3a-4be0-809b-b497c58a14f6");
			mapIds.add("b7f39e0c-3025-4fa3-8e83-ef1f492fe358");
			param.put("mapids", mapIds);
			
			content.put("funcname", "pauseByCreative");
			content.put("methodparam", param);
			break;
		case "putOnByAdx":
			param.put("campaignid", "26861f62-5cd7-4073-9186-676f8f5d7b24");
			
			JSONArray groupAdxs = new JSONArray();
			JSONObject groupAdx = new JSONObject();
			groupAdx.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			groupAdx.put("adxid", "1fed4171-9925-4834-aa7b-9b4d3a58841b");
			groupAdxs.add(groupAdx);
			groupAdx = new JSONObject();
			groupAdx.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			groupAdx.put("adxid", "6246ae47-d24b-4afa-88ba-57417ccab6aa");
			groupAdxs.add(groupAdx);
			groupAdx = new JSONObject();
			groupAdx.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			groupAdx.put("adxid", "ce579246-e707-4cb9-b982-88cad7944b92");
			groupAdxs.add(groupAdx);
			
			param.put("groupadxs", groupAdxs);
			
			content.put("funcname", "putOnByAdx");
			content.put("methodparam", param);
			break;
		case "pauseByAdx":
			groupAdxs = new JSONArray();
			groupAdx = new JSONObject();
			groupAdx.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			groupAdx.put("adxid", "1fed4171-9925-4834-aa7b-9b4d3a58841b");
			groupAdxs.add(groupAdx);
			groupAdx = new JSONObject();
			groupAdx.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			groupAdx.put("adxid", "6246ae47-d24b-4afa-88ba-57417ccab6aa");
			groupAdxs.add(groupAdx);
			groupAdx = new JSONObject();
			groupAdx.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			groupAdx.put("adxid", "ce579246-e707-4cb9-b982-88cad7944b92");
			groupAdxs.add(groupAdx);
			
			param.put("groupadxs", groupAdxs);
			
			content.put("funcname", "pauseByAdx");
			content.put("methodparam", param);
			break;
		case "putOnByGroup":
			param.put("campaignid", "26861f62-5cd7-4073-9186-676f8f5d7b24");
			
			JSONArray groupids = new JSONArray();
			groupids.add("022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			param.put("groupids", groupids);

			content.put("funcname", "putOnByGroup");
			content.put("methodparam", param);
			break;
		case "pauseByGroup":
			groupids = new JSONArray();
			groupids.add("022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			param.put("groupids", groupids);
			
			content.put("funcname", "pauseByGroup");
			content.put("methodparam", param);
			break;
		case "putOnByCampaign":
			JSONArray campaignids = new JSONArray();
			campaignids.add("26861f62-5cd7-4073-9186-676f8f5d7b24");
			param.put("campaignids", campaignids);
			
			content.put("funcname", "putOnByCampaign");
			content.put("methodparam", param);
			break;
		case "pauseByCampaign":
			campaignids = new JSONArray();
			campaignids.add("26861f62-5cd7-4073-9186-676f8f5d7b24");
			param.put("campaignids", campaignids);
			
			content.put("funcname", "pauseByCampaign");
			content.put("methodparam", param);
			break;
		case "setAdxProp":
			JSONArray propdatas = new JSONArray();
			JSONObject propdata = new JSONObject();
			JSONObject adxprop = new JSONObject();
			JSONArray adxprops = new JSONArray();
			
			adxprop.put("adxid", "1fed4171-9925-4834-aa7b-9b4d3a58841b");
			adxprop.put("prop", 20);
			adxprops.add(adxprop);
			
			adxprop = new JSONObject();
			adxprop.put("adxid", "6246ae47-d24b-4afa-88ba-57417ccab6aa");
			adxprop.put("prop", 15.5);
			adxprops.add(adxprop);
			
			adxprop = new JSONObject();
			adxprop.put("adxid", "ce579246-e707-4cb9-b982-88cad7944b92");
			adxprop.put("prop", 26.5);
			adxprops.add(adxprop);
			
			propdata.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			propdata.put("adxprops", adxprops);
			
			propdatas.add(propdata);
			
			param.put("propdatas", propdatas);
			content.put("funcname", "setAdxProp");
			content.put("methodparam", param);
			break;
		case "setCreateivePrice":
			JSONArray createiveprices = new JSONArray();
			
			JSONObject createiveprice = new JSONObject();
			createiveprice.put("mapid", "28f13909-dbbe-42e4-b9fd-edd97a31d6ce");
			createiveprice.put("price", 10);
			createiveprices.add(createiveprice);
			
			createiveprice = new JSONObject();
			createiveprice.put("mapid", "8b7b1b4a-eb3a-4be0-809b-b497c58a14f6");
			createiveprice.put("price", 6);
			createiveprices.add(createiveprice);
			
			createiveprice = new JSONObject();
			createiveprice.put("mapid", "b7f39e0c-3025-4fa3-8e83-ef1f492fe358");
			createiveprice.put("price", 8);
			createiveprices.add(createiveprice);
			
			param.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			param.put("adxid", "1fed4171-9925-4834-aa7b-9b4d3a58841b");
			param.put("createiveprices", createiveprices);
			
			content.put("funcname", "setCreateivePrice");
			content.put("methodparam", param);
			break;
		case "getKpiByCampaignIds":
			campaignids = new JSONArray();
			campaignids.add("26861f62-5cd7-4073-9186-676f8f5d7b24");
			
			param.put("campaignids", campaignids);
			
			content.put("funcname", "getKpiByCampaignIds");
			content.put("methodparam", param);
			break;
		case "getKpiByGroupIds":
			groupids = new JSONArray();
			groupids.add("022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			
			param.put("groupids", groupids);
			
			content.put("funcname", "getKpiByGroupIds");
			content.put("methodparam", param);
			break;
		case "getKpiByAdxIds":
			JSONArray adxids = new JSONArray();
			adxids.add("1fed4171-9925-4834-aa7b-9b4d3a58841b");
			adxids.add("6246ae47-d24b-4afa-88ba-57417ccab6aa");
			adxids.add("ce579246-e707-4cb9-b982-88cad7944b92");
			
			param.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			param.put("adxids", adxids);
			
			content.put("funcname", "getKpiByAdxIds");
			content.put("methodparam", param);
			break;
		case "getKpiByMapIds":
			JSONArray mapids = new JSONArray();
			mapids.add("28f13909-dbbe-42e4-b9fd-edd97a31d6ce");
			mapids.add("8b7b1b4a-eb3a-4be0-809b-b497c58a14f6");
			mapids.add("b7f39e0c-3025-4fa3-8e83-ef1f492fe358");
			
			param.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			param.put("adxid", "1fed4171-9925-4834-aa7b-9b4d3a58841b");
			param.put("mapids", mapids);
			
			content.put("funcname", "getKpiByMapIds");
			content.put("methodparam", param);
			break;
		default:
			break;
		}
		System.out.println(content.toString());
		return content.toString();
	}
}
