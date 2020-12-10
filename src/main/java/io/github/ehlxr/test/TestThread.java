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

package io.github.ehlxr.test;

import io.github.ehlxr.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

public class TestThread implements Runnable {

    @Override
    public void run() {
        //		HessianProxyFactory factory = new HessianProxyFactory();
        //		IChargeCounter readLogs;
        //		for(int i=0;i<5;i++){
        //			try {
        //				readLogs = (IChargeCounter) factory.create(IChargeCounter.class, "http://localhost:8080/dsp-counter/remote/chargeCounter");
        //				readLogs.counterControlForThisSumResult("100003", 1, "m");
        //				//System.out.println(JedisUtil.getStr("dsp_counter_100003"));
        //				break;
        //			} catch (Exception e) {
        //				try {
        //					Thread.sleep(1000);
        //				} catch (InterruptedException e1) {
        //					e1.printStackTrace();
        //				}
        //			}
        //		}


        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("groupid", "100003");
        postParam.put("count", "1");
        postParam.put("type", "m");
        for (int i = 0; i < 5; i++) {
            try {
                HttpClientUtil.sendPostParam("http://192.168.1.135:8080/dsp-counter/remote/chargeCounter/counterControlForThisSumResult", postParam);
                //				HttpClientUtil.sendPost("http://192.168.1.135:8080/dsp-counter/remote/chargeCounter/counterControlForThisSumResult", "groupid=100003&count=1&type=m");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
