package net.manaten.octopus;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.*;

/**
 * 方針変更し、AstNode -> String(Translated source)な変換関数を作ることにする
 * @author mana
 *
 */
public class DefaultTranslation
{
	protected final AstRoot root;

	public static String withLn(String str)
	{
		int width = (str.length()+"").length();
		StringBuilder sb = new StringBuilder(str.length());
		int i = 0;
		for (String line : str.split("\n"))
		{
			i++;
			for (int j = 0, time = width - (i+"").length(); j < time; j++)
				sb.append(" ");
			sb.append(i + " : " + line + "\n");
		}
		return sb.toString();
	}

	public DefaultTranslation(AstRoot root)
	{
		this.root = root;
		usedVariableName = getNameSet(this.root);
	}

	/**
	 * Create free variable name still used in.
	 * @return
	 */
	private static final String FREE_VAL_PREFIX = "o_t";
	private final Set<String> usedVariableName;
	private int freeValNum = 0;
	protected final String createFreeName()
	{
		while(true)
		{
			String result = FREE_VAL_PREFIX + freeValNum++;
			if (!usedVariableName.contains(result))
			{
				usedVariableName.add(result);
				return result;
			}
		}
	}

	/**
	 * node以下で使用されてる変数名のセットを取得
	 */
	public final static Set<String> getNameSet(AstNode node)
	{
		final Set<String> result = new HashSet<String>();
		node.visit(new NodeVisitor() {
			@Override
			public boolean visit(AstNode n) {
				if (n instanceof Name)
					result.add(((Name) n).getIdentifier());
				return true;
			}
		});
		return result;
	}


	protected String translateList(List<? extends AstNode> nodes, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		if (nodes == null)
			return "";

		int max = nodes.size();
		int count = 0;
		for (AstNode node : nodes)
		{
			sb.append( translate(node, info) );
			if(count++ < max - 1)
				sb.append(", ");
		}
		return sb.toString();
	}

	public AstRoot translateToNode()
	{
		CompilerEnvirons compilerEnv = new CompilerEnvirons();
		compilerEnv.setRecordingComments(true);
		compilerEnv.setOptimizationLevel(-1);
		compilerEnv.setGeneratingSource(true);
		Parser parser = new Parser(compilerEnv, compilerEnv.getErrorReporter());
		String translated = translate();
		try
		{
			return parser.parse(translated, "dummy.js", 1);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e.getMessage() + "\n" + withLn(translated));
		}
	}

	//*
	public String translate()
	{
		return translate(root, new TranslationInfomation());
	}
	//*/

	protected String translate(AstNode node, TranslationInfomation info)
	{
		//System.out.println(node.getClass().toString());
		if (node instanceof ArrayLiteral) return translate((ArrayLiteral) node, info);
		if (node instanceof Block) return translate((Block) node, info);
		if (node instanceof CatchClause) return translate((CatchClause) node, info);
		if (node instanceof Comment) return translate((Comment) node, info);
		if (node instanceof ConditionalExpression) return translate((ConditionalExpression) node, info);
		if (node instanceof ElementGet) return translate((ElementGet) node, info);
		if (node instanceof EmptyExpression) return translate((EmptyExpression) node, info);
		if (node instanceof ErrorNode) return translate((ErrorNode) node, info);
		if (node instanceof ExpressionStatement) return translate((ExpressionStatement) node, info);

		if (node instanceof NewExpression) return translate((NewExpression) node, info);
		if (node instanceof FunctionCall) return translate((FunctionCall) node, info);

		if (node instanceof IfStatement) return translate((IfStatement) node, info);


		if (node instanceof Assignment) return translate((Assignment) node, info);
		if (node instanceof ObjectProperty) return translate((ObjectProperty) node, info);
		if (node instanceof PropertyGet) return translate((PropertyGet) node, info);
		if (node instanceof XmlDotQuery) return translate((XmlDotQuery) node, info);
		if (node instanceof XmlMemberGet) return translate((XmlMemberGet) node, info);
		if (node instanceof InfixExpression) return translate((InfixExpression) node, info);


		if (node instanceof BreakStatement) return translate((BreakStatement) node, info);
		if (node instanceof ContinueStatement) return translate((ContinueStatement) node, info);
		if (node instanceof Label) return translate((Label) node, info);

		if (node instanceof ArrayComprehension) return translate((ArrayComprehension) node, info);
		if (node instanceof LetNode) return translate((LetNode) node, info);

		if (node instanceof DoLoop) return translate((DoLoop) node, info);
		if (node instanceof ForInLoop) return translate((ForInLoop) node, info);
		if (node instanceof ForLoop) return translate((ForLoop) node, info);
		if (node instanceof WhileLoop) return translate((WhileLoop) node, info);
		if (node instanceof Loop) return translate((Loop) node, info);

		if (node instanceof AstRoot) return translate((AstRoot) node, info);
		if (node instanceof FunctionNode) return translate((FunctionNode) node, info);
		if (node instanceof ScriptNode) return translate((ScriptNode) node, info);

		if (node instanceof Scope) return translate((Scope) node, info);

		if (node instanceof SwitchStatement) return translate((SwitchStatement) node, info);
		if (node instanceof Jump) return translate((Jump) node, info);

		if (node instanceof KeywordLiteral) return translate((KeywordLiteral) node, info);
		if (node instanceof LabeledStatement) return translate((LabeledStatement) node, info);
		if (node instanceof Name) return translate((Name) node, info);
		if (node instanceof NumberLiteral) return translate((NumberLiteral) node, info);
		if (node instanceof ObjectLiteral) return translate((ObjectLiteral) node, info);
		if (node instanceof ParenthesizedExpression) return translate((ParenthesizedExpression) node, info);
		if (node instanceof RegExpLiteral) return translate((RegExpLiteral) node, info);
		if (node instanceof ReturnStatement) return translate((ReturnStatement) node, info);
		if (node instanceof StringLiteral) return translate((StringLiteral) node, info);
		if (node instanceof SwitchCase) return translate((SwitchCase) node, info);
		if (node instanceof ThrowStatement) return translate((ThrowStatement) node, info);
		if (node instanceof TryStatement) return translate((TryStatement) node, info);
		if (node instanceof UnaryExpression) return translate((UnaryExpression) node, info);
		if (node instanceof VariableDeclaration) return translate((VariableDeclaration) node, info);
		if (node instanceof VariableInitializer) return translate((VariableInitializer) node, info);
		if (node instanceof WithStatement) return translate((WithStatement) node, info);

		if (node instanceof XmlExpression) return translate((XmlExpression) node, info);
		if (node instanceof XmlString) return translate((XmlString) node, info);
		if (node instanceof XmlFragment) return translate((XmlFragment) node, info);

		if (node instanceof XmlLiteral) return translate((XmlLiteral) node, info);

		if (node instanceof XmlElemRef) return translate((XmlElemRef) node, info);
		if (node instanceof XmlPropRef) return translate((XmlPropRef) node, info);
		if (node instanceof XmlRef) return translate((XmlRef) node, info);

		if (node instanceof Yield) return translate((Yield) node, info);

		throw new TranslationException(node);
	}

	/**
	 * Translate statement.(for override)
	 * @param statement
	 * @param info
	 * @return
	 */
	protected String translateStatement(AstNode statement, TranslationInfomation info)
	{
		return translate(statement, info);
	}

	/**
	 * Translate statements included Block, AstRoot, Scope (for override).
	 * @param statements
	 * @param info
	 * @return
	 */
	protected String translateStatements(List<AstNode> statements, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		for (AstNode statement : statements)
			sb.append( translateStatement(statement, info) );
		return sb.toString();
	}

	protected String translate(ArrayLiteral node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append( translateList(node.getElements(), info) );
		sb.append("]");
		return sb.toString();
	}

	protected String translate(Block node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");

		List<AstNode> kids = new LinkedList<AstNode>();
		for (Node kid : node)
			kids.add( (AstNode) kid );
		sb.append( translateStatements(kids, info));

		sb.append("}\n");
		return sb.toString();
	}

	protected String translate(CatchClause node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("catch (");
		sb.append( translate(node.getVarName(), info) );
		if(node.getCatchCondition() != null)
		{
			sb.append(" if ");
			sb.append( translate(node.getCatchCondition(), info) );
		}
		sb.append(") ");
		sb.append( translate(node.getBody(), info) );
		return sb.toString();
	}


	protected String translate(Comment node, TranslationInfomation info)
	{
		System.out.println("coment");
		StringBuilder sb = new StringBuilder(node.getLength() + 10);
		sb.append(node.getValue());
		return sb.toString();
	}

	protected String translate(ConditionalExpression node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append( translate(node.getTestExpression(), info) );
		sb.append(" ? ");
		sb.append( translate(node.getTrueExpression(), info) );
		sb.append(" : ");
		sb.append( translate(node.getFalseExpression(), info) );
		return sb.toString();
	}

	protected String translate(ElementGet node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append( translate(node.getTarget(), info) );
		sb.append("[");
		sb.append( translate(node.getElement(), info) );
		sb.append("]");
		return sb.toString();
	}

	protected String translate(EmptyExpression node, TranslationInfomation info)
	{
		return "";
	}

	protected String translate(ErrorNode node, TranslationInfomation info)
	{
		return "";
	}

	protected String translate(ExpressionStatement node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append( translate(node.getExpression(), info) );
		sb.append(";\n");
		return sb.toString();
	}

	protected String translate(FunctionCall node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append( translate(node.getTarget(), info) );
		sb.append("(");
		sb.append( translateList(node.getArguments(), info) );
		sb.append(")");
		return sb.toString();
	}

	protected String translate(NewExpression node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("new ");
		sb.append( translate(node.getTarget(), info) );
		sb.append("(");
		sb.append( translateList(node.getArguments(), info) );
		sb.append(")");
		if(node.getInitializer() != null)
		{
			sb.append(" ");
			sb.append( translate(node.getInitializer(), info) );
		}
		return sb.toString();
	}

	protected String translate(IfStatement node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder(32);
		sb.append("if (");
		sb.append( translate(node.getCondition(), info));
		sb.append(") ");
		sb.append( translate(node.getThenPart(), info));
		if(node.getElsePart() != null)
		{
			sb.append(" else ");
			sb.append( translate(node.getElsePart(), info) );
		}
		sb.append("\n");
		return sb.toString();
	}

	protected String translate(InfixExpression node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append( translate(node.getLeft(), info) );
		sb.append(" ");
		sb.append(AstNode.operatorToString(node.getType()));
		sb.append(" ");
		sb.append( translate(node.getRight(), info) );
		return sb.toString();
	}

	protected String translate(Assignment node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append( translate(node.getLeft(), info) );
		sb.append(" ");
		sb.append(AstNode.operatorToString(node.getType()));
		sb.append(" ");
		sb.append( translate(node.getRight(), info) );
		return sb.toString();
	}

	protected String translate(ObjectProperty node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		if(node.isGetter())
			sb.append("get ");
		else if(node.isSetter())
			sb.append("set ");
		sb.append( translate(node.getLeft(), info) );
		if (node.getType() == 103)
			sb.append(": ");
		sb.append( translate(node.getRight(), info) );
		return sb.toString();
	}

	protected String translate(PropertyGet node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append( translate(node.getLeft(), info) );
		sb.append(".");
		sb.append( translate(node.getRight(), info) );
		return sb.toString();
	}

	protected String translate(XmlDotQuery node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(XmlMemberGet node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(Jump node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(BreakStatement node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("break");
		if(node.getBreakLabel() != null)
		{
			sb.append(" ");
			sb.append( translate(node.getBreakLabel(), info) );
		}
		sb.append(";\n");
		return sb.toString();
	}

	protected String translate(ContinueStatement node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(Label node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(Scope node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");

		List<AstNode> kids = new LinkedList<AstNode>();
		for (Node kid : node)
			kids.add( (AstNode) kid );
		sb.append( translateStatements(kids, info));

		sb.append("}\n");
		return sb.toString();
	}

	protected String translate(ArrayComprehension node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(LetNode node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(Loop node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(DoLoop node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("do ");
		sb.append( translate(node.getBody(), info) );
		sb.append(" while (");
		sb.append( translate(node.getCondition(), info) );
		sb.append(");\n");
		return sb.toString();
	}

	protected String translate(ForInLoop node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("for ");
		if(node.isForEach())
			sb.append("each ");
		sb.append("(");
		sb.append( translate(node.getIterator(), info) );
		sb.append(" in ");
		sb.append( translate(node.getIteratedObject(), info) );
		sb.append(") ");
		if(node.getBody() instanceof Block)
			sb.append( translate(node.getBody(), info) ).append("\n");
		else
			sb.append("\n").append( translate(node.getBody(), info) );
		return sb.toString();
	}

	protected String translate(ArrayComprehensionLoop node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(ForLoop node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("for (");
		sb.append( translate(node.getInitializer(), info) );
		sb.append("; ");
		sb.append( translate(node.getCondition(), info) );
		sb.append("; ");
		sb.append( translate(node.getIncrement(), info) );
		sb.append(") ");
		if(node.getBody() instanceof Block)
			sb.append( translate(node.getBody(), info) ).append("\n");
		else
			sb.append("\n").append( translate(node.getBody(), info) );
		return sb.toString();
	}

	protected String translate(WhileLoop node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("while (");
		sb.append( translate(node.getCondition(), info) );
		sb.append(") ");
		if(node.getBody() instanceof Block)
			sb.append( translate(node.getBody(), info) ).append("\n");
		else
			sb.append("\n").append( translate(node.getBody(), info) );
		return sb.toString();
	}

	protected String translate(ScriptNode node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(AstRoot node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();

		List<AstNode> kids = new LinkedList<AstNode>();
		for (Node kid : node)
			kids.add( (AstNode) kid );
		sb.append( translateStatements(kids, info));

		return sb.toString();
	}

	protected String translate(FunctionNode node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("function");
		if(node.getFunctionName() != null)
		{
			sb.append(" ");
			sb.append( translate(node.getFunctionName(), info) );
		}
		sb.append("(");
		sb.append( translateList(node.getParams(), info) );
		sb.append(") ");

		if(node.isExpressionClosure())
		{
			sb.append(" ");
			sb.append( translate(node.getBody(), info) );
		}
		else
		{
			sb.append( translate(node.getBody(), info) );
		}
		if( node.getFunctionType() == 1)
			sb.append("\n");
		return sb.toString();
	}

	protected String translate(SwitchStatement node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("switch (");
		sb.append( translate(node.getExpression(), info) );
		sb.append(") {\n");
		for (SwitchCase sc : node.getCases() )
			sb.append( translate(sc, info) );
		sb.append("}\n");
		return sb.toString();
	}

	protected String translate(KeywordLiteral node, TranslationInfomation info)
	{
		return node.toSource();
	}

	protected String translate(LabeledStatement node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(Name node, TranslationInfomation info)
	{
		return node.toSource();
	}

	protected String translate(NumberLiteral node, TranslationInfomation info)
	{
		return node.toSource();
	}

	protected String translate(ObjectLiteral node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append( translateList(node.getElements(), info) );
		sb.append("}");
		return sb.toString();
	}

	protected String translate(ParenthesizedExpression node, TranslationInfomation info)
	{
		return (new StringBuilder()).append("(").append( translate(node.getExpression(), info) ).append(")").toString();
	}

	protected String translate(RegExpLiteral node, TranslationInfomation info)
	{
		return node.toSource();
	}

	protected String translate(ReturnStatement node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("return");
		if(node.getReturnValue() != null)
		{
			sb.append(" ");
			sb.append( translate( node.getReturnValue(), info) );
		}
		sb.append(";\n");
		return sb.toString();
	}

	protected String translate(StringLiteral node, TranslationInfomation info)
	{
		return node.toSource();
	}

	protected String translate(SwitchCase node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		if(node.getExpression() == null)
		{
			sb.append("default:\n");
		} else
		{
			sb.append("case ");
			sb.append(node.getExpression().toSource(0));
			sb.append(":\n");
		}
		if(node.getStatements() != null)
		{
			for (AstNode s : node.getStatements())
				sb.append( translate(s, info) );
		}
		return sb.toString();
	}

	protected String translate(ThrowStatement node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("throw");
		sb.append(" ");
		sb.append(translate(node.getExpression(), info) );
		sb.append(";\n");
		return sb.toString();
	}

	protected String translate(TryStatement node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder(250);
		sb.append("try ");
		sb.append( translate(node.getTryBlock(), info));
		for(CatchClause cc : node.getCatchClauses())
			sb.append( translate(cc, info) );
		if(node.getFinallyBlock() != null)
		{
			sb.append(" finally ");
			sb.append( translate(node.getFinallyBlock(), info) );
		}
		return sb.toString();
	}

	protected String translate(UnaryExpression node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		if(!node.isPostfix())
		{
			sb.append(AstNode.operatorToString(node.getType()));
			if(node.getType() == 32 || node.getType() == 31)
				sb.append(" ");
		}
		sb.append( translate(node.getOperand(), info) );
		if(node.isPostfix())
			sb.append(AstNode.operatorToString(node.getType()));
		return sb.toString();
	}

	protected String translate(VariableDeclaration node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Token.typeToName(node.getType()).toLowerCase());
		sb.append(" ");
		sb.append( translateList(node.getVariables() ,info) );
		if(!(node.getParent() instanceof Loop))
			sb.append(";\n");
		return sb.toString();
	}

	protected String translate(VariableInitializer node, TranslationInfomation info)
	{
		StringBuilder sb = new StringBuilder();
		sb.append( translate(node.getTarget(), info));
		if(node.getInitializer() != null)
		{
			sb.append(" = ");
			sb.append( translate(node.getInitializer(), info));
		}
		return sb.toString();
	}

	protected String translate(WithStatement node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(XmlFragment node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(XmlExpression node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(XmlString node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(XmlLiteral node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(XmlRef node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(XmlElemRef node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(XmlPropRef node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

	protected String translate(Yield node, TranslationInfomation info)
	{
		throw new TranslationException("Unsupported node : ", node);
	}

}