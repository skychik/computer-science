package ru.ifmo.cs.computer_science.lab4.utils;

import ru.ifmo.cs.computer_science.lab5.utils.FXYZFunction;

import java.io.Serializable;

public class FYXFunctionSystemWithNameAndRelativePath implements Serializable {
	private FYXFunction function;
	private String name;
	private String relativePath;

	public FYXFunctionSystemWithNameAndRelativePath(FYXFunction function, String name, String relativePath) {
		this.function = function;
		this.name = name;
		this.relativePath = relativePath;
	}

	public FYXFunction getFunction() {return function;}

	public String getName() {
		return name;
	}

	public String getRelativePath() {
		return relativePath;
	}
}