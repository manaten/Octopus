package net.manaten.octopus.old;

import java.util.Set;

import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.Name;

public class TranslationInfomation {
	private FunctionNode continuation;
	private Set<Name> boundVariables;

	public FunctionNode getContinuation() {
		return continuation;
	}

	public void setContinuation(FunctionNode continuation) {
		this.continuation = continuation;
	}

	public void setBoundVariables(Set<Name> boundVariables)
	{
		this.boundVariables = boundVariables;
	}

	public Set<Name> getBoundVariables()
	{
		return boundVariables;
	}
}
