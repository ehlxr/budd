package osc.git.eh3.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {
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
}
