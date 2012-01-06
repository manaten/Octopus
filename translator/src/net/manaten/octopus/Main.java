package net.manaten.octopus;

import java.io.IOException;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		//TODO remove
		args = new String[]{ "sample/chat/chat.octopus" };

		if (args.length != 1)
		{
			System.out.println("usage: some.octopus");
			System.exit(0);
		}
		OctopusDescriptor desc = OctopusDescriptor.load(args[0]);

		Translator translator = new Translator(desc);
		translator.translate();
	}
}
