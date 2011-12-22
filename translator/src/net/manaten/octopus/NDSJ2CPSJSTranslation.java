package net.manaten.octopus;

import java.util.LinkedList;

import org.mozilla.javascript.Node;
import org.mozilla.javascript.ast.*;

public class NDSJ2CPSJSTranslation extends DefaultTranslation
{
	public NDSJ2CPSJSTranslation(AstRoot root) {
		super(root);
	}

	private LinkedList<AstNode> getChildren(AstNode node)
	{
		LinkedList<AstNode> nodes = new LinkedList<AstNode>();
		for (Node kid : node)
			nodes.add((AstNode) kid);
		return nodes;
	}

	private LinkedList<AstNode> translateStatements(LinkedList<AstNode> nodes)
	{
		return nodes;
	}

	public String translate(Block node, TranslationInfomation info)
	{
		if (node == null) return null;
		LinkedList<AstNode> sts = translateStatements(getChildren(node));
		Block result = new Block();
		for (AstNode kid : sts)
			result.addChild(kid);
		return null;
	}

	@Override
	public String translate(AstRoot node, TranslationInfomation info)
	{
		if (node == null) return null;
		LinkedList<AstNode> sts = translateStatements(getChildren(node));
		AstRoot result = new AstRoot();
		for (AstNode kid : sts)
			result.addChild(kid);
		return null;
	}

	@Override
	public String translate(Scope node, TranslationInfomation info)
	{
		if (node == null) return null;
		if (node instanceof ArrayComprehension) return translate((ArrayComprehension) node, info);
		if (node instanceof LetNode) return translate((LetNode) node, info);
		if (node instanceof Loop) return translate((Loop) node, info);
		if (node instanceof ScriptNode) return translate((ScriptNode) node, info);
		throw new TranslationException(node);
	}
}
