package com.adserver.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

/**
 * Utils function of a collection of reflection.
 * 
 * Provide access to private variables, access to generic type Class, extract
 * the properties of the elements in the collection, etc. Functions Utils.
 * 
 */
public class ReflectionUtils {

	private static Log log = LogFactory.getLog(ReflectionUtils.class);
	private Logger logPrint = LogUtil.log;

	/**
	 * Read directly the object attribute values, ignoring the private /
	 * protected modifiers, without passing through getter function.
	 */
	public static Object getFieldValue(final Object object,
			final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			log.error("Can not throw exceptions{}", e);
		}
		return result;
	}

	/**
	 * Directly set the object property values, ignoring the private / protected
	 * modifiers, without a setter function.
	 */
	public static void setFieldValue(final Object object,
			final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			log.error("Can not throw exceptions:{}", e);
		}
	}

	/**
	 * Directly call the object methods, ignoring private / protected modifiers.
	 */
	public static Object invokeMethod(final Object object,
			final String methodName, final Class<?>[] parameterTypes,
			final Object[] parameters) throws InvocationTargetException {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null)
			throw new IllegalArgumentException("Could not find method ["
					+ methodName + "] on target [" + object + "]");

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (IllegalAccessException e) {
			log.error("Can not throw exceptions:{}", e);
		}

		return null;
	}

	/**
	 * Upward cycle of transformation, to obtain the object DeclaredField.
	 */
	protected static Field getDeclaredField(final Object object,
			final String fieldName) {
//		Assert.notNull(object, "object cannot null!");
		log.info("object cannot null!");
		log.info("fieldName");
//		Assert.hasText(fieldName, "fieldName");
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field is not in the current class definition, the continued
				// transformation of
			}
		}
		return null;
	}

	/**
	 * Upward cycle of transformation, to obtain the object DeclaredField.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * Upward cycle of transformation, to obtain the object DeclaredField.
	 */
	protected static Method getDeclaredMethod(Object object, String methodName,
			Class<?>[] parameterTypes) {
//		Assert.notNull(object, "object cannot null!");
		log.info("object cannot null!");
		
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				// Method Not in the current class definition, the continued
				// transformation of
			}
		}
		return null;
	}

	/**
	 * Through reflection, to obtain Class definition statement of the generic
	 * parameters of the parent class type of. eg. public UserDao extends
	 * HibernateDao<User>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be
	 *         determined
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * Through reflection, access to the definition of Class, declare that the
	 * parent class of the generic parameter type.
	 * 
	 * as public UserDao extends HibernateDao<User,Long>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be
	 *         determined
	 */

	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(final Class clazz,
			final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
//			log.warn(clazz.getSimpleName()
//					+ "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName()
					+ "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			log
					.warn(clazz.getSimpleName()
							+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * Extraction of the properties of the objects in the collection (via getter
	 * function), combined into a List.
	 * 
	 * @param collection
	 *            The source collection.
	 * @param propertyName
	 *            To extract the property name.
	 */
	@SuppressWarnings("unchecked")
	public static List fetchElementPropertyToList(final Collection collection,
			final String propertyName) {
		List list = new ArrayList();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			convertToUncheckedException(e);
		}

		return list;
	}

	/**
	 * Will be reflected when the checked exception into unchecked exception.
	 */
	public static IllegalArgumentException convertToUncheckedException(
			Exception e) {
		if (e instanceof IllegalAccessException
				|| e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException)
			return new IllegalArgumentException("Refelction Exception.", e);
		else
			return new IllegalArgumentException(e);
	}
}
