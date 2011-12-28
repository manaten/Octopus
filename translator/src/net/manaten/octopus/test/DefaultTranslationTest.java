package net.manaten.octopus.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.NodeVisitor;

import net.manaten.octopus.DefaultTranslation;

public class DefaultTranslationTest
{
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		File src = new File("./test/TranslationTest.js");

		CompilerEnvirons compilerEnv = new CompilerEnvirons();
		compilerEnv.setOptimizationLevel(-1);
		compilerEnv.setGeneratingSource(true);
		Parser parser = new Parser(compilerEnv, compilerEnv.getErrorReporter());
		AstRoot root = parser.parse(new BufferedReader(new FileReader(src)), src.getName(), 1);

		DefaultTranslation translation = new DefaultTranslation(root){};
		AstNode transRoot = translation.translateToNode();

		System.out.println("-----------Original Root------------");
		System.out.println(root.toSource());
		System.out.println("------------Translated Root-----------");
		System.out.println(transRoot.toSource());
		System.out.println("-----------------------");
		System.out.println(root.toSource().equals(transRoot.toSource()));
		if (!root.toSource().equals(transRoot.toSource()))
		{
			String r = root.toSource();
			String tr = transRoot.toSource();
			for (int i = 0; i < r.length() && i < tr.length(); i++)
			{
				if (r.charAt(i) != tr.charAt(i))
				{
					System.out.println("\n"+r.charAt(i) +" not equals " + tr.charAt(i) + " at "+i);
					break;
				}
				else
					System.out.print(r.charAt(i));

			}
		}

		final LinkedList<AstNode> nodes = new LinkedList<AstNode>();
		root.visit(new NodeVisitor()
		{
			public boolean visit(AstNode astnode)
			{
				nodes.add(astnode);
				return true;
			}
		});


		final LinkedList<AstNode> transNodes = new LinkedList<AstNode>();
		transRoot.visit(new NodeVisitor()
		{
			public boolean visit(AstNode astnode)
			{
				transNodes.add(astnode);
				return true;
			}
		});

		if (nodes.size() == transNodes.size())
		{
			while (!transNodes.isEmpty())
			{
				AstNode node = nodes.poll();
				AstNode transNode = transNodes.poll();
				if (node.getClass() != transNode.getClass())
					System.out.println("Node class not equal.");
				if (node == transNode)
					System.out.println("Object equals. " + node.getClass());
			}
		}
		else
		{
			System.out.println("size not equal.");
		}
	}
}
