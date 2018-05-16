package ru.ifmo.cs.computer_science.lab5.utils;

import java.io.Serializable;

public class FXYZFunctionWithNameAndRelativePath implements Serializable {
	private FXYZFunction f1;
	private FXYZFunction f2;
	private String name;
	private String relativePath;

	public FXYZFunctionWithNameAndRelativePath(FXYZFunction f1, FXYZFunction f2, String name, String relativePath) {
		this.f1 = f1;
		this.f2 = f2;
		this.name = name;
		this.relativePath = relativePath;
	}
	public FXYZFunction getFirstFunction() {
		return f1;
	}

	public FXYZFunction getSecondFunction() {
		return f2;
	}

	public String getName() {
		return name;
	}

	public String getRelativePath() {
		return relativePath;
	}
}