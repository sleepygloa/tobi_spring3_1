package com.tobi.domain;

public enum Level {
	//등급 BASIC, SILVER, GOLD
	GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);

	private final int value;
	private final Level next;

	Level(int value, Level next) {
		this.value = value;
		this.next = next;
	}

	public int intValue() {
		return value;
	}

	public Level nextLevel() {
		return this.next;
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
