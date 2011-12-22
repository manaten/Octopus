package net.manaten.octopus;


import java.util.Queue;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;

public class NormalizeTranslation extends DefaultTranslation
{

	public NormalizeTranslation(AstRoot root) {
		super(root);
	}

	public String translate(AstNode node, TranslationInfomation info)
	{
		String result = super.translate(node, info);
		info.setExpressionTop(false);
		return result;
	}

	protected String translate(SwitchStatement node, TranslationInfomation info)
	{
		throw new TranslationException(node);
	}


	protected String translate(ExpressionStatement statement, TranslationInfomation info)
	{
		info.setExpressionTop(true);
		StringBuilder sb = new StringBuilder();
		String translated = translate(statement.getExpression(), info);
		Queue<String> nq = info.getNodeQueue();
		while (!nq.isEmpty())
			sb.append(nq.poll()).append(";");
		sb.append(translated);
		return sb.toString() + ";";
	}

	protected String translate(VariableDeclaration node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		for (AstNode kid : node.getVariables())
		{
			info.setExpressionTop(true);
			String translated = translate(kid, info);
			Queue<String> nq = info.getNodeQueue();
			while (!nq.isEmpty())
				sb.append(nq.poll()).append(";");

			sb.append(Token.typeToName(node.getType()).toLowerCase());
			sb.append(" ");
			sb.append(translated);
			sb.append(";\n");
		}
		return sb.toString();
	}

	protected String translate(Assignment node, TranslationInfomation info)
	{
		boolean isTop = info.isExpressionTop();
		info.setExpressionTop(false);
		if (node.getLeft() instanceof ElementGet)
		{
			StringBuilder sb = new StringBuilder();
			if (isTop)
			{
				ElementGet eg = (ElementGet) node.getLeft();
				sb.append( translate(eg.getTarget(), info) );
				sb.append('[' + translate(eg.getElement(), info) + "] = ");
				sb.append( translate(node.getRight(), info) );
				return sb.toString();
			}
			String v = createFreeName();
			sb.append("var "+v+" = (");
			ElementGet eg = (ElementGet) node.getLeft();
			sb.append( translate(eg.getTarget(), info) );
			sb.append('[' + translate(eg.getElement(), info) + "] = ");
			sb.append( translate(node.getRight(), info) + ")");
			info.addNodeQueue(sb.toString());
			return v;
		}
		if (node.getLeft() instanceof PropertyGet)
		{
			StringBuilder sb = new StringBuilder();
			if (isTop)
			{
				PropertyGet pg = (PropertyGet) node.getLeft();
				sb.append( translate(pg.getTarget(), info) );
				sb.append("['" + pg.getProperty().getIdentifier() + "'] = ");
				sb.append( translate(node.getRight(), info) );
				return sb.toString();
			}
			String v = createFreeName();
			sb.append("var "+v+" = (");
			PropertyGet pg = (PropertyGet) node.getLeft();
			sb.append( translate(pg.getTarget(), info) );
			sb.append("['" + pg.getProperty().getIdentifier() + "'] = ");
			sb.append( translate(node.getRight(), info) + ")");
			info.addNodeQueue(sb.toString());
			return v;
		}
		return super.translate(node, info);
	}

	//TODO ここから作業再開 2011-12-21
	protected String translate(PropertyGet node, TranslationInfomation info)
	{
		boolean isTop = info.isExpressionTop();
		info.setExpressionTop(false);

		String v = createFreeName();
		StringBuilder sb = new StringBuilder();
		sb.append("var "+v+" = ");
		sb.append( translate(node.getTarget(), info) );
		sb.append("['" + node.getProperty().getIdentifier() + "']");
		if (isTop)
			return sb.toString();
		info.addNodeQueue(sb.toString());
		return v;
	}

	protected String translate(ElementGet node, TranslationInfomation info)
	{
		boolean isTop = info.isExpressionTop();
		info.setExpressionTop(false);

		String v = createFreeName();
		StringBuilder sb = new StringBuilder();
		sb.append("var "+v+" = ");
		sb.append( translate(node.getTarget(), info) );
		sb.append('[' + translate(node.getElement(), info) + "]");
		if (isTop)
			return sb.toString();
		info.addNodeQueue(sb.toString());
		return v;
	}

	protected String translate(FunctionCall node, TranslationInfomation info)
	{
		boolean isTop = info.isExpressionTop();
		info.setExpressionTop(false);

		String v = createFreeName();
		StringBuilder sb = new StringBuilder();
		sb.append("var "+v+" = ");
		sb.append( translate(node.getTarget(), info) );
		sb.append("(");
		sb.append( translateList(node.getArguments(), info) );
		sb.append(")");
		if (isTop)
			return sb.toString();
		info.addNodeQueue(sb.toString());
		return v;
	}

}
