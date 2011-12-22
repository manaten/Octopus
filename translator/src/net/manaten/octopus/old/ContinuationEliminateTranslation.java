package net.manaten.octopus.old;

import java.util.HashMap;
import java.util.Map;


import org.mozilla.javascript.Node;
import org.mozilla.javascript.ast.*;

public class ContinuationEliminateTranslation extends DefaultTranslation
{
	private Map<String, AstNode> paramMap = new HashMap<String, AstNode>();

	@Override
	public AstNode translate(Block node, TranslationInfomation info)
	{
		if (node == null) return null;
		//Blockのネストはみっともないので治す
		Block result = new Block();
		for (Node kid : node)
		{
			AstNode kid_ = translate((AstNode)kid, info);
			if (kid_ instanceof Block)
				for (Node kidkid : kid_)
					result.addChild((AstNode) kidkid);
			else
				result.addChild(kid_);
		}
		return result;
	}

	@Override
	public AstNode translate(AstRoot node, TranslationInfomation info)
	{
		if (node == null) return null;
		//Blockのネストはみっともないので治す
		AstRoot result = new AstRoot();
		for (Node kid : node)
		{
			AstNode kid_ = translate((AstNode)kid, info);
			if (kid_ instanceof Block)
				for (Node kidkid : kid_)
					result.addChild((AstNode) kidkid);
			else
				result.addChild(kid_);
		}
		return result;
	}

	@Override
	public AstNode translate(Scope node, TranslationInfomation info)
	{
		if (node == null) return null;
		if (node instanceof ArrayComprehension) return translate((ArrayComprehension) node, info);
		if (node instanceof LetNode) return translate((LetNode) node, info);
		if (node instanceof Loop) return translate((Loop) node, info);
		if (node instanceof ScriptNode) return translate((ScriptNode) node, info);

		//Blockのネストはみっともないので治す
		Scope result = new Scope();
		for (Node kid : node)
		{
			AstNode kid_ = translate((AstNode)kid, info);
			if (kid_ instanceof Block)
				for (Node kidkid : kid_)
					result.addChild((AstNode) kidkid);
			else
				result.addChild(kid_);
		}
		return result;
	}

	@Override
	public AstNode translate(ExpressionStatement node, TranslationInfomation info)
	{
		if (node == null) return null;

		// 生成されたContinuatioinNodeの場合、戻せるなら戻す操作をする
		if (node.getExpression() instanceof FunctionCall)
		{
			FunctionCall fc = (FunctionCall) node.getExpression();
			if (fc.getTarget() instanceof ParenthesizedExpression)
			{
				ParenthesizedExpression pe = (ParenthesizedExpression) fc.getTarget();
				if (pe.getExpression() instanceof ContinuationNode)
				{
					ContinuationNode cn = (ContinuationNode) pe.getExpression();
					Name param = (Name) cn.getParams().get(0);
					AstNode target = fc.getArguments().isEmpty() ? null : fc.getArguments().get(0);
					if (target != null)
						paramMap.put(param.getIdentifier(), translate(target, info));
//					else
//						paramMap.put(param.getIdentifier(), translate(fc.getTarget(), info));
					return translate(cn.getBody(), info);
				}
			}
		}

		return super.translate(node, info);
	}

	@Override
	public AstNode translate(Name node, TranslationInfomation info)
	{
		if (paramMap.containsKey(node.getIdentifier()))
			return paramMap.get(node.getIdentifier());
		return super.translate(node, info);
	}
}
