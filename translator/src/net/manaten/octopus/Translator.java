package net.manaten.octopus;

import java.io.File;

public class Translator
{
	private File serverSrcPath, clientSrcPath;
	private File serverDstPath, clientDstPath;
	public Translator(File serverSrc, File clientSrc)
	{
		this.serverSrcPath = serverSrc;
		this.clientSrcPath = clientSrc;

		File dstDir = new File(serverSrc.getParentFile(), "trans");
		if (!dstDir.exists())
			dstDir.mkdir();
		serverDstPath = new File(dstDir, "server.js");
		clientDstPath = new File(dstDir, "client.js");
		System.out.printf("%s,%s", serverDstPath, clientDstPath);
	}

	public void translate()
	{
		
	}
}
