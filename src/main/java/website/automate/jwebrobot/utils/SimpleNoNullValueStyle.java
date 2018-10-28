package website.automate.jwebrobot.utils;

import org.apache.commons.lang3.builder.ToStringStyle;

public final class SimpleNoNullValueStyle extends ToStringStyle {
        private static final long serialVersionUID = 1L;

        public static final SimpleNoNullValueStyle INSTANCE = new SimpleNoNullValueStyle();

        private SimpleNoNullValueStyle() {
            this.setUseClassName(false);
            this.setUseIdentityHashCode(false);
        }

        @Override
        public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
            if(value == null){
                return;
            }
            super.append(buffer, fieldName, value, fullDetail);
        }
    }
