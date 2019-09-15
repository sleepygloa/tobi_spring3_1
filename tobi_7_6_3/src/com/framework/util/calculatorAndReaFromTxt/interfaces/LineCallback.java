package com.framework.util.calculatorAndReaFromTxt.interfaces;

public interface LineCallback<T> {
	T doSomethingWithLine(String line, T value);
}
