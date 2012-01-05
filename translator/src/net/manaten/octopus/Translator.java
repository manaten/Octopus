package net.manaten.octopus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.*;

public class Translator
{
	private File serverSrcPath, clientSrcPath;
	private File serverDstPath, clientDstPath;
	private AstRoot serverRoot, clientRoot;

	public Translator(File serverSrc, File clientSrc)
	{
		this.serverSrcPath = serverSrc;
		this.clientSrcPath = clientSrc;

		File dstDir = new File(serverSrc.getParentFile(), "trans");
		if (!dstDir.exists())
			dstDir.mkdir();
		serverDstPath = new File(dstDir, "server.js");
		clientDstPath = new File(dstDir, "client.js");
	}

	public void parse() throws IOException
	{
		CompilerEnvirons compilerEnv = new CompilerEnvirons();
		compilerEnv.setOptimizationLevel(-1);
		compilerEnv.setGeneratingSource(true);
		Parser parser;

		parser = new Parser(compilerEnv, compilerEnv.getErrorReporter());
		this.serverRoot = parser.parse(new BufferedReader(new FileReader(serverSrcPath)), serverSrcPath.getName(), 1);

		parser = new Parser(compilerEnv, compilerEnv.getErrorReporter());
		this.clientRoot = parser.parse(new BufferedReader(new FileReader(clientSrcPath)), clientSrcPath.getName(), 1);
	}

	private Set<String> getSymbols(AstRoot root)
	{
		final Set<String> symbols = new HashSet<String>();
		root.visit(new NodeVisitor()
		{
			@Override
			public boolean visit(AstNode node) {
				if (node instanceof Name)
				{
					symbols.add(node.getString());
				}
				return true;
			}
		});
		return symbols;
	}

	public void translate()
	{
		Set<String> serverSymbols = getSymbols(serverRoot);
		Set<String> clientSymbols = getSymbols(clientRoot);
		System.out.println(serverSymbols);
		System.out.println(clientSymbols);


		AstNode serverTlanslatedRoot = translate(serverRoot);
		AstNode clientTlanslatedRoot = translate(clientRoot);

		StringBuilder serverSource = new StringBuilder();
		serverSource.append("var Octopus = require('../../../node_modules/octopus'),sys = require('sys');var exports = {};\n");
		serverSource.append(serverTlanslatedRoot.toSource());
		serverSource.append("var port = 8080, clientHtml = __dirname + '/index.html', clientCode = __dirname + '/client.js';var octServer = Octopus.create(clientCode, clientHtml, port);octServer.setExports(exports);octServer.on('connection', function(client) {sys.log('client connetcted !!!!');});sys.log('Server running at http://127.0.0.1:' + port + '/');");

		saveFile(serverDstPath, serverSource.toString());
		saveFile(clientDstPath, clientTlanslatedRoot.toSource());
	}

	private AstNode translate(AstRoot node)
	{
		DJS2JSTranslation translation = new DJS2JSTranslation(node);
		AstNode transRoot = translation.translateToNode();
		return transRoot;
	}

	private void saveFile(File outPath, String content)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(outPath));
			writer.write(content);
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
