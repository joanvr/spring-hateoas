/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.hateoas.mediatype;

import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Test utilities to verify configuration of media type support.
 *
 * @author Oliver Drotbohm
 */
public class MediaTypeTestUtils {

	/**
	 * Looks up the the media types supported for {@link RepresentationModel} in the {@link RequestMappingHandlerAdapter}
	 * within the given {@link ApplicationContext}.
	 *
	 * @param context must not be {@literal null}.
	 * @return will never be {@literal null}.
	 */
	public static List<MediaType> getSupportedHypermediaTypes(ApplicationContext context) {
		return getSupportedHypermediaTypes(context, RepresentationModel.class);
	}

	/**
	 * Looks up the the media types supported for the given type in the {@link RequestMappingHandlerAdapter} within the
	 * given {@link ApplicationContext}.
	 *
	 * @param context must not be {@literal null}.
	 * @param type must not be {@literal null}.
	 * @return will never be {@literal null}.
	 */
	public static List<MediaType> getSupportedHypermediaTypes(ApplicationContext context, Class<?> type) {

		RequestMappingHandlerAdapter adapter = context.getBean(RequestMappingHandlerAdapter.class);

		return adapter.getMessageConverters().stream() //
				.filter(MappingJackson2HttpMessageConverter.class::isInstance) //
				.map(MappingJackson2HttpMessageConverter.class::cast) //
				.findFirst() //
				.map(it -> it.getSupportedMediaTypes(type)) //
				.orElseGet(() -> Collections.emptyList()); //
	}
}
