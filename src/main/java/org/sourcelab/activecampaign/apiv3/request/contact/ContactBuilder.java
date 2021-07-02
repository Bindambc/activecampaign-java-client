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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 */
public class ContactBuilder {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;

    private Map<Long, String> fields = new HashMap<>();

    public ContactBuilder withId(final long id) {
        this.id = id;
        return this;
    }

    public ContactBuilder withId(final String id) {
        return withId(Long.parseLong(id));
    }

    public ContactBuilder withEmail(final String email) {
        this.email = email;
        return this;
    }

    public ContactBuilder withFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ContactBuilder withLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactBuilder withPhone(final String phone) {
        this.phone = phone;
        return this;
    }

    public ContactBuilder withFields(final Map<Long, String> fields) {
        Objects.requireNonNull(fields);
        this.fields.putAll(fields);
        return this;
    }

    public ContactBuilder withField(final Long fieldId, final String value) {
        fields.put(fieldId, value);
        return this;
    }

    public Contact build() {
        final List<Contact.FieldValue> fieldValues = new ArrayList<>();
        fields.forEach((key, value) -> fieldValues.add(new Contact.FieldValue(key, value)));

        return new Contact(
            id,
            email,
            firstName,
            lastName,
            phone,
            fieldValues
        );
    }
}
