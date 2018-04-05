package com.abner.codebase.reporting;

import com.abner.codebase.reporting.job.ReportingJob;

public class Main {
	private Main() {
	}

	public static void main(String[] args) {
		System.out.println(args);
		new ReportingJob().start(args);
	}
}
