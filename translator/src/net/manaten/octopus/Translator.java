package net.manaten.octopus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.*;

public class Translator
{
	private final OctopusDescriptor desc;

	public Translator(OctopusDescriptor desc)
	{
		this.desc = desc;

		File outputDir = new File(desc.getOutputDir());
		if (!outputDir.exists())
			outputDir.mkdir();
	}

	private AstRoot parse(String fileName) throws IOException
	{
		CompilerEnvirons compilerEnv = new CompilerEnvirons();
		compilerEnv.setOptimizationLevel(-1);
		compilerEnv.setGeneratingSource(true);
		Parser parser = new Parser(compilerEnv, compilerEnv.getErrorReporter());
		return parser.parse(new BufferedReader(new FileReader(new File(desc.getBasePath(), fileName))), fileName, 1);
	}

	public void translate() throws IOException
	{
		AstNode serverTlanslatedRoot = translate(parse(desc.getServerCode()));
		AstNode clientTlanslatedRoot = translate(parse(desc.getClientCode()));

		StringBuilder serverSource = new StringBuilder();
		serverSource.append("var Octopus = require('octopus'),sys = require('sys');var exports = {};\n");
		serverSource.append(serverTlanslatedRoot.toSource());
		serverSource.append("var port = ");
		serverSource.append(desc.getPort());
		serverSource.append(", clientHtml = __dirname + '/' + '");
		serverSource.append(desc.getStartHtml());
		serverSource.append("', clientCode = __dirname + '/client.js';var octServer = Octopus.create(clientCode, clientHtml, port);octServer.setExports(exports);octServer.on('connection', function(client) {sys.log('client connetcted !!!!');});sys.log('Server running at http://127.0.0.1:' + port + '/');");

		saveFile(new File(desc.getOutputDir(), "server.js"), serverSource.toString());
		saveFile(new File(desc.getOutputDir(), "client.js"), clientTlanslatedRoot.toSource());
		copyStaticFiles();
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

	private void copyFile(File src, File dst) throws IOException
	{
		FileChannel sourceChannel = new FileInputStream(src).getChannel();
		FileChannel destinationChannel = new FileOutputStream(dst).getChannel();
		sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
		sourceChannel.close();
		destinationChannel.close();
	}

	private void copyStaticFiles() throws IOException
	{
		for (String file : desc.getStaticFiles())
		{
			File src = new File(desc.getBasePath(), file);
			File dst = new File(desc.getOutputDir(), file);
			copyFile(src, dst);
		}
	}
}
