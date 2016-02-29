package osc.git.eh3.test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.caucho.hessian.client.HessianProxyFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import osc.git.eh3.readlogs.IReadLogs;

public class TestSyncLogData {

	public static void main(String[] args) throws Exception {
		List<String> mapids = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HH");

		// 测试数据
		mapids.add("ad2176a7-74f2-4eab-965f-04c509653a93");
		mapids.add("63cae342-3288-4591-8097-53a9ac8bbe48");
		mapids.add("b38749f5-d5f9-441f-89de-a97c9b35adb0");
		mapids.add("3bc9a763-b5ad-4ade-a46c-12ce6f7a9479");
		mapids.add("cf384c11-0b25-4462-80ae-8a7eaedbf765");
		mapids.add("e9d823fe-379a-4d86-b9cd-93bb21c085a3");
		mapids.add("fa3c7262-4538-4192-bbca-ac1114f690c1");
		mapids.add("441af98d-917a-423a-ad3d-6faa8609e722");
		mapids.add("60112069-7c05-4135-8430-836ed61576e8");
		mapids.add("62367312-d881-426d-81b4-fe635d1db989");
		mapids.add("c41c1d40-1f71-44d2-88ab-478a584cd030");
		mapids.add("5f9735cf-6036-4902-9cff-5c7a40c76f24");

		Map<String, Double> adxs = new HashMap<>();
		adxs.put("15", 1.0000);
		adxs.put("14", 1.0000);
		adxs.put("2", 1.0000);
		adxs.put("8", 1.0000);
		adxs.put("11", 1.0000);
		adxs.put("1122331", 1.0000);
		adxs.put("1", 1.0000);
		adxs.put("4", 1.0000);
		adxs.put("9", 1.0000);
		adxs.put("13", 1.0000);
		adxs.put("10", 1.0000);
		adxs.put("5", 1.0000);
		adxs.put("7", 1.0000);
		adxs.put("112233", 1.0000);
		adxs.put("3", 0.1569);

		HessianProxyFactory factory = new HessianProxyFactory();
		IReadLogs readLogs = (IReadLogs) factory.create(IReadLogs.class, "http://localhost:8080/sync-logs/remote/readlogs");
		String results = readLogs.readFile(sdf.parse("2016-02-24 09"), sdf.parse("2016-02-24 10"), mapids,
				"6692a223-2ae1-439a-93f4-09fb36e718ef",adxs);
		System.out.println(results);
		JSONArray dataArr = new JSONArray();
		dataArr = JSONArray.fromObject(results);
		// List<StatisByHourModel> dataList = (List<StatisByHourModel>)
		// JSONArray.toCollection(dataArr, StatisByHourModel.class);

		// List<Map<String, Object>> result = new ArrayList<Map<String,
		// Object>>();
		// for (Object object : dataArr) {
		// if(object == null) {
		// continue;
		// }
		// JSONObject data = JSONObject.fromObject(object);
		// String time = data.optString("time");
		// if (result.size() <= 0) {
		// Map<String, Object> resultMap = new HashMap<String, Object>();
		// resultMap.put("time", time);
		// resultMap.put("imprs",data.optInt("imprs"));
		// resultMap.put("clks", data.optInt("clks"));
		// resultMap.put("cost", data.optDouble("cost"));
		//
		// result.add(resultMap);
		// continue;
		// }
		// List<Map<String, Object>> temp = new ArrayList<Map<String,
		// Object>>();
		// for (Map<String, Object> map : result) {
		// if (time.equals(map.get("time"))) {
		// map.put("imprs",((null == map.get("imprs") ||
		// "".equals(map.get("imprs"))) ? 0 :
		// Integer.parseInt(map.get("imprs").toString())) +
		// data.optInt("imprs"));
		// map.put("clks",((null == map.get("clks") ||
		// "".equals(map.get("clks"))) ? 0 :
		// Integer.parseInt(map.get("clks").toString())) + data.optInt("clks"));
		// map.put("cost", ((null == map.get("cost") ||
		// "".equals(map.get("cost"))) ? 0 :
		// Double.parseDouble(map.get("cost").toString()))+
		// data.optDouble("cost"));
		// } else {
		// Map<String, Object> resultMap = new HashMap<String, Object>();
		// resultMap.put("time", time);
		// resultMap.put("imprs", data.optInt("imprs"));
		// resultMap.put("clks", data.optInt("clks"));
		// resultMap.put("cost",data.optInt("cost"));
		//
		// temp.add(resultMap);
		// }
		// }
		// result.addAll(temp);
		// }
		// System.out.println(result);

		// JSONArray dataList = new JSONArray();
		// dataList.addAll(JSONArray.toCollection(JSONArray.fromObject(results)));
		// List<StatisByHourModel> result = (List<StatisByHourModel>)
		// JSONArray.toCollection(dataList, StatisByHourModel.class);
		// for (StatisByHourModel statisByHourModel : result) {
		// if(null==statisByHourModel){
		// continue;
		// }
		// System.out.println(statisByHourModel.getCreativeid());
		// }

		// dataArr.addAll(JSONArray.toCollection(JSONArray.fromObject(results)));
		// List<StatisByHourModel> dataList = (List<StatisByHourModel>)
		// JSONArray.toCollection(dataArr, StatisByHourModel.class);

		// List<StatisByHourModel> result = new ArrayList<StatisByHourModel>();
		// Map<String, StatisByHourModel> result = new HashMap<>();
		//
		// for (StatisByHourModel data : dataList) {
		// if(data == null) {
		// continue;
		// }
		// String time = dateToStrLong(data.getTime());
		//
		// if(result.containsKey(time)){
		// StatisByHourModel tempData = result.get(time);
		// tempData.setImprs(tempData.getImprs()+data.getImprs());
		// tempData.setClks(tempData.getClks()+data.getClks());
		// tempData.setCost(tempData.getCost().add(data.getCost()));
		// }else{
		// result.put(time, data);
		// }
		// }

		// Map<String, Map<String, Object>> resultMap = new HashMap<>();
		// for (StatisByHourModel data : dataList) {
		// if(data == null) {
		// continue;
		// }
		// Map<String, Object> tData = new HashMap<>();
		// tData.put("time", sdf.format(data.getTime()));
		// tData.put("imprs", data.getImprs());
		// tData.put("clks", data.getClks());
		// tData.put("cost", data.getCost());
		//
		// String time = dateToStrLong(data.getTime());
		// if(resultMap.containsKey(time)){
		// Map<String, Object> tempData = resultMap.get(time);
		// tempData.put("imprs",((null == tempData.get("imprs") ||
		// "".equals(tempData.get("imprs"))) ? 0 :
		// Integer.parseInt(tempData.get("imprs").toString())) +
		// data.getImprs());
		// tempData.put("clks",((null == tempData.get("clks") ||
		// "".equals(tempData.get("clks"))) ? 0 :
		// Integer.parseInt(tempData.get("clks").toString())) + data.getClks());
		// tempData.put("cost", ((null == tempData.get("cost") ||
		// "".equals(tempData.get("cost"))) ? BigDecimal.ZERO : new
		// BigDecimal(tempData.get("cost").toString())).add(data.getCost()));
		// }else{
		// resultMap.put(time, tData);
		// }
		// }
		//
		// List dd = new ArrayList<>();
		// for (String key : resultMap.keySet()) {
		// dd.add(resultMap.get(key));
		// }
		// System.out.println(dd);

		Map<String, JSONObject> resultMap = new HashMap<>();
		for (Object object : dataArr) {
			try {
				if (object == null) {
					continue;
				}
				JSONObject data = JSONObject.fromObject(object);
				String time = data.optString("time");
				if (resultMap.containsKey(time)) {
					JSONObject tempData = resultMap.get(time);
					tempData.put("imprs", tempData.optInt("imprs") + data.optInt("imprs"));
					tempData.put("clks", tempData.optInt("clks") + data.optInt("clks"));
					tempData.put("cost", tempData.optInt("cost") + data.optInt("cost"));
				} else {
					data.put("time", sdf.format(sdf1.parse(time)));
					resultMap.put(time, data);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		List dd = new ArrayList<>();
		for (String key : resultMap.keySet()) {
			dd.add(resultMap.get(key));
		}
		System.out.println(dd);

	}

	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}
}
