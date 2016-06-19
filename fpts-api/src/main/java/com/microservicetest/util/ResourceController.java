package com.microservicetest.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicestest.enums.ResourceType;

/**
 * Util class that holds shared resources in a centralized way.<br>
 * e.g. ObjectMapper for JSON Operations
 *
 * @author andre
 *
 */
public class ResourceController {
	static Map<ResourceType, Object> resources = new HashMap<ResourceType, Object>();
	// Initialize pre defined resources
	static {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		putResource(ResourceType.OBJECT_MAPPER, mapper);
	}

	public static <E> E getResource(ResourceType resource) {
		return (E) resources.get(resource);
	}

	public static <E> void putResource(ResourceType resource, E e) {
		resources.put(resource, e);
	}

}
