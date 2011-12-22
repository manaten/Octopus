package net.manaten.octopus;

import org.mozilla.javascript.ast.AstNode;


@SuppressWarnings("serial")
public class TranslationException extends RuntimeException
{
	private AstNode innerNode;

	public TranslationException(String msg, AstNode node)
	{
		super(msg + "\n In " + node.getClass().toString() +
				"\n------------------------------" + node.toSource() + "\n------------------------------");
		this.innerNode = node;
	}

	public TranslationException(AstNode node)
	{
		super("unsupported translation of " + node.getClass().toString() +
				"\n------------------------------" + node.toSource() + "\n------------------------------");
		this.innerNode = node;
	}
	public AstNode getInnerNode()
	{
		return innerNode;
	}
}
