package com.test;

import java.lang.reflect.Method;

import org.evosuite.Properties;
import org.evosuite.Properties.StatisticsBackend;
import org.evosuite.utils.MethodUtil;
import org.junit.Test;

import com.example.C;

public class ConstructorTest {

	@Test
	public void test() {
		Class<?> clazz = C.class;
		String methodName = "test";
		int parameterNum = 2;

		String targetClass = clazz.getCanonicalName();
		//			Method method = clazz.getMethods()[0];
		Method method = TestUility.getTragetMethod(methodName, clazz, parameterNum);

		String targetMethod = method.getName() + MethodUtil.getSignature(method);
		String cp = "target/classes";

		// Properties.LOCAL_SEARCH_RATE = 1;
		//			Properties.DEBUG = true;
		//			Properties.PORT = 8000;
		Properties.CLIENT_ON_THREAD = true;
		Properties.STATISTICS_BACKEND = StatisticsBackend.DEBUG;

		Properties.TIMEOUT = 10000000;
		//			Properties.TIMELINE_INTERVAL = 3000;

		String fitnessApproach = "fbranch";

		int timeBudget = 30000;
		TestUility.evosuite(targetClass, targetMethod, cp, timeBudget, true, fitnessApproach);
	}
}
