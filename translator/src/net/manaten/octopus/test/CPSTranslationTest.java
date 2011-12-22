package net.manaten.octopus.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;

import net.manaten.octopus.old.CPSTranslation;
import net.manaten.octopus.old.ContinuationEliminateTranslation;

public class CPSTranslationTest
{
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		File src = new File("./test/TranslationTestSmall.js");
		CompilerEnvirons compilerEnv = new CompilerEnvirons();
		compilerEnv.setOptimizationLevel(-1);
		compilerEnv.setGeneratingSource(true);
		Parser parser = new Parser(compilerEnv, compilerEnv.getErrorReporter());
		AstRoot root = parser.parse(new BufferedReader(new FileReader(src)), src.getName(), 1);

		System.out.println(root.toSource());
		System.out.println("-----------------------");

		CPSTranslation translation = new CPSTranslation();
		AstNode transRoot = translation.translate(root);
		System.out.println(transRoot.toSource());

		System.out.println("-----------------------");

		ContinuationEliminateTranslation translation2 = new ContinuationEliminateTranslation();
		AstNode transRoot2 = translation2.translate(transRoot);
		System.out.println(transRoot2.toSource());
	}
}
