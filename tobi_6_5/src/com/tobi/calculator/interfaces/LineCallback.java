package com.tobi.calculator.interfaces;

public interface LineCallback<T> {
	T doSomethingWithLine(String line, T value);
}
