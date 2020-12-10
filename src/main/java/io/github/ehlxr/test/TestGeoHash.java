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

import io.github.ehlxr.utils.GeoHash;

public class TestGeoHash {


    public static void main(String[] args) {
        //		double lon1 = 109.014520;
        //		double lat1 = 34.236080;
        //
        //		double lon2 = 108.9644583556;
        //		double lat2 = 34.286439088548;
        //		double dist;
        //		String geocode;
        //
        //		dist = CommonUtils.getDistance(lon1, lat1, lon2, lat2);
        //		System.out.println("两点相距：" + dist + " 米");
        //
        //		geocode = GeoHash.encode(lat1, lon1);
        //		System.out.println("当前位置编码：" + geocode);
        //
        //		geocode = GeoHash.encode(lat2, lon2);
        //		System.out.println("远方位置编码：" + geocode);
        //
        //		System.out.println(GeoHash.decode("wqjdb8mzw7vspswfydscen0002")[0]+" "+GeoHash.decode("wqjdb8mzw7vspswfydscen0002")[1]);

        double lon1 = 112.014520;
        double lat1 = 69.236080;
        System.out.println(GeoHash.encode(lat1, lon1));

        double lat = 34.236088;
        double lon = 109.01455;
        System.out.println(GeoHash.encode(lat, lon));
    }
}