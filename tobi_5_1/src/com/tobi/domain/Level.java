package com.tobi.domain;

public enum Level {
	//등급 BASIC, SILVER, GOLD
	BASIC(1), SILVER(2), GOLD(3);

	private final int value;

	Level(int value) {
		this.value = value;
	}

	public int intValue() {
		return value;
	}

	//DB 정보를 불러온 값과 등급 값 매칭
	public static Level valueOf(int value) {
		switch (value) {
			case 1:
				return BASIC;
			case 2:
				return SILVER;
			case 3:
				return GOLD;
			default:
				throw new AssertionError("Unknown value: " + value);
		}
	}
}
