package net.manaten.octopus.old;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


import org.mozilla.javascript.Node;
import org.mozilla.javascript.ast.*;


public class CPSTranslation extends DefaultTranslation {
	//public AstNode translate(){}

	private LinkedList<AstNode> reverse(Iterable<? extends Node> node)
	{
		LinkedList<AstNode> kids = new LinkedList<AstNode>();
		for (Node kid : node)
			kids.addFirst((AstNode)kid);
		return kids;
	}

	private int freeNameId = 0;
	/**
	 * create free Name by info.
	 * @return
	 */
	private Name createFreeName(TranslationInfomation info)
	{
		Name n = new Name();
		n.setIdentifier("octpus_t" + freeNameId++);
		return n;
	}

	/**
	 * node to expressionStatement.
	 * @param node
	 * @return
	 */
	private AstNode expressionStatement(AstNode node)
	{
		ExpressionStatement es = new ExpressionStatement();
		es.setExpression(node);
		return es;
	}

	/**
	 * create call statements of fun with arg
	 * @param fun
	 * @param arg
	 * @return
	 */
	private AstNode call(AstNode fun, AstNode... arg)
	{
		ExpressionStatement es = new ExpressionStatement();
		ParenthesizedExpression pe = new ParenthesizedExpression();
		FunctionCall call = new FunctionCall();
		pe.setExpression(fun);
		call.setTarget(pe);
		if (arg != null)
			for (AstNode a : arg)
				call.addArgument(a);
		es.setExpression(call);
		return es;
	}

	/**
	 * Create FunctionNode of continuation with paramater name;
	 * @param name
	 * @return
	 */
	private FunctionNode toContinuation(AstNode node, Name name)
	{
		FunctionNode k = new ContinuationNode();
		k.setIsExpressionClosure(false);
		Block kBody = new Block();
		k.setBody(kBody);
		k.addParam(name);
		if (node != null)
			kBody.addChild(node);
		return k;
	}
	/**
	 * Create FunctionNode of continuation with paramater name by TransformationInfomation;
	 * @param name
	 * @return
	 */
	private FunctionNode toContinuation(AstNode node, TranslationInfomation info)
	{
		//TODO use to TranslationInfomation.
		return toContinuation(node, createFreeName(info));
	}


	protected AstNode translate(ArrayLiteral node, TranslationInfomation info)
	{
		if (node == null) return null;

		FunctionNode k = info.getContinuation();
		ArrayLiteral arrayLit = new ArrayLiteral();
		AstNode call = call(k, arrayLit);

		AstNode kid_ = null;
		Name n = createFreeName(info);
		k = toContinuation(call, n);
		List<AstNode> elements = new ArrayList<AstNode>(node.getElements().size());
		for (AstNode kid : reverse(node.getElements()))
		{
			elements.add(n);
			info.setContinuation(k);
			kid_ = translate(kid, info);

			n = createFreeName(info);
			k = toContinuation(kid_, n);
		}
		Collections.reverse(elements);
		arrayLit.setElements(elements);
		return kid_;
	}

	protected AstNode translate(Block node, TranslationInfomation info)
	{
		if (node == null) return null;

		System.out.println("Hoggeeeee");
		FunctionNode k = info.getContinuation();
		AstNode kid_ = call(k);
		for (AstNode kid : reverse(node))
		{
			k = toContinuation(kid_, info);
			info.setContinuation(k);
			kid_ = translate(kid, info);
		}
		return kid_;
	}

	protected CatchClause translate(CatchClause node, TranslationInfomation info)
	{
		if (node == null) return null;
		CatchClause result = new CatchClause();
		result.setVarName(name(node.getVarName()));
		result.setCatchCondition(translate(node.getCatchCondition(), info));
		result.setBody(block(node.getBody(), info));
		return result;
	}

	/*
	protected AstNode translate(Comment node, TranslationInfomation info)
	{
		Comment result = new Comment(node.getPosition(), node.getLength(), node.getCommentType(), node.getValue());
		return result;
	}
	//*/

	protected AstNode translate(ConditionalExpression node, TranslationInfomation info)
	{
		if (node == null) return null;
		ConditionalExpression result = new ConditionalExpression();
		result.setTestExpression(translate(node.getTestExpression(), info));
		result.setTrueExpression(translate(node.getTrueExpression(), info));
		result.setFalseExpression(translate(node.getFalseExpression(), info));
		return result;
	}

	protected AstNode translate(ElementGet node, TranslationInfomation info)
	{
		if (node == null) return null;
		ElementGet result = new ElementGet();
		result.setElement(translate(node.getElement(), info));
		result.setTarget(translate(node.getTarget(), info));
		return result;
	}

	protected AstNode translate(EmptyExpression node, TranslationInfomation info)
	{
		if (node == null) return null;
		EmptyExpression result = new EmptyExpression();
		return result;
	}

	protected AstNode translate(ErrorNode node, TranslationInfomation info)
	{
		if (node == null) return null;
		ErrorNode result = new ErrorNode();
		result.setMessage(node.getMessage());
		return result;
	}

	protected AstNode translate(ExpressionStatement node, TranslationInfomation info)
	{
		if (node == null) return null;
		return translate(node.getExpression(), info);
	}

	protected AstNode translate(FunctionCall node, TranslationInfomation info)
	{
		if (node == null) return null;
		if (node instanceof NewExpression) return translate((NewExpression) node, info);

		FunctionCall result = new FunctionCall();
		result.setTarget(translate(node.getTarget(), info));
		for (AstNode arg : node.getArguments())
			result.addArgument(translate(arg, info));
		return result;
	}

	protected AstNode translate(NewExpression node, TranslationInfomation info)
	{
		if (node == null) return null;
		NewExpression result = new NewExpression();
		result.setTarget(translate(node.getTarget(), info));
		for (AstNode arg : node.getArguments())
			result.addArgument(translate(arg, info));
		result.setInitializer(translate(node.getInitializer(), info));
		return result;
	}

	protected AstNode translate(IfStatement node, TranslationInfomation info)
	{
		if (node == null) return null;
		IfStatement result = new IfStatement();
		result.setCondition(translate(node.getCondition(), info));
		result.setThenPart(translate(node.getThenPart(), info));
		result.setElsePart(translate(node.getElsePart(), info));
		return result;
	}

	protected AstNode translate(InfixExpression node, TranslationInfomation info)
	{
		if (node == null) return null;

		if (node instanceof Assignment) return translate((Assignment) node, info);
/*
		if (node instanceof ObjectProperty) return translate((ObjectProperty) node, info);
		if (node instanceof PropertyGet) return translate((PropertyGet) node, info);
		if (node instanceof XmlDotQuery) return translate((XmlDotQuery) node, info);
		if (node instanceof XmlMemberGet) return translate((XmlMemberGet) node, info);
*/

		Name r1 = createFreeName(info);
		Name r2 = createFreeName(info);
		FunctionNode k  = info.getContinuation();
		InfixExpression exp = new InfixExpression();
		exp.setType(node.getType());
		exp.setLeft(r1);
		exp.setRight(r2);

		AstNode call = call(k, exp);
		k = toContinuation(call, r2);
		info.setContinuation(k);
		AstNode n = translate(node.getRight(), info);

		k = toContinuation(n, r1);
		info.setContinuation(k);
		return translate(node.getLeft(), info);
	}

	protected AstNode translate(Assignment node, TranslationInfomation info)
	{
		if (node == null) return null;

		if (node.getLeft() instanceof Name)
		{
			Name r = createFreeName(info);
			Assignment ass = new Assignment();
			ass.setLeft(name((Name)node.getLeft()));
			ass.setRight(r);
			ass.setType(node.getType());
			FunctionNode k = toContinuation(call(info.getContinuation(), ass), r);
			info.setContinuation(k);
			return translate(node.getRight(), info);
		}
		else if (node.getLeft() instanceof PropertyGet)
		{
			PropertyGet origin = (PropertyGet) node.getLeft();
			Name r1 = createFreeName(info), r2 = createFreeName(info);

			PropertyGet pg = new PropertyGet();
			pg.setTarget(r1);
			pg.setProperty(name("set"));
			AstNode setCall = call(pg, origin.getProperty(), r2, info.getContinuation());

			info.setContinuation(toContinuation(setCall, r2));

			info.setContinuation(toContinuation(translate(node.getRight(), info), r1));
			return translate(origin.getTarget(), info);
		}
		else if (node.getLeft() instanceof ElementGet)
		{

		}
		else
			throw new RuntimeException("Unnkown Assignment trandformation in " + node.toSource());

		Assignment result = new Assignment();
		result.setLeft(translate(node.getLeft(), info));
		result.setRight(translate(node.getRight(), info));
		result.setType(node.getType());
		return result;
	}

	protected ObjectProperty translate(ObjectProperty node, TranslationInfomation info)
	{
		if (node == null) return null;
		ObjectProperty result = new ObjectProperty();
		result.setLeft(translate(node.getLeft(), info));
		result.setRight(translate(node.getRight(), info));
		result.setType(node.getType());
		return result;
	}

	protected AstNode translate(PropertyGet node, TranslationInfomation info)
	{
		if (node == null) return null;
		PropertyGet result = new PropertyGet();
		result.setTarget(translate(node.getTarget(), info));
		result.setProperty(name(node.getProperty()));
		result.setType(node.getType());
		return result;
	}

	protected AstNode translate(XmlDotQuery node, TranslationInfomation info)
	{
		if (node == null) return null;
		XmlDotQuery result = new XmlDotQuery();
		result.setLeft(translate(node.getLeft(), info));
		result.setRight(translate(node.getRight(), info));
		result.setType(node.getType());
		return result;
	}

	protected AstNode translate(XmlMemberGet node, TranslationInfomation info)
	{
		if (node == null) return null;
		XmlMemberGet result = new XmlMemberGet();
		result.setLeft(translate(node.getLeft(), info));
		result.setRight(translate(node.getRight(), info));
		result.setType(node.getType());
		return result;
	}

	protected AstNode translate(Jump node, TranslationInfomation info)
	{
		if (node == null) return null;
		if (node instanceof BreakStatement) return translate((BreakStatement) node, info);
		if (node instanceof ContinueStatement) return translate((ContinueStatement) node, info);
		if (node instanceof Label) return translate((Label) node, info);
		if (node instanceof Scope) return translate((Scope) node, info);
		if (node instanceof SwitchStatement) return translate((SwitchStatement) node, info);

		throw new RuntimeException("Unknown node : " + node.getClass());
	}

	protected AstNode translate(BreakStatement node, TranslationInfomation info)
	{
		if (node == null) return null;
		BreakStatement result = new BreakStatement();
		result.setBreakLabel(name(node.getBreakLabel()));
		return result;
	}

	protected AstNode translate(ContinueStatement node, TranslationInfomation info)
	{
		if (node == null) return null;
		ContinueStatement result = new ContinueStatement();
		result.setLabel(name(node.getLabel()));
		return result;
	}

	protected Label translate(Label node, TranslationInfomation info)
	{
		if (node == null) return null;
		Label result = new Label();
		result.setName(node.getName());
		return result;
	}

	protected AstNode translate(Scope node, TranslationInfomation info)
	{
		if (node == null) return null;
		if (node instanceof ArrayComprehension) return translate((ArrayComprehension) node, info);
		if (node instanceof LetNode) return translate((LetNode) node, info);
		if (node instanceof Loop) return translate((Loop) node, info);
		if (node instanceof ScriptNode) return translate((ScriptNode) node, info);

		Name r = createFreeName(info);
		FunctionNode k = info.getContinuation();
		AstNode kid_ = call(k, r);
		for (AstNode kid : reverse(node))
		{
			//結局副作用のある文が残ればいいので、最終的にこのへんのコードはシンプルにしていいはず
//			k = toContinuation(expressionStatement(r), r);
//			k.getBody().addChild(kid_);
//			info.setContinuation(k);
//			kid_ = translate(kid, info);
//			r = createFreeName(info);

			k = toContinuation(kid_, r);
			info.setContinuation(k);
			kid_ = translate(kid, info);
			r = createFreeName(info);
		}
		return kid_;
	}

	protected AstNode translate(ArrayComprehension node, TranslationInfomation info)
	{
		if (node == null) return null;
		ArrayComprehension result = new ArrayComprehension();
		result.setResult(translate(node.getResult(), info));
		for (ArrayComprehensionLoop loop : node.getLoops())
			result.addLoop(translate(loop, info));
		result.setFilter(translate(node.getFilter(), info));
		return result;
	}

	protected AstNode translate(LetNode node, TranslationInfomation info)
	{
		if (node == null) return null;
		LetNode result = new LetNode();
		result.setBody(translate(node.getBody(), info));
		result.setVariables(translate(node.getVariables(), info));

		return result;
	}

	protected AstNode translate(Loop node, TranslationInfomation info)
	{
		if (node == null) return null;
		if (node instanceof DoLoop) return translate((DoLoop) node, info);
		if (node instanceof ForInLoop) return translate((ForInLoop) node, info);
		if (node instanceof ForLoop) return translate((ForLoop) node, info);
		if (node instanceof WhileLoop) return translate((WhileLoop) node, info);
		throw new RuntimeException("Unknown node : " + node.getClass());
	}

	protected AstNode translate(DoLoop node, TranslationInfomation info)
	{
		if (node == null) return null;
		DoLoop result = new DoLoop();
		result.setBody(translate(node.getBody(), info));
		result.setCondition(translate(node.getCondition(), info));
		return result;
	}

	protected AstNode translate(ForInLoop node, TranslationInfomation info)
	{
		if (node == null) return null;
		if (node instanceof ArrayComprehensionLoop) return translate((ArrayComprehensionLoop) node, info);
		ForInLoop result = new ForInLoop();
		result.setBody(translate(node.getBody(), info));
		result.setIterator(translate(node.getIterator(), info));
		result.setIteratedObject(translate(node.getIteratedObject(), info));
		return result;
	}

	protected ArrayComprehensionLoop translate(ArrayComprehensionLoop node, TranslationInfomation info)
	{
		if (node == null) return null;
		ArrayComprehensionLoop result = new ArrayComprehensionLoop();
		result.setIterator(translate(node.getIterator(), info));
		result.setIteratedObject(translate(node.getIteratedObject(), info));
		return result;
	}

	protected AstNode translate(ForLoop node, TranslationInfomation info)
	{
		if (node == null) return null;
		ForLoop result = new ForLoop();
		result.setBody(translate(node.getBody(), info));
		result.setCondition(translate(node.getCondition(), info));
		result.setInitializer(translate(node.getInitializer(), info));
		result.setIncrement(translate(node.getIncrement(), info));
		return result;
	}

	protected AstNode translate(WhileLoop node, TranslationInfomation info)
	{
		if (node == null) return null;
		WhileLoop result = new WhileLoop();
		result.setBody(translate(node.getBody(), info));
		result.setCondition(translate(node.getCondition(), info));
		return result;
	}

	protected AstNode translate(ScriptNode node, TranslationInfomation info)
	{
		if (node == null) return null;
		if (node instanceof AstRoot) return translate((AstRoot) node, info);
		if (node instanceof FunctionNode) return translate((FunctionNode) node, info);
		/*
		ScriptNode result = new ScriptNode();
		return result;
		//*/
		throw new RuntimeException("Unknown node : " + node.getClass());
	}

	protected AstNode translate(AstRoot node, TranslationInfomation info)
	{
		if (node == null) return null;
		AstRoot result = new AstRoot();

		Name r = createFreeName(info);
		AstNode kid_ = expressionStatement(r);
		FunctionNode k;
		for (AstNode kid : reverse(node))
		{
//			k = toContinuation(expressionStatement(r), r);
//			if (kid_ != null)
//				k.getBody().addChild(kid_);
//			info.setContinuation(k);
//			kid_ = translate(kid, info);
//			r = createFreeName(info);
//
			k = toContinuation(kid_, r);
			info.setContinuation(k);
			kid_ = translate(kid, info);
			r = createFreeName(info);
		}
		result.addChild(kid_);
		return result;
	}

	protected AstNode translate(FunctionNode node, TranslationInfomation info)
	{
		if (node == null) return null;
		FunctionNode result = new FunctionNode();
		result.setFunctionName(name(node.getFunctionName()));
		for (AstNode param : node.getParams())
			result.addParam(translate(param, info));
		result.setBody(translate(node.getBody(), info));
		result.setIsExpressionClosure(node.isExpressionClosure());
		result.setFunctionType(node.getFunctionType());
		return result;
	}

	protected AstNode translate(SwitchStatement node, TranslationInfomation info)
	{
		if (node == null) return null;
		SwitchStatement result = new SwitchStatement();
		result.setExpression(translate(node.getExpression(), info));
		for (SwitchCase sc : node.getCases())
			result.addCase(translate(sc, info));
		return result;
	}

	protected AstNode translate(KeywordLiteral node, TranslationInfomation info)
	{
		if (node == null) return null;
		KeywordLiteral result = new KeywordLiteral();
		result.setType(node.getType());
		return call(info.getContinuation(), result);
	}

	protected AstNode translate(LabeledStatement node, TranslationInfomation info)
	{
		if (node == null) return null;
		LabeledStatement result = new LabeledStatement();
		for (Label label : node.getLabels())
			result.addLabel(translate(label, info));
		result.setStatement(translate(node.getStatement(), info));
		return result;
	}

	protected AstNode translate(Name node, TranslationInfomation info)
	{
		if (node == null) return null;
		Name result = new Name();
		result.setIdentifier(node.getIdentifier());
		AstNode call = call(info.getContinuation(), result);
		return call;
	}

	protected AstNode translate(NumberLiteral node, TranslationInfomation info)
	{
		if (node == null) return null;

		NumberLiteral result = new NumberLiteral();
		result.setValue(node.getValue());
		AstNode call = call(info.getContinuation(), result);
		return call;
	}

	protected ObjectLiteral translate(ObjectLiteral node, TranslationInfomation info)
	{
		if (node == null) return null;
		ObjectLiteral result = new ObjectLiteral();
		for (ObjectProperty element : node.getElements())
			result.addElement(translate(element, info));
		return result;
	}

	protected AstNode translate(ParenthesizedExpression node, TranslationInfomation info)
	{
		if (node == null) return null;
		ParenthesizedExpression result = new ParenthesizedExpression();
		result.setExpression(translate(node.getExpression(), info));
		return result;
	}

	protected AstNode translate(RegExpLiteral node, TranslationInfomation info)
	{
		if (node == null) return null;
		RegExpLiteral result = new RegExpLiteral();
		result.setValue(node.getValue());
		result.setFlags(node.getFlags());
		return result;
	}

	protected AstNode translate(ReturnStatement node, TranslationInfomation info)
	{
		if (node == null) return null;
		ReturnStatement result = new ReturnStatement();
		result.setReturnValue(translate(node.getReturnValue(), info));
		return result;
	}

	protected AstNode translate(StringLiteral node, TranslationInfomation info)
	{
		if (node == null) return null;
		StringLiteral result = new StringLiteral();
		result.setValue(node.getValue());
		result.setQuoteCharacter(node.getQuoteCharacter());
		AstNode call = call(info.getContinuation(), result);
		return call;
	}

	protected SwitchCase translate(SwitchCase node, TranslationInfomation info)
	{
		if (node == null) return null;
		SwitchCase result = new SwitchCase();
		result.setExpression(translate(node.getExpression(), info));
		for (AstNode statement : node.getStatements())
			result.addStatement(translate(statement, info));
		return result;
	}

	protected AstNode translate(ThrowStatement node, TranslationInfomation info)
	{
		if (node == null) return null;
		ThrowStatement result = new ThrowStatement();
		result.setExpression(translate(node.getExpression(), info));
		return result;
	}

	protected AstNode translate(TryStatement node, TranslationInfomation info)
	{
		if (node == null) return null;
		TryStatement result = new TryStatement();
		result.setTryBlock(translate(node.getTryBlock(), info));
		result.setFinallyBlock(translate(node.getFinallyBlock(), info));
		for (CatchClause cc : node.getCatchClauses())
			result.addCatchClause(translate(cc, info));
		return result;
	}

	protected AstNode translate(UnaryExpression node, TranslationInfomation info)
	{
		if (node == null) return null;

		Name r1 = createFreeName(info);
		FunctionNode k  = info.getContinuation();
		UnaryExpression exp = new UnaryExpression();
		exp.setIsPostfix(node.isPostfix());
		exp.setType(node.getType());
		exp.setOperand(r1);

		AstNode call = call(k, exp);
		k = toContinuation(call, r1);
		info.setContinuation(k);
		return translate(node.getOperand(), info);
	}

	protected VariableDeclaration translate(VariableDeclaration node, TranslationInfomation info)
	{
		if (node == null) return null;
		VariableDeclaration result = new VariableDeclaration();
		result.setType(node.getType());
		for (VariableInitializer v : node.getVariables())
			result.addVariable(translate(v, info));
		return result;
	}

	protected VariableInitializer translate(VariableInitializer node, TranslationInfomation info)
	{
		if (node == null) return null;
		VariableInitializer result = new VariableInitializer();
		result.setInitializer(translate(node.getInitializer(), info));
		result.setTarget(translate(node.getTarget(), info));
		return result;
	}

	protected AstNode translate(WithStatement node, TranslationInfomation info)
	{
		if (node == null) return null;
		WithStatement result = new WithStatement();
		result.setExpression(translate(node.getExpression(), info));
		result.setStatement(translate(node.getStatement(), info));
		return result;
	}

	protected XmlFragment translate(XmlFragment node, TranslationInfomation info)
	{
		if (node == null) return null;
		if (node instanceof XmlExpression) return translate((XmlExpression) node, info);
		if (node instanceof XmlString) return translate((XmlString) node, info);
		throw new RuntimeException("Unknown node : " + node.getClass());
	}

	protected XmlFragment translate(XmlExpression node, TranslationInfomation info)
	{
		if (node == null) return null;
		XmlExpression result = new XmlExpression();
		result.setExpression(translate(node.getExpression(), info));
		return result;
	}

	protected XmlFragment translate(XmlString node, TranslationInfomation info)
	{
		if (node == null) return null;
		XmlString result = new XmlString();
		result.setXml(node.getXml());
		return result;
	}

	protected AstNode translate(XmlLiteral node, TranslationInfomation info)
	{
		if (node == null) return null;
		XmlLiteral result = new XmlLiteral();
		for (XmlFragment fragment : node.getFragments())
			result.addFragment(translate(fragment, info));
		return result;
	}

	protected AstNode translate(XmlRef node, TranslationInfomation info)
	{
		if (node == null) return null;
		if (node instanceof XmlElemRef) return translate((XmlElemRef) node, info);
		if (node instanceof XmlPropRef) return translate((XmlPropRef) node, info);
		throw new RuntimeException("Unknown node : " + node.getClass());
	}

	protected AstNode translate(XmlElemRef node, TranslationInfomation info)
	{
		if (node == null) return null;
		XmlElemRef result = new XmlElemRef();
		result.setNamespace(name(node.getNamespace()));
		result.setExpression(translate(node.getExpression(), info));
		result.setAtPos(node.getAtPos());
		return result;
	}

	protected AstNode translate(XmlPropRef node, TranslationInfomation info)
	{
		if (node == null) return null;
		XmlPropRef result = new XmlPropRef();
		result.setNamespace(name(node.getNamespace()));
		result.setPropName(name(node.getPropName()));
		result.setAtPos(node.getAtPos());
		return result;
	}

	protected AstNode translate(Yield node, TranslationInfomation info)
	{
		if (node == null) return null;
		Yield result = new Yield();
		result.setValue(translate(node.getValue(), info));
		return result;
	}

}
