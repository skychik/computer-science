package ru.ifmo.cs.computer_science.lab2;


import org.junit.Test;
import ru.ifmo.cs.computer_science.lab2.utils.DefinedIntegralSimpsonMethod;
import ru.ifmo.cs.computer_science.lab2.utils.YXFunction;

import static java.lang.Math.*;

public class Test1 {
	@Test(timeout = 4000)
	public void testSimpsonMethod() {
		assertEqual(x -> 1, 0, 1, 1);
		assertEqual(x -> 2*x*x, 1, 2, 14.0/3.0);
		assertEqual(x -> 8+2*x-x*x, -2, 4, 36);
		assertEqual(x -> exp(2*x), 1, 2, (exp(4) - exp(2))/2);
		//assertEqual(x -> x*pow(tan(x), 2), 0, PI/2+0.1, PI/4-PI*PI/32+log(1/sqrt(2)));
	}

	private void assertEqual(YXFunction function, double lowerLimit, double higherLimit, double answer) {
		DefinedIntegralSimpsonMethod integral;
		try {
			integral = new DefinedIntegralSimpsonMethod(function, lowerLimit, higherLimit, 0.00000001);
			integral.integrate();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
			assert false;
			return;
		}

		System.out.println("assert |" + integral.getDefinedIntegral() + " - " + answer + "| <= " + 0.000000001);
		assert abs(integral.getDefinedIntegral() - answer) <= 0.0000001;
	}
}
