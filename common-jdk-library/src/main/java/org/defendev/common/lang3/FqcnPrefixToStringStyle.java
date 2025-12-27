package org.defendev.common.lang3;

import org.apache.commons.lang3.builder.ToStringStyle;



public class FqcnPrefixToStringStyle extends ToStringStyle {

    public static final FqcnPrefixToStringStyle FQCN_PREFIX_STYLE = new FqcnPrefixToStringStyle();

    private FqcnPrefixToStringStyle() {
        this.setUseIdentityHashCode(false);
    }

}
