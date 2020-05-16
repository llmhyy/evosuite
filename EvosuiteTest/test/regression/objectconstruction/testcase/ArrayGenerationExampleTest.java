package regression.objectconstruction.testcase;

import java.lang.reflect.Method;

import org.evosuite.utils.MethodUtil;
import org.junit.Test;

import com.test.TestUility;

import evosuite.shell.EvoTestResult;

public class ArrayGenerationExampleTest extends DebugSetup {

	@Test
	public void testArrayGenerationExample() {
		Class<?> clazz = regression.objectconstruction.example.ArrayGenerationExample.class;

		String methodName = "target";
		int parameterNum = 0;

		String targetClass = clazz.getCanonicalName();
		Method method = TestUility.getTargetMethod(methodName, clazz, parameterNum);

		String targetMethod = method.getName() + MethodUtil.getSignature(method);
		String cp = "target/test-classes";

		String fitnessApproach = "fbranch";

		int timeBudget = 100;
		EvoTestResult result = TestUility.evosuite(targetClass, targetMethod, cp, timeBudget, true, fitnessApproach);
		
		int age = result.getAge();
		int time = result.getTime();
		double coverage = result.getCoverage();
		assert age <= 10;
		assert time <= 5;
		assert coverage == 1.0;
	}
	
}