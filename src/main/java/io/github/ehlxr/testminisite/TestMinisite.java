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

package io.github.ehlxr.testminisite;

import io.github.ehlxr.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

public class TestMinisite {
    public static String URL = "http://127.0.0.1:8080/dsp-minisite/Audi2016Q2/getProvinces";

    public static void main(String[] args) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("project", "1");
        params.put("bid","1");
        params.put("mapid","1");
        params.put("name","1");
        params.put("gender","1");
        params.put("tel","1");
        params.put("favoriteModel","1");
        params.put("province","1");
        params.put("city","1");
        params.put("dealerName","1");
        params.put("dealerCode","1");
        params.put("scheduledTime","1");
        params.put("budget","1");

        params.put("provinceName","北京");
        params.put("cityName","北京");

        //		System.out.println(HttpClientUtil.sendPostParam(URL, params));
        System.out.println(HttpClientUtil.sendGet(URL));
    }

}
