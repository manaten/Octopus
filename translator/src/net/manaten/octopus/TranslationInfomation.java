package net.manaten.octopus;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.mozilla.javascript.ast.AstNode;

public class TranslationInfomation {
	private List<AstNode> continuation;
	private final Queue<String> addQueue;
	private boolean isExpressionTop;

	public boolean isExpressionTop()
	{
		return isExpressionTop;
	}

	public void setExpressionTop(boolean isExpressionTop)
	{
		this.isExpressionTop = isExpressionTop;
	}

	public TranslationInfomation()
	{
		addQueue = new LinkedList<String>();
	}

	public void addNodeQueue(String node)
	{
		addQueue.add(node);
	}

	public Queue<String> getNodeQueue()
	{
		return addQueue;
	}

	public List<AstNode> getContinuation() {
		return continuation;
	}

	public void setContinuation(List<AstNode> continuation) {
		this.continuation = continuation;
	}

}
