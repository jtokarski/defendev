package org.defendev.gradle.convention

import javax.inject.Inject;

import org.gradle.api.provider.Property
import org.gradle.api.model.ObjectFactory;



class CommonLibraryExtension {

    /*
     * Using Property<...> in order to enable lazy initialization of this properties (often, a plugin
     * is applied before it's initialized). Or, in other words, the plugin specific DSL statements go AFTER
     * the plugins {...} block.
     *
     */
    final Property<String> pomName;

    final Property<String> pomDescription;

    @Inject
    CommonLibraryExtension(ObjectFactory objects) {
        pomName = objects.property(String);
        pomDescription = objects.property(String);
    }

}
