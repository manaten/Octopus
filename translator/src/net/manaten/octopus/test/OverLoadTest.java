package net.manaten.octopus.test;

abstract class Node
{
	Node right, left;
	public Node(Node right, Node left)
	{
		this.right = right;
		this.left = left;
	}
};
class NodeA extends Node
{
	public NodeA(Node right, Node left)
	{
		super(right, left);
	}
};
class NodeB extends Node
{
	public NodeB(Node right, Node left)
	{
		super(right, left);
	}
};
class NodeC extends NodeB
{
	public NodeC(Node right, Node left) {
		super(right, left);
	}
};

class Translation
{
	public Node translate(Node node)
	{
		if (node == null) return null;
		throw new RuntimeException("Error! Unknown node : " + node.getClass());
	}
	public NodeA translate(NodeA node)
	{
		System.out.println("Do NodeA translation...");
		return new NodeA( translate(node.right), translate(node.left) );
	}
	public NodeB translate(NodeB node)
	{
		System.out.println("Do NodeB translation...");
		return new NodeB( translate(node.right), translate(node.left) );
	}
	public NodeC translate(NodeC node)
	{
		System.out.println("Do NodeC translation...");
		return new NodeC( translate(node.right), translate(node.left) );
	}
}


public class OverLoadTest
{
	public static void main(String[] args)
	{
		Node root = new NodeA( new NodeB(null, new NodeC(null, null)), new NodeC(null, null) );
		new Translation().translate(root);
	}
}
