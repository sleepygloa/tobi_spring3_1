package com.tobi.interfaces;

public interface LineCallback<T> {
	T doSomethingWithLine(String line, T value);
}
