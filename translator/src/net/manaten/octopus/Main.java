package net.manaten.octopus;

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
			OctopusDescription desc = OctopusDescription.load(args[0]);
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
