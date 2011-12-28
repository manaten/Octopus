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

import net.manaten.octopus.DJS2JSTranslation;
import net.manaten.octopus.DefaultTranslation;

public class DJS2JSTranslationTest
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

		DJS2JSTranslation translation = new DJS2JSTranslation(root);
		AstNode transRoot = translation.translateToNode();


		System.out.println("-----------Original Root------------");
		System.out.println(DefaultTranslation.withLn(root.toSource()));
		System.out.println("------------Translated Root-----------");
		System.out.println(DefaultTranslation.withLn(transRoot.toSource()));
		System.out.println("-----------------------");
	}
}
