package ru.ifmo.cs.computer_science.lab2.utils;

import java.io.Serializable;
import java.util.Objects;

public class YXFunctionWithNameAndRelativePath implements Serializable {
	private YXFunction function;
	private String name;
	private String relativePath;

	public YXFunctionWithNameAndRelativePath(YXFunction function, String name, String relativePath) {
		this.function = function;
		this.name = name;
		this.relativePath = relativePath;
	}

	public YXFunction getFunction() {
		return function;
	}

	public String getName() {
		return name;
	}

	public String getRelativePath() {
		return relativePath;
	}
}