package com.forum.java.algos;

import java.util.Arrays;

public class Statistics {

	double getMean(double[] data) {
		double sum = 0.0;
		for (double a : data)
			sum += a;
		System.out.println("Sum is "+sum+" data len: "+data.length);
		return sum / data.length;
	}

	double getVariance(double[] data) {
		double mean = getMean(data);
		double temp = 0;
		for (double a : data)
			temp += (mean - a) * (mean - a);
		return temp / data.length;
	}

	double getStdDev(double[] data) {
		return Math.sqrt(getVariance(data));
	}

	public double median(double[] data) {
		Arrays.sort(data);

		if (data.length % 2 == 0) {
			return (data[(data.length / 2) - 1] + data[data.length / 2]) / 2.0;
		} else {
			return data[data.length / 2];
		}
	}
	
	public static void main(String[] args) {
		Statistics stats = new Statistics();
		double[] data = {-1.36, -1.21, -0.91, -0.76, -0.45, -0.15, 0.45, 0.45, 0.76, 1.36, 1.82};
		System.out.println("Mean is "+stats.getMean(data));
		System.out.println("SD is "+stats.getStdDev(data));
	}
}