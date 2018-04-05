package com.abner.codebase.reporting.config;

public enum ReportConfig implements RuntimeProperties{

	
	REPORT_NAME {
		@SuppressWarnings("unchecked")
		@Override
		public String getValue() {
			return "report";
		}

		@Override
		public String getKey() {
			return S_REPORT_NAME;
		}

		@Override
		public String getDescription() {
			return REPORT_NAME_DESCRIPTION;
		}

		@Override
		public String getLongKey() {
			return L_REPORT_NAME;
		}
	};
	
	
	private static final String S_REPORT_NAME = "n";
	private static final String L_REPORT_NAME = "reportingName";
	private static final String REPORT_NAME_DESCRIPTION = "Report Name";
}
