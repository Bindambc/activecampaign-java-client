/**
 * Copyright 2019, 2020, 2021 SourceLab.org https://github.com/SourceLabOrg/activecampaign-java-client
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.sourcelab.activecampaign.apiv3.request.tag;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.sourcelab.activecampaign.JacksonFactory;
import org.sourcelab.activecampaign.apiv3.response.tag.TagCreateResponse;
import org.sourcelab.http.rest.request.Request;
import org.sourcelab.http.rest.request.RequestMethod;
import org.sourcelab.http.rest.request.body.RequestBodyContent;
import org.sourcelab.http.rest.request.body.StringBodyContent;

import java.io.IOException;

/**
 * Represents a tag create request.
 */
public class TagCreateRequest implements Request<TagCreateResponse> {
    private final TagCreateDto tagCreateDto = new TagCreateDto();

    @Override
    public String getApiEndpoint() {
        return "api/3/tags";
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    public TagCreateRequest withTag(final String tag) {
        tagCreateDto.setTag(tag);
        return this;
    }

    public TagCreateRequest withDescription(final String description) {
        tagCreateDto.setDescription(description);
        return this;
    }

    public TagCreateRequest withTagType(final String tagType) {
        tagCreateDto.setTagType(tagType);
        return this;
    }

    public TagCreateRequest withTagTypeContact() {
        return withTagType("contact");
    }
    public TagCreateRequest withTagTypeTemplate() {
        return withTagType("template");
    }

    @Override
    public RequestBodyContent getRequestBody() {
        try {
            return new StringBodyContent(
                JacksonFactory.newInstance().writeValueAsString(tagCreateDto)
            );
        } catch (final JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public TagCreateResponse parseResponse(final String response) throws IOException {
        return JacksonFactory.newInstance().readValue(response, TagCreateResponse.class);
    }
}
