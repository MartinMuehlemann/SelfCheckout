package com.swisshof.selfcheckout.statemachine.generic;

public interface Event<T> {
	public T getEvent();
}
