package net.manaten.octopus.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;

import net.manaten.octopus.DefaultTranslation;
import net.manaten.octopus.NormalizeTranslation;

public class NormalizeTranslationTest
{
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		File src = new File("./test/TranslationTest.js");

		CompilerEnvirons compilerEnv = new CompilerEnvirons();
		compilerEnv.setOptimizationLevel(-1);
		compilerEnv.setGeneratingSource(true);
		compilerEnv.setRecordingComments(true);
		Parser parser = new Parser(compilerEnv, compilerEnv.getErrorReporter());
		AstRoot root = parser.parse(new BufferedReader(new FileReader(src)), src.getName(), 1);

		NormalizeTranslation translation = new NormalizeTranslation(root);
		AstRoot transRoot = translation.translateToNode();

		NormalizeTranslation translation2 = new NormalizeTranslation(transRoot);
		AstRoot transRoot2 = translation2.translateToNode();

		System.out.println("-----------Original Root------------");
		System.out.println(DefaultTranslation.withLn(root.toSource()));
		System.out.println("------------Translated Root-----------");
		System.out.println(DefaultTranslation.withLn(transRoot.toSource()));
		System.out.println("-----------------------");
		System.out.println();

		System.out.println(transRoot2.toSource().equals(transRoot.toSource()));
		if (!transRoot2.toSource().equals(transRoot.toSource()))
		{
			System.out.println("------------Re-Translated Root-----------");
			System.out.println(DefaultTranslation.withLn(transRoot2.toSource()));
			System.out.println("------------Diff-----------");
			String r = transRoot2.toSource();
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
	}
}
