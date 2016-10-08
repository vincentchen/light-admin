package org.lightadmin.test.renderer;


import org.lightadmin.api.config.utils.FieldValueRenderer;
import org.lightadmin.test.model.TestLineItem;
import org.lightadmin.test.model.TestOrder;
import org.springframework.util.StringUtils;

import java.util.Set;

import static com.google.common.collect.Sets.newLinkedHashSet;

public class LineItemRenderer implements FieldValueRenderer<TestOrder> {

    @Override
    public String apply(final TestOrder parentTestEntity) {
        Set<String> lineItemsDescriptions = newLinkedHashSet();

        for (TestLineItem lineItems : parentTestEntity.getLineItems()) {
            lineItemsDescriptions.add(
                    "LineItem Id: " + lineItems.getId() +
                            "; Product Name: " + lineItems.getProduct().getName());
        }

        return StringUtils.collectionToDelimitedString(lineItemsDescriptions, "<br/>");
    }
}
