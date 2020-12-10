/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrg@live.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.ehlxr.testopen;

import io.github.ehlxr.utils.AESTool;
import io.github.ehlxr.utils.Base64;
import io.github.ehlxr.utils.HttpClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TestOpenPuton {
    private static final String URL = "http://127.0.0.1:8080/dsp-open/pxene/dsp.do";
    private static final String key = "adjdjfjfjfjdkdkd";//
    private static final String appid = "t123456";// 用户
    private static final String token = "cst123456";// 令牌

    public static void main(String[] args) throws Exception {

        //		String sendPostParam = HttpClientUtil.sendPostParam(URL, getPostParam("setAdxProp"));// 获得数据并且发送请求
        //		String data = getData(sendPostParam);
        //		System.out.println(JSONObject.fromObject(data));


        JSONObject json = new JSONObject();
        json.put("param", "中午");
        String str = "{\"order_info\":{\"put_time\":[],\"cost_type\":1,\"order_budget\":{\"budget_all\":100.0},\"cost_single\":100.0,\"type\":1,\"order_id\":14575,\"landing_page\":\"www.baidu.com\",\"order_name\":\"tony-test-31\",\"brand_en\":null,\"plat\":1,\"end_time\":\"2016-05-03\",\"creative_group\":[{\"group_name\":\"test_五一\",\"plat\":1,\"ratio\":\"-\",\"group_id\":405,\"click_code\":null,\"impression_code\":null,\"landing_page\":\"www.baidu.com\"}],\"buy_type\":1,\"order_freq\":{\"imp_day_req\":5,\"click_total_freq\":5,\"imp_total_req\":10,\"click_day_freq\":1},\"ad_owner\":{\"owner_qualification\":[],\"owner_name\":\"ABI\",\"organization_code\":null,\"owner_id\":107,\"owner_category\":\"食品饮料>健康饮料，运动饮料，功能性饮料\",\"website_url\":\"http://sedrin.reloadbuzz.com/food2/xueJin/index.php/home/user/index\"},\"start_time\":\"2016-05-03\",\"order_goal\":{\"order_total_show\":1},\"brand_zh\":\"Sedrin\",\"industry\":\"食品饮料>健康饮料，运动饮料，功能性饮料\",\"note\":\"媒体类型定向：\\nnull\\n\\n关键词定向：\\n123\\n\\n广告素材轮播：\\n平均\\n\\n备注：\\n备注1(资源设置)：\\n321\\n\\n\\n\"}}";

        System.out.println(HttpClientUtil.sendPostJSONData(URL, str));
    }

    public static String getData(String encryptString) throws Exception {
        byte[] decode = Base64.decode(encryptString.getBytes());
        String aString = new String(decode, StandardCharsets.UTF_8);
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
