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


		//		protected String translate(Block node, TranslationInfomation info)
		//		{
		//			StringBuilder sb = new StringBuilder();
		//			List<AstNode> kids = new LinkedList<AstNode>();
		//			for (Node kid : node)
		//				kids.add( (AstNode) kid );
		//			sb.append( translateStatements(kids, info));
		//			return sb.toString();
		//		}

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

			if(node.getElsePart() != null)
			{
				sb.append("} else {");
				sb.append( translate(node.getElsePart(), info) );
			}
			sb.append("}\n");
			return sb.toString();
		}

		protected String translate(DoLoop node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("while(true) {");
			sb.append( translate(node.getBody(), info) );
			if (node.getCondition().getType() != Token.TRUE)
			{
				sb.append(" if (!(");
				sb.append( translate(node.getCondition(), info) );
				sb.append(")) { break; }");
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
			sb.append( translate(node.getInitializer(), info) );
			sb.append("; while (true) {");
			sb.append(" if (!(");
			sb.append( translate(node.getCondition(), info) );
			sb.append(")) { break; } ");
			sb.append( translate(node.getBody(), info) );
			sb.append( translate(node.getIncrement(), info) );
			sb.append("; ");
			sb.append("}");
			return sb.toString();
		}

		protected String translate(WhileLoop node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("while(true) {");
			if (node.getCondition().getType() != Token.TRUE)
			{
				sb.append(" if (!(");
				sb.append( translate(node.getCondition(), info) );
				sb.append(")) { break; } ");
			}
			sb.append( translate(node.getBody(), info) );
			sb.append("}");
			return sb.toString();
		}
	}


	/**
	 * プロパティアクセスの繰り出し
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
			// FC(); なら繰り出しをしない
			if (node.getExpression() instanceof FunctionCall)
				return super.translate( (FunctionCall) node.getExpression(), info) + ";";
			return super.translate(node, info);
		}


		/**
		 *
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
				else if (node.getInitializer() instanceof FunctionCall)
				{
					FunctionCall fc = (FunctionCall) node.getInitializer();
					sb.append( translate(fc.getTarget(), info) );
					sb.append("(");
					sb.append( translateList(fc.getArguments(), info) );
					sb.append(")");
				}
				else
					sb.append( translate(node.getInitializer(), info));
			}
			return sb.toString();
		}

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
		 * F[a,...] -> var v = trans(F)(trans(a...)); v
		 */
		protected String translate(FunctionCall node, TranslationInfomation info)
		{
			String v = createFreeName();
			StringBuilder sb = new StringBuilder();
			sb.append("var "+v+" = ");
			sb.append( translate(node.getTarget(), info) );
			sb.append("(");
			sb.append( translateList(node.getArguments(), info) );
			sb.append(")");
			info.addNodeQueue(sb.toString());
			return v;
		}
	}

}
