package edu.mssm.pharm.maayanlab.Harmonizome.serdes;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.mssm.pharm.maayanlab.Harmonizome.model.Feature;

public class FeatureSerializer implements JsonSerializer<Feature> {

	public JsonElement serialize(final Feature feature, final Type type, final JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("gene", new JsonPrimitive(feature.getGene().getName()));
		result.add("thresholdValue", new JsonPrimitive(feature.getThresholdValue()));
		return result;
	}
}