package com.theprogrammningturkey.schematicsoverload.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FileUtil
{
	public static File folder;

	public static final JsonParser JSON_PARSER = new JsonParser();
	private static Gson gson = new GsonBuilder().create();

	public static JsonElement readJsonfromFile(String filepath)
	{
		StringBuilder result = new StringBuilder();
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath))));
			String line = "";
			while((line = reader.readLine()) != null)
			{
				result.append(line);
			}
			reader.close();
		} catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		return JSON_PARSER.parse(result.toString());
	}

	public static JsonElement readJsonfromCompressedFile(String filepath)
	{
		StringBuilder result = new StringBuilder();
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(new File(filepath)))));
			String line = "";
			while((line = reader.readLine()) != null)
			{
				result.append(line);
			}
			reader.close();
		} catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		String json = result.toString();
		json = json.substring(json.indexOf("{"));
		return JSON_PARSER.parse(json);
	}

	public static void writeToFile(File folder, String fileName, JsonObject json)
	{
		if(!folder.exists())
			folder.mkdirs();
		File file = new File(folder, fileName);
		System.out.println(file);
		try
		{
			file.createNewFile();
			FileWriter output = new FileWriter(file);
			output.write(gson.toJson(json));
			output.flush();
			output.close();
		} catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void writeToCompressedFile(File folder, String fileName, JsonObject json)
	{
		if(!folder.exists())
			folder.mkdirs();
		File file = new File(folder, fileName);
		System.out.println(file);
		try
		{
			file.createNewFile();
			DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(file))));
			output.writeUTF(gson.toJson(json));
			output.flush();
			output.close();
		} catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}