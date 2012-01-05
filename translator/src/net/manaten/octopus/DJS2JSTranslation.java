package net.manaten.octopus;

import java.util.LinkedList;
import java.util.List;

import org.mozilla.javascript.Node;
import org.mozilla.javascript.ast.*;

public class DJS2JSTranslation extends DefaultTranslation
{
	public DJS2JSTranslation(AstRoot root) {
		super(root);
	}

	@Override
	public String translate()
	{
		NormalizeTranslation ntrans = new NormalizeTranslation(root);
		NDJS2CPSJSTranslation nd2j = new NDJS2CPSJSTranslation(ntrans.translateToNode());
		return nd2j.translate();
	}

	/**
	 * N-formed D-JS to CPS-JS
	 * @author mana
	 */
	private class NDJS2CPSJSTranslation extends DefaultTranslation
	{
		private class NDJS2CPSJSInfo extends TranslationInfomation
		{
			private String breakPoint, continuePoint, returnPoint, kPoint;

			public NDJS2CPSJSInfo()
			{
				super();
			}

			public NDJS2CPSJSInfo(NDJS2CPSJSInfo info)
			{
				super();
				breakPoint = info.breakPoint;
				continuePoint = info.continuePoint;
				returnPoint = info.returnPoint;
				kPoint = info.kPoint;
			}

			public void setContinuePoint(String continuePoint) {
				this.continuePoint = continuePoint;
			}

			public String getContinuePoint() {
				return continuePoint;
			}

			public void setBreakPoint(String breakPoint) {
				this.breakPoint = breakPoint;
			}

			public String getBreakPoint() {
				return breakPoint;
			}

			public void setReturnPoint(String returnPoint) {
				this.returnPoint = returnPoint;
			}

			public String getReturnPoint() {
				return returnPoint;
			}

			public void setkPoint(String kPoint) {
				this.kPoint = kPoint;
			}

			public String getkPoint() {
				return kPoint;
			}

		}

		public NDJS2CPSJSTranslation(AstRoot root)
		{
			super(root);
		}

		public String translate()
		{
			return translate(root, new NDJS2CPSJSInfo());
		}

		private boolean isFunctionDecl(AstNode node)
		{
			if (node instanceof VariableDeclaration)
			{
				VariableDeclaration vd = (VariableDeclaration) node;
				VariableInitializer vi = vd.getVariables().get(0);
				if (vi.getInitializer() instanceof FunctionNode)
				{
					return true;
				}
			}
			return false;
		}

		private boolean isPropertySet(AstNode node)
		{
			if (node instanceof ExpressionStatement)
			{
				ExpressionStatement es = (ExpressionStatement) node;
				if (es.getExpression() instanceof Assignment)
				{
					Assignment ass = (Assignment) es.getExpression();
					if (ass.getLeft() instanceof ElementGet)
					{
						return true;
					}
				}

			}
			return false;
		}

		private boolean isPropertyGet(AstNode node)
		{
			if (node instanceof VariableDeclaration)
			{
				VariableDeclaration vd = (VariableDeclaration) node;
				VariableInitializer vi = vd.getVariables().get(0);
				if (vi.getInitializer() instanceof ElementGet)
				{
					return true;
				}
			}
			return false;
		}

		private boolean isFunctionCall(AstNode node)
		{
			if (node instanceof ExpressionStatement)
			{
				ExpressionStatement es = (ExpressionStatement) node;
				if (es.getExpression() instanceof FunctionCall)
				{
					return true;
				}
			}
			return false;
		}

		private boolean isFunctionCallGet(AstNode node)
		{
			if (node instanceof VariableDeclaration)
			{
				VariableDeclaration vd = (VariableDeclaration) node;
				VariableInitializer vi = vd.getVariables().get(0);
				if (vi.getInitializer() instanceof FunctionCall)
				{
					return true;
				}
			}
			return false;
		}

		private String translateFunctionDecl(AstNode node, List<AstNode> k, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			VariableDeclaration vd = (VariableDeclaration) node;
			VariableInitializer vi = vd.getVariables().get(0);
			FunctionNode fn = (FunctionNode) vi.getInitializer();
			String return_k = createFreeName(RETRUN_PREFIX);
			NDJS2CPSJSInfo info_ = new NDJS2CPSJSInfo((NDJS2CPSJSInfo)info);
			info_.setReturnPoint(return_k);

			String funcName =  translate(vi.getTarget(), info);
			sb.append("var ");
			sb.append( funcName );
			sb.append(" = ");
			sb.append("function");
			sb.append("(");
			String transArgs = translateList(fn.getParams(), info);
			if (transArgs.equals(""))
				sb.append( return_k );
			else
				sb.append(transArgs + ", " + return_k);
			sb.append(") {");
			sb.append("if ("+return_k+" === undefined) {"+return_k+" = function(){};}");
			sb.append( translate(fn.getBody(), info_) );
			sb.append("};\n");
			sb.append(funcName + ".isUserDefined = true;");

			return sb.toString();
		}

		private String translateFunctionCall(AstNode node, List<AstNode> k, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			ExpressionStatement es = (ExpressionStatement) node;
			FunctionCall fc = (FunctionCall) es.getExpression();
			ElementGet eg = (ElementGet) fc.getTarget();

			sb.append( translate(eg.getTarget(), info) );
			sb.append(".apply_cps(");
			sb.append( translateList(fc.getArguments(), info) );
			sb.append(", function () {\n");
			sb.append( translateStatements(k, info) );
			sb.append("});\n");

			return sb.toString();
		}

		private String translateFunctionCallGet(AstNode node, List<AstNode> k, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			VariableDeclaration vd = (VariableDeclaration) node;
			VariableInitializer vi = vd.getVariables().get(0);
			FunctionCall fc = (FunctionCall) vi.getInitializer();
			ElementGet eg = (ElementGet) fc.getTarget();

			sb.append( translate(eg.getTarget(), info) );
			sb.append(".apply_cps(");
			sb.append( translateList(fc.getArguments(), info) );
			sb.append(", function (");
			sb.append( translate(vi.getTarget(), info) );
			sb.append(") {\n");
			sb.append( translateStatements(k, info) );
			sb.append("});\n");

			return sb.toString();
		}

		private String translatePropertyGet(AstNode node, List<AstNode> k, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			VariableDeclaration vd = (VariableDeclaration) node;
			VariableInitializer vi = vd.getVariables().get(0);
			ElementGet eg = (ElementGet) vi.getInitializer();

			sb.append( translate(eg.getTarget(), info) );
			sb.append(".get_cps(");
			sb.append( translate(eg.getElement(), info));
			sb.append(", function(");
			sb.append( translate(vi.getTarget(), info));
			sb.append(") {\n");
			sb.append( translateStatements(k, info) );
			sb.append("});\n");

			return sb.toString();
		}

		private String translatePropertySet(AstNode node, List<AstNode> k, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			ExpressionStatement es = (ExpressionStatement) node;
			Assignment ass = (Assignment) es.getExpression();
			ElementGet eg = (ElementGet) ass.getLeft();

			sb.append( translate(eg.getTarget(), info) );
			sb.append( ".set_cps(" );
			sb.append( translate(eg.getElement(), info));
			sb.append(", ");
			sb.append( translate(ass.getRight(), info) );
			sb.append(", function(");
			sb.append(") {\n");
			sb.append( translateStatements(k, info) );
			sb.append("});\n");

			return sb.toString();
		}

		private static final String K_PREFIX = "k", BREAK_PREFIX = "break_", CONTINUE_PREFIX = "continue_", RETRUN_PREFIX = "return_";

		private String translateIfStatement(IfStatement node, List<AstNode> k, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder(32);
			String k_n = createFreeName(K_PREFIX);
			NDJS2CPSJSInfo info_ = new NDJS2CPSJSInfo((NDJS2CPSJSInfo)info);
			info_.setkPoint(k_n);
			sb.append("Octopus.__inner__.if_cps( function() { return ");
			sb.append( translate(node.getCondition(), info));
			sb.append("; }, function(" + k_n + ") {");
			sb.append( translate(node.getThenPart(), info_) );
			sb.append("}, function(" + k_n + ") {");
			sb.append( translate(node.getElsePart(), info_) );
			sb.append("}, function() {");
			sb.append( translateStatements(k, info) );
			sb.append("});");
			return sb.toString();
		}

		//TODO
		private String translateWhileLoop(WhileLoop node, List<AstNode> k, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			String break_k_n = createFreeName(BREAK_PREFIX);
			String continue_k_n = createFreeName(CONTINUE_PREFIX);
			NDJS2CPSJSInfo info_ = new NDJS2CPSJSInfo((NDJS2CPSJSInfo)info);
			info_.setBreakPoint(break_k_n);
			info_.setContinuePoint(continue_k_n);

			sb.append("Octopus.__inner__.loop_cps(function(");
			sb.append(break_k_n + ", " + continue_k_n);
			sb.append(") {");
			sb.append( translate(node.getBody(), info_) );
			sb.append("} , function() {");
			sb.append( translateStatements(k, info) );
			sb.append("});");
			return sb.toString();
		}

		//TODO
		private String translateForInLoop(ForInLoop node, List<AstNode> k, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			String break_k_n = createFreeName(BREAK_PREFIX);
			String continue_k_n = createFreeName(CONTINUE_PREFIX);
			NDJS2CPSJSInfo info_ = new NDJS2CPSJSInfo((NDJS2CPSJSInfo)info);
			info_.setBreakPoint(break_k_n);
			info_.setContinuePoint(continue_k_n);

			sb.append( translate(node.getIteratedObject(), info) );
			sb.append(".each_cps");
			sb.append("( function(");
			if (node.getIterator() instanceof VariableDeclaration)
			{
				VariableDeclaration vd = (VariableDeclaration) node.getIterator();
				VariableInitializer vi = vd.getVariables().get(0);
				sb.append( translate(vi.getTarget(), info)  + ", ");
			}
			else
				sb.append( translate(node.getIterator(), info)  + ", ");
			sb.append(break_k_n + ", " + continue_k_n);
			sb.append(") {");
			sb.append( translate(node.getBody(), info_) );
			sb.append("}, function() {");
			sb.append( translateStatements(k, info) );
			sb.append("});");
			return sb.toString();
		}

		protected String translateStatements(List<AstNode> statements, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			LinkedList<AstNode> _statements = new LinkedList<AstNode>(statements);
			while (!_statements.isEmpty())
			{
				AstNode statement = _statements.poll();
				if (isFunctionDecl(statement))
				{
					sb.append(translateFunctionDecl(statement, _statements, info));
				}

				else if (isFunctionCall(statement))
				{
					sb.append(translateFunctionCall(statement, _statements, info));
					return sb.toString();
				}

				else if (isFunctionCallGet(statement))
				{
					sb.append(translateFunctionCallGet(statement, _statements, info));
					return sb.toString();
				}

				else if (isPropertyGet(statement))
				{
					sb.append(translatePropertyGet(statement, _statements, info));
					return sb.toString();
				}

				else if (isPropertySet(statement))
				{
					sb.append(translatePropertySet(statement, _statements, info));
					return sb.toString();
				}

				else if (statement instanceof WhileLoop)
				{
					sb.append(translateWhileLoop((WhileLoop) statement, _statements, info));
					return sb.toString();
				}

				else if (statement instanceof IfStatement)
				{
					sb.append(translateIfStatement((IfStatement) statement, _statements, info));
					return sb.toString();
				}

				else if (statement instanceof ForInLoop)
				{
					sb.append(translateForInLoop((ForInLoop) statement, _statements, info));
					return sb.toString();
				}

				else if (statement instanceof BreakStatement)
				{
					if (((NDJS2CPSJSInfo)info).getBreakPoint() == null)
						throw new TranslationException("Name not defined", statement);
					sb.append(((NDJS2CPSJSInfo)info).getBreakPoint() + "();");
					return sb.toString();
				}

				else if (statement instanceof ContinueStatement)
				{
					if (((NDJS2CPSJSInfo)info).getContinuePoint() == null)
						throw new TranslationException("Name not defined", statement);
					sb.append(((NDJS2CPSJSInfo)info).getContinuePoint() + "();");
					return sb.toString();
				}

				else if (statement instanceof ReturnStatement)
				{
					if (((NDJS2CPSJSInfo)info).getReturnPoint() == null)
						throw new TranslationException("Name not defined", statement);
					ReturnStatement rs = (ReturnStatement) statement;
					sb.append(((NDJS2CPSJSInfo)info).getReturnPoint() + "(");
					if (rs.getReturnValue() != null)
						sb.append(translate(rs.getReturnValue(), info));
					sb.append(");");
					return sb.toString();
				}
				else
					sb.append( translateStatement(statement, info) );
			}
			if (((NDJS2CPSJSInfo)info).getkPoint() != null)
				sb.append(((NDJS2CPSJSInfo)info).getkPoint() + "();");
			return sb.toString();
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
		protected String translate(Block node, TranslationInfomation info)
		{
			StringBuilder sb = new StringBuilder();
			List<AstNode> kids = new LinkedList<AstNode>();
			for (Node kid : node)
				kids.add( (AstNode) kid );
			sb.append( translateStatements(kids, info));
			return sb.toString();
		}

	}
}
