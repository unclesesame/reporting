package com.abner.codebase.reporting.infra.impl;

public class CacheKey {
	private String version;
	
	private CacheKey(String version) {
		this.version = version;
	}
	
	public static CacheKey newCacheKey(String version) {
		return new CacheKey(version);
	}
	
	public String getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheKey other = (CacheKey) obj;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CacheKey [version=" + version + "]";
	}
	
}
