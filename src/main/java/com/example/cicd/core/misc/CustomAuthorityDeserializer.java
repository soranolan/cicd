package com.example.cicd.core.misc;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthorityDeserializer extends JsonDeserializer<Object> {
	
	@Override
	public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		ObjectMapper mapper = (ObjectMapper) p.getCodec();
		JsonNode jsonNode = mapper.readTree(p);
		Iterator<JsonNode> elements = jsonNode.elements();
		
		LinkedList<GrantedAuthority> grantedAuthorities = new LinkedList<>();
		while (elements.hasNext()) {
			JsonNode next = elements.next();
			JsonNode authority = next.get("authority");
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.asText()));
		}
		return grantedAuthorities;
	}
	
}
