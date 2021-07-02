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
package org.sourcelab.activecampaign.apiv3.request.contact;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
@JsonRootName(value = "contact")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Long id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final List<FieldValue> fieldValues;

    public static ContactBuilder newBuilder() {
        return new ContactBuilder();
    }

    @JsonCreator
    public Contact(
        @JsonProperty("id") final Long id,
        @JsonProperty("email") final String email,
        @JsonProperty("firstName") final String firstName,
        @JsonProperty("lastName") final String lastName,
        @JsonProperty("phone") final String phone,
        final List<FieldValue> fieldValues
    ) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        if (fieldValues == null) {
            this.fieldValues = Collections.emptyList();
        } else {
            this.fieldValues = Collections.unmodifiableList(new ArrayList<>(fieldValues));
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public List<FieldValue> getFieldValues() {
        return fieldValues;
    }

    @Override
    public String toString() {
        return "Contact{"
            + "id=" + id
            + ", email='" + email + '\''
            + ", firstName='" + firstName + '\''
            + ", lastName='" + lastName + '\''
            + ", phone='" + phone + '\''
            + ", fieldValues=" + fieldValues
            + '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FieldValue {
        private final long field;
        private final String value;

        @JsonCreator
        public FieldValue(
            @JsonProperty("field") final long field,
            @JsonProperty("value") final String value
        ) {
            this.field = field;
            this.value = value;
        }

        public long getField() {
            return field;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "FieldValue{"
                + "field=" + field
                + ", value='" + value + '\''
                + '}';
        }
    }
}
