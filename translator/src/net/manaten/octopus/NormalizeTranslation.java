package net.manaten.octopus;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;

public class NormalizeTranslation extends DefaultTranslation
{
	public NormalizeTranslation(AstRoot root) {
		super(root);
	}

	@Override
	public String translate()
	{
		NTLevel1 NTLevel1 = new NTLevel1(root);
		//		return NTLevel1.translate();
		NTLevel2 NTLevel2 = new NTLevel2(NTLevel1.translateToNode());
		return NTLevel2.translate();
	}

	/**
	 * プロパティアクセスの繰り出し以外のノーマライズ
	 * @author mana
	 */
	private class NTLevel1 extends DefaultTranslation
	{
		public NTLevel1(AstRoot root)
		{
			super(root);
		}

		/**
		 * { ... } -> ...
		 */
		protected String translate(Scope node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			List<AstNode> kids = new LinkedList<AstNode>();
			for (Node kid : node)
				kids.add( (AstNode) kid );
			sb.append( translateStatements(kids, info));
			return sb.toString();
		}

		/**
		 * Switchは面倒なのでとりあえず後回し
		 */
		protected String translate(SwitchStatement node, TranslationInfomation info)
		{
			throw new TranslationException(node);
		}


		protected String translate(NewExpression node, TranslationInfomation info)
		{
			throw new TranslationException(node);
			//			StringBuilder sb = new StringBuilder();
			//			sb.append("new ");
			//			sb.append( translate(node.getTarget(), info) );
			//			sb.append("(");
			//			sb.append( translateList(node.getArguments(), info) );
			//			sb.append(")");
			//			if(node.getInitializer() != null)
			//			{
			//				sb.append(" ");
			//				sb.append( translate(node.getInitializer(), info) );
			//			}
			//			return sb.toString();
		}

		/**
		 * var a = x, b = y, c = z; -> var a = x; var b = y; var c = z;
		 */
		protected String translate(VariableDeclaration node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			for (AstNode kid : node.getVariables())
			{
				String translated = translate(kid, info);
				sb.append(Token.typeToName(node.getType()).toLowerCase());
				sb.append(" ");
				sb.append(translated);
				if (!(node.getParent() instanceof ForInLoop))
					sb.append(";\n");
			}
			return sb.toString();
		}

		/**
		 * R.P -> R['p']
		 */
		protected String translate(PropertyGet node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			sb.append( translate(node.getLeft(), info) );
			sb.append("['");
			sb.append( translate(node.getRight(), info) );
			sb.append("']");
			return sb.toString();
		}

		protected String translate(IfStatement node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder(32);
			sb.append("if (");
			sb.append( translate(node.getCondition(), info));
			sb.append(") {");
			sb.append( translate(node.getThenPart(), info) );
			sb.append("} else {");
			if(node.getElsePart() != null)
				sb.append( translate(node.getElsePart(), info) );
			sb.append("}\n");
			return sb.toString();
		}

		protected String translate(DoLoop node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("for(;;) {");
			sb.append( translate(node.getBody(), info) );
			if (node.getCondition() != null && node.getCondition().getType() != Token.TRUE)
			{
				sb.append(" if (!(");
				sb.append( translate(node.getCondition(), info) );
				sb.append(")) { break; } else {}");
			}
			sb.append("}");
			return sb.toString();
		}

		protected String translate(ForInLoop node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("for ");
			sb.append("(");
			sb.append( translate(node.getIterator(), info) );
			sb.append(" in ");
			sb.append( translate(node.getIteratedObject(), info) );
			sb.append(") {");
			sb.append( translate(node.getBody(), info) ).append("}\n");
			return sb.toString();
		}

		protected String translate(ForLoop node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			if (node.getInitializer() != null)
				sb.append( translate(node.getInitializer(), info) );
			sb.append("for (;;");
			sb.append( translate(node.getIncrement(), info) );
			sb.append(") {");
			if (node.getCondition() != null && node.getCondition().getType() != Token.TRUE)
			{
				sb.append(" if (!(");
				sb.append( translate(node.getCondition(), info) );
				sb.append(")) { break; } else {}");
			}
			sb.append( translate(node.getBody(), info) );
			sb.append( translate(node.getIncrement(), info) );
			sb.append(";}");
			return sb.toString();
		}

		protected String translate(WhileLoop node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("for(;;) {");
			if (node.getCondition() != null && node.getCondition().getType() != Token.TRUE)
			{
				sb.append(" if (!(");
				sb.append( translate(node.getCondition(), info) );
				sb.append(")) { break; } else {}");
			}
			sb.append( translate(node.getBody(), info) );
			sb.append("}");
			return sb.toString();
		}
	}


	/**
	 * プロパティアクセスの繰り出しと、ブロック最後にreturn or continue
	 * @author mana
	 */
	private class NTLevel2 extends DefaultTranslation
	{
		public NTLevel2(AstRoot root)
		{
			super(root);
		}

		protected String translateStatement(AstNode statement, TranslationInfomation info)
		{
			info = new TranslationInfomation();
			String translated = translate(statement, info);

			StringBuilder sb = new StringBuilder();
			Queue<String> nq = info.getNodeQueue();
			while (!nq.isEmpty())
				sb.append(nq.poll()).append(";\n");
			sb.append(translated);
			return sb.toString();
		}


		protected String translate(ExpressionStatement node, TranslationInfomation info)
		{
			// rec[prop] = exp; なら繰り出しをしない
			if (node.getExpression() instanceof Assignment)
			{
				Assignment as = (Assignment) node.getExpression();
				if (as.getLeft() instanceof ElementGet)
				{
					StringBuilder sb = new StringBuilder();
					ElementGet eg = (ElementGet) as.getLeft();
					sb.append( translate(eg.getTarget(), info) );
					sb.append('[' + translate(eg.getElement(), info) + "] = ");
					sb.append( translate(as.getRight(), info) + ";");
					return sb.toString();
				}
			}
			return super.translate(node, info);
		}


		/**
		 * var x = y;のyがEGまたはFCの場合は繰り出さない
		 */
		protected String translate(VariableInitializer node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			sb.append( translate(node.getTarget(), info));
			if(node.getInitializer() != null)
			{
				sb.append(" = ");
				if (node.getInitializer() instanceof ElementGet)
				{
					ElementGet eg = (ElementGet) node.getInitializer();
					sb.append( translate(eg.getTarget(), info) );
					sb.append('[' + translate(eg.getElement(), info) + "]");
				}
				else
					sb.append( translate(node.getInitializer(), info));
			}
			return sb.toString();
		}

		/**
		 * rec[prop] = exp -> var v = exp; rec[prop] = v; v
		 */
		protected String translate(Assignment node, TranslationInfomation info)
		{
			if (node.getLeft() instanceof ElementGet)
			{
				StringBuilder sb = new StringBuilder();
				String v = createFreeName();
				sb.append("var "+v+" = " + translate(node.getRight(), info) + ";");
				ElementGet eg = (ElementGet) node.getLeft();
				sb.append( translate(eg.getTarget(), info) );
				sb.append('[' + translate(eg.getElement(), info) + "] = " + v);
				info.addNodeQueue(sb.toString());
				return v;
			}
			return super.translate(node, info);
		}

		/**
		 * R[E] -> var v = trans(R)[trans(E)]; v
		 */
		protected String translate(ElementGet node, TranslationInfomation info)
		{
			String v = createFreeName();
			StringBuilder sb = new StringBuilder();
			sb.append("var "+v+" = ");
			sb.append( translate(node.getTarget(), info) );
			sb.append('[' + translate(node.getElement(), info) + "]");
			info.addNodeQueue(sb.toString());
			return v;
		}

		/**
		 * new F(a,...) -> var v = new trans(F)(trans(a...)); v
		 */
		protected String translate(NewExpression node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
	        sb.append("new ");
			sb.append( translate(node.getTarget(), info) );
			sb.append("(");
			sb.append( translateList(node.getArguments(), info) );
			sb.append(")");
			if (node.getParent() instanceof VariableInitializer || node.getParent() instanceof ExpressionStatement)
				return sb.toString();
			String v = createFreeName();
			info.addNodeQueue("var "+v+" = " + sb.toString());
			return v;
		}

		/**
		 * F(a,...) -> var v = trans(F)(trans(a...)); v
		 */
		protected String translate(FunctionCall node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			if (node.getTarget() instanceof ElementGet)
			{
				ElementGet eg = (ElementGet) node.getTarget();
				//もともとapplyの場合
				if (eg.getElement() instanceof StringLiteral && ((StringLiteral)eg.getElement()).getValue().equals("apply"))
				{
					sb.append( translate(eg.getTarget(), info) );
					sb.append("['apply'](");
					sb.append( translateList(node.getArguments(), info) );
					sb.append(")");
				}
				else
				{
					String reciever = translate(eg.getTarget(), info);
					String method = createFreeName();
					StringBuilder sb2 = new StringBuilder();
					sb2.append("var "+method+" = ");
					sb2.append( reciever );
					sb2.append('[' + translate(eg.getElement(), info) + "];");
					info.addNodeQueue(sb2.toString());

					sb.append( method );
					sb.append("['apply']("+reciever+", [");
					sb.append( translateList(node.getArguments(), info) );
					sb.append("])");
				}
			}
			else
			{
				sb.append( translate(node.getTarget(), info) );
				sb.append("['apply'](null, [");
				sb.append( translateList(node.getArguments(), info) );
				sb.append("])");
			}
			if (node.getParent() instanceof VariableInitializer || node.getParent() instanceof ExpressionStatement)
				return sb.toString();
			String v = createFreeName();
			info.addNodeQueue("var "+v+" = " + sb.toString());
			return v;
		}

		/**
		 * function f(...) {...} -> var f = function(...) { ... }
		 */
		protected String translate(FunctionNode node, TranslationInfomation info)
		{

			if (node.getParent() instanceof AstRoot || node.getParent() instanceof Block || node.getParent() instanceof Scope)
			{
				StringBuilder sb = new StringBuilder();
				sb.append("var ");
				sb.append( translate(node.getFunctionName(), info) );
				sb.append(" = ");
				sb.append("function");
				sb.append("(");
				sb.append( translateList(node.getParams(), info) );
				sb.append(") ");
				sb.append( translateFuncBody(node.getBody(), info) );
				sb.append(";\n");
				return sb.toString();
			}
			else if (node.getParent() instanceof VariableInitializer)
			{
				StringBuilder sb = new StringBuilder();
				sb.append("function");
				sb.append("(");
				sb.append( translateList(node.getParams(), info) );
				sb.append(") ");
				sb.append( translateFuncBody(node.getBody(), info) );
				return sb.toString();
			}
			else
			{
				StringBuilder sb = new StringBuilder();
				String n = (node.getFunctionName() != null) ? node.getFunctionName().getIdentifier() : createFreeName();
				sb.append("var ");
				sb.append( n );
				sb.append(" = ");
				sb.append("function");
				sb.append("(");
				sb.append( translateList(node.getParams(), info) );
				sb.append(") ");
				sb.append( translateFuncBody(node.getBody(), info) );
				sb.append(";\n");
				info.addNodeQueue(sb.toString());
				return n;
			}
		}

		// 最後にかならずreturnするようにする
		private String translateFuncBody(AstNode body, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("{\n");

			List<AstNode> kids = new LinkedList<AstNode>();
			for (Node kid : body)
				kids.add( (AstNode) kid );

			for (AstNode kid : kids)
				sb.append( translateStatement(kid, info));

			if (kids.isEmpty() || !(kids.get(kids.size()-1) instanceof ReturnStatement))
				sb.append("return;");

			sb.append("}\n");
			return sb.toString();
		}

		protected String translate(ForLoop node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("for (;;) {");
			List<AstNode> kids = new LinkedList<AstNode>();
			for (Node kid : node.getBody())
				kids.add( (AstNode) kid );
			for (AstNode kid : kids)
				sb.append( translateStatement(kid, info));
			if ( kids.isEmpty() || !(kids.get(kids.size()-1) instanceof ContinueStatement) && !(kids.get(kids.size()-1) instanceof BreakStatement) )
				sb.append("continue;");
			sb.append("}");
			return sb.toString();
		}

		protected String translate(ForInLoop node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("for ");
			sb.append("(");
			sb.append( translate(node.getIterator(), info) );
			sb.append(" in ");
			sb.append( translate(node.getIteratedObject(), info) );
			sb.append(") {");
			List<AstNode> kids = new LinkedList<AstNode>();
			for (Node kid : node.getBody())
				kids.add( (AstNode) kid );
			for (AstNode kid : kids)
				sb.append( translateStatement(kid, info));
			if ( kids.isEmpty() || !(kids.get(kids.size()-1) instanceof ContinueStatement) && !(kids.get(kids.size()-1) instanceof BreakStatement) )
				sb.append("continue;");
			sb.append("}");
			return sb.toString();
		}
	}

}
