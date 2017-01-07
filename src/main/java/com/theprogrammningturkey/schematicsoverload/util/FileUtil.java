package com.theprogrammningturkey.schematicsoverload.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

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
		String result = "";
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath))));
			String line = "";
			while((line = reader.readLine()) != null)
			{
				result += line;
			}
			reader.close();
		} catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		return JSON_PARSER.parse(result);
	}

	public static void writeToFile(File ouput, String fileName, JsonObject json)
	{
		File file = new File(ouput, fileName);
		try
		{
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			fw.write(gson.toJson(json));
			fw.flush();
			fw.close();
		} catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}