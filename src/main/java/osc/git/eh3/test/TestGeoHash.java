package osc.git.eh3.test;

import osc.git.eh3.utils.CommonUtils;
import osc.git.eh3.utils.GeoHash;

public class TestGeoHash {
	

	public static void main(String[] args) {
		double lon1 = 109.0145193757;
		double lat1 = 34.236080797698;
		double lon2 = 108.9644583556;
		double lat2 = 34.286439088548;
		double dist;
		String geocode;

		dist = CommonUtils.getDistance(lon1, lat1, lon2, lat2);
		System.out.println("两点相距：" + dist + " 米");

		geocode = GeoHash.encode(lat1, lon1);
		System.out.println("当前位置编码：" + geocode);

		geocode = GeoHash.encode(lat2, lon2);
		System.out.println("远方位置编码：" + geocode);
		
		System.out.println(GeoHash.decode("s6q8mc6nbupd")[0]+" "+GeoHash.decode("s6q8mc6nbupd")[1]);

	}
}