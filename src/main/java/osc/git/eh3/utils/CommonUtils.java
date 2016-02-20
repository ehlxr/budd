package osc.git.eh3.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
					String fieldName = field.getName();
					String strGet = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
					Method methodGet = cls.getDeclaredMethod(strGet);
					Object object = methodGet.invoke(bean);
					maps.put(fieldName, object == null ? "" : object);
				} catch (Exception e) {
				}
			}
			for (Field field : beanFields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				Class<?> fieldType = field.getType();
				String fieldValue = (maps.get(fieldName) == null || "".equals(maps.get(fieldName))) ? null : maps.get(fieldName).toString();
				if (fieldValue != null) {
					if (String.class.equals(fieldType)) {
						field.set(dataBean, fieldValue);
					} else if (byte.class.equals(fieldType)) {
						field.setByte(dataBean, Byte.parseByte(fieldValue));

					} else if (Byte.class.equals(fieldType)) {
						field.set(dataBean, Byte.valueOf(fieldValue));

					} else if (boolean.class.equals(fieldType)) {
						field.setBoolean(dataBean, Boolean.parseBoolean(fieldValue));

					} else if (Boolean.class.equals(fieldType)) {
						field.set(dataBean, Boolean.valueOf(fieldValue));

					} else if (short.class.equals(fieldType)) {
						field.setShort(dataBean, Short.parseShort(fieldValue));

					} else if (Short.class.equals(fieldType)) {
						field.set(dataBean, Short.valueOf(fieldValue));

					} else if (int.class.equals(fieldType)) {
						field.setInt(dataBean, Integer.parseInt(fieldValue));

					} else if (Integer.class.equals(fieldType)) {
						field.set(dataBean, Integer.valueOf(fieldValue));

					} else if (long.class.equals(fieldType)) {
						field.setLong(dataBean, Long.parseLong(fieldValue));

					} else if (Long.class.equals(fieldType)) {
						field.set(dataBean, Long.valueOf(fieldValue));

					} else if (float.class.equals(fieldType)) {
						field.setFloat(dataBean, Float.parseFloat(fieldValue));

					} else if (Float.class.equals(fieldType)) {
						field.set(dataBean, Float.valueOf(fieldValue));

					} else if (double.class.equals(fieldType)) {
						field.setDouble(dataBean, Double.parseDouble(fieldValue));

					} else if (Double.class.equals(fieldType)) {
						field.set(dataBean, Double.valueOf(fieldValue));

					} else if (Date.class.equals(fieldType)) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						field.set(dataBean, sdf.parse(fieldValue));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataBean;
	}
}
