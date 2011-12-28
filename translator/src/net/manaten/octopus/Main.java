package net.manaten.octopus;

import java.io.File;
import java.io.IOException;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		args = new String[]{"./sample/chat/server.js", "./sample/chat/client.js"};
		if (args.length != 2)
		{
			System.out.println("usage: octopus serverCode clientCode");
			System.exit(0);
		}

		Translator translator = new Translator(new File(args[0]), new File(args[1]));
		// TODO try-catch
		translator.parse();
		translator.translate();
	}
}