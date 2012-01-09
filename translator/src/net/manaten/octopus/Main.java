package net.manaten.octopus;

import java.io.File;

public class Main
{
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("usage: some.octopus");
			System.exit(0);
		}

		try
		{
			File descPath;
			File input = new File(args[0]);
			if (input.isDirectory())
				descPath = new File(input, "octopus.json");
			else
				descPath = input;

			OctopusDescription desc = OctopusDescription.load(descPath);
			Translator translator = new Translator(desc);
			translator.translate();
		}
		catch (Exception e)
		{
			System.err.println("Error occured during translation.");
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
