package com.test;

import org.junit.Test;

import org.evosuite.Properties;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.TestFactory;
import org.evosuite.testcase.statements.PrimitiveStatement;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class EmpiricalStudyTest {
	@Test
	public <T> void testMapGeneration() {
		// Force TestFactory to generate true random value, 
		// instead of choosing value from pool
		Properties.PRIMITIVE_POOL = 0.0;
		// Increase range for numeric variables
		Properties.MAX_INT = Integer.MAX_VALUE / 3;
		
		// Get Methods
		Class classObj = EmpiricalStudyTest.class;
		Method[] methods = classObj.getDeclaredMethods();
		
		for (Method method : methods) {
			if (!method.getName().contains("method")) {
				continue;
			}
			
			System.out.println("\n" + "Invoking method: " + method.getName());
			
			// Get parameter types
			Class<T> clazz = getParameterType(method);
						
			DefaultTestCase test = new DefaultTestCase();
			try {
				// Generate X number of test cases (inputs)
				for (int j = 0; j < 10; j++) {
					TestFactory.getInstance().attemptGeneration(test, (Type) clazz, 0);
					PrimitiveStatement<Type> st = (PrimitiveStatement<Type>) test.getStatement(0);
					T input = castToPrimitive(st.getValue(), clazz);
					
					// Invoke method with inputs and get output
					try {
						System.out.print("input: " + input + ", ");
						Object output = method.invoke(null, input);
						System.out.println("output: " + output);
					} catch (Exception e) {
						System.out.println("Error " + e);
					}
				}
				// System.out.println(test);
				
			} catch (ConstructionFailedException e) {
				System.out.println("Error " + e);
			}
		}
	}
	
	public <T> Class<T> getParameterType(Method method) {
		Class<T> c = (Class<T>) method.getParameterTypes()[0];
		return c;
	}
	
	public <T> T castToPrimitive(Object obj, Class<T> clazz) {
		try {
			// primitive types has error when trying to cast, 
			// have to use their class type to cast
			String typeName = clazz.getTypeName();
			switch (typeName) {
				case "int":
					return (T) Integer.class.cast(obj);
				case "long":
					return (T) Long.class.cast(obj);
				case "double":
					return (T) Double.class.cast(obj);
				case "float":
					return (T) Float.class.cast(obj);
				case "byte":
					return (T) Byte.class.cast(obj);
				case "short":
					return (T) Short.class.cast(obj);
				case "char":
					return (T) Character.class.cast(obj);
			}
			
			return clazz.cast(obj);
		} catch (ClassCastException e) {
			return null;
		}
	}
	
	public static int methodInt(int x) {
		if (x > 5000) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public static int methodDouble(double x) {
		if (x > 10000.101) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public static int methodLong(long x) {
		if (x > 500000) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public static char methodChar(char c) {
		if (c == 'A' || c == 'B') {
			return '0';
		} else if (c == 'C' || c == 'D'){
			return '1';
		} else {
			return '2';
		}
	}
	
	public static String methodString(String s) {
		if (s.length() > 10) {
			return "ZERO";
		} else {
			return "ONE";
		}
	} 
}
