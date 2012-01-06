package net.manaten.octopus;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

public class OctopusDescription
{
	private static final String OUTPUT_DIR_NAME = "/trans";

	private String serverCode, clientCode, startHtml, outputDir, basePath;
	private int port;
	private String[] staticFiles;

	public String toJSON()
	{
		return JSON.encode(this);
	}

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getStartHtml() {
		return startHtml;
	}

	public void setStartHtml(String startHtml) {
		this.startHtml = startHtml;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String[] getStaticFiles() {
		return staticFiles;
	}

	public void setStaticFiles(String[] staticFiles) {
		this.staticFiles = staticFiles;
	}

	public static OctopusDescription load(String fileName) throws JSONException, IOException
	{
		OctopusDescription od = JSON.decode(new FileReader(fileName), OctopusDescription.class);
		String basePath = new File(fileName).getAbsoluteFile().getParent();
		od.setBasePath(basePath);
		od.setOutputDir(new File(basePath, OUTPUT_DIR_NAME).toString());
		return od;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getBasePath() {
		return basePath;
	}
}
