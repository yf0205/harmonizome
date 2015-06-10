package edu.mssm.pharm.maayanlab.Harmonizome.serializer;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.mssm.pharm.maayanlab.Harmonizome.model.DatasetGroup;

public class DatasetGroupSerializer implements JsonSerializer<DatasetGroup> {

	public JsonElement serialize(final DatasetGroup datasetGroup, final Type type, final JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("name", new JsonPrimitive(datasetGroup.getName()));
		return result;
	}

}