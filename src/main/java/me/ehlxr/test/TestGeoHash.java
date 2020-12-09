package me.ehlxr.test;

import me.ehlxr.utils.GeoHash;

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