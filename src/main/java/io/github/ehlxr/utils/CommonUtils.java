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

package io.github.ehlxr.utils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommonUtils {
	private static final double EARTH_RADIUS = 6371; // 默认地球半径(单位km)

	/**
	 * 转化为弧度(rad)
	 *
	 * @param d
	 * @return
	 */
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 对象转换成另一个类对象
	 *
	 * @param bean
	 *            转换的数据对象
	 * @param clazz
	 *            转换后类对象
	 * @return 转换后数据对象
	 */
	public static <T> T convertClass(Object bean, Class<T> clazz) {

		Map<String, Object> maps = new HashMap<String, Object>();
		T dataBean = null;
		if (null == bean) {
			return null;
		}
		try {
			Class<?> cls = bean.getClass();
			dataBean = clazz.newInstance();
			Field[] fields = cls.getDeclaredFields();
			Field[] beanFields = clazz.getDeclaredFields();
			for (Field field : fields) {
				try {
					String fieldName = field.getName().toLowerCase();
					String strGet = "get" + fieldName;

					Method[] methods = cls.getMethods();
					for (Method method : methods) {
						if (strGet.equalsIgnoreCase(method.getName())) {
							Object object = method.invoke(bean);
							maps.put(fieldName, object == null ? "" : object);
						}
					}
				} catch (Exception e) {
				}
			}
			for (Field field : beanFields) {
				field.setAccessible(true);
				String fieldName = field.getName().toLowerCase();
				Class<?> fieldType = field.getType();
				Object fieldValue = (maps.get(fieldName) == null || "".equals(maps.get(fieldName))) ? null : maps.get(fieldName);
				if (fieldValue != null) {
					if (String.class.equals(fieldType)) {
						if (fieldValue instanceof Date) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							field.set(dataBean, sdf.format(fieldValue));
						} else {
							field.set(dataBean, fieldValue.toString());
						}
					} else if (byte.class.equals(fieldType)) {
						field.setByte(dataBean, Byte.parseByte(fieldValue.toString()));

					} else if (Byte.class.equals(fieldType)) {
						field.set(dataBean, Byte.valueOf(fieldValue.toString()));

					} else if (boolean.class.equals(fieldType)) {
						field.setBoolean(dataBean, Boolean.parseBoolean(fieldValue.toString()));

					} else if (Boolean.class.equals(fieldType)) {
						field.set(dataBean, Boolean.valueOf(fieldValue.toString()));

					} else if (short.class.equals(fieldType)) {
						field.setShort(dataBean, Short.parseShort(fieldValue.toString()));

					} else if (Short.class.equals(fieldType)) {
						field.set(dataBean, Short.valueOf(fieldValue.toString()));

					} else if (int.class.equals(fieldType)) {
						field.setInt(dataBean, Integer.parseInt(fieldValue.toString()));

					} else if (Integer.class.equals(fieldType)) {
						field.set(dataBean, Integer.valueOf(fieldValue.toString()));

					} else if (long.class.equals(fieldType)) {
						field.setLong(dataBean, Long.parseLong(fieldValue.toString()));

					} else if (Long.class.equals(fieldType)) {
						field.set(dataBean, Long.valueOf(fieldValue.toString()));

					} else if (float.class.equals(fieldType)) {
						field.setFloat(dataBean, Float.parseFloat(fieldValue.toString()));

					} else if (Float.class.equals(fieldType)) {
						field.set(dataBean, Float.valueOf(fieldValue.toString()));

					} else if (double.class.equals(fieldType)) {
						field.setDouble(dataBean, Double.parseDouble(fieldValue.toString()));

					} else if (Double.class.equals(fieldType)) {
						field.set(dataBean, Double.valueOf(fieldValue.toString()));

					} else if (Date.class.equals(fieldType)) {
						field.set(dataBean, fieldValue);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataBean;
	}

	/**
	 * 获取当前网络ip
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()=15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 计算经纬度点对应正方形4个点的坐标
	 *
	 * @param longitude
	 * @param latitude
	 * @param distance
	 * @return
	 */
	public static Map<String, double[]> returnLLSquarePoint(double longitude, double latitude, double distance) {
		Map<String, double[]> squareMap = new HashMap<String, double[]>();
		// 计算经度弧度,从弧度转换为角度
		double dLongitude = 2 * (Math.asin(Math.sin(distance / (2 * EARTH_RADIUS)) / Math.cos(Math.toRadians(latitude))));
		dLongitude = Math.toDegrees(dLongitude);
		// 计算纬度角度
		double dLatitude = distance / EARTH_RADIUS;
		dLatitude = Math.toDegrees(dLatitude);
		// 正方形
		double[] leftTopPoint = { latitude + dLatitude, longitude - dLongitude };
		double[] rightTopPoint = { latitude + dLatitude, longitude + dLongitude };
		double[] leftBottomPoint = { latitude - dLatitude, longitude - dLongitude };
		double[] rightBottomPoint = { latitude - dLatitude, longitude + dLongitude };
		squareMap.put("leftTopPoint", leftTopPoint);
		squareMap.put("rightTopPoint", rightTopPoint);
		squareMap.put("leftBottomPoint", leftBottomPoint);
		squareMap.put("rightBottomPoint", rightBottomPoint);
		return squareMap;
	}

	/**
	 * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下
	 *
	 * @param lon1
	 *            第一点的精度
	 * @param lat1
	 *            第一点的纬度
	 * @param lon2
	 *            第二点的精度
	 * @param lat3
	 *            第二点的纬度
	 * @return 返回的距离，单位m
	 */
	public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lon1) - rad(lon2);
		double s = 2
				* Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS * 1000;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
}
