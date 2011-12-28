package net.manaten.octopus;

import java.util.LinkedList;

import org.mozilla.javascript.Node;
import org.mozilla.javascript.ast.*;

public class DJS2JSTranslation extends DefaultTranslation
{
	public DJS2JSTranslation(AstRoot root) {
		super(root);
	}

	@Override
	public String translate()
	{
		NormalizeTranslation ntrans = new NormalizeTranslation(root);
		NDJS2CPSJSTranslation nd2j = new NDJS2CPSJSTranslation(ntrans.translateToNode());
		return nd2j.translate();
	}

	/**
	 * N-formed D-JS to CPS-JS
	 * @author mana
	 */
	private class NDJS2CPSJSTranslation extends DefaultTranslation
	{
		public NDJS2CPSJSTranslation(AstRoot root)
		{
			super(root);
		}

	}
}
