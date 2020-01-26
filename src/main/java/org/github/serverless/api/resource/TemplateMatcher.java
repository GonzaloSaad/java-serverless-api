package org.github.serverless.api.resource;

public class TemplateMatcher {

    private static final String RESOURCE_VARIABLE_REGEX = "\\{\\w+}";


    private final String template;

    public TemplateMatcher(String template) {
        this.template = template;
    }

    public boolean matches(String resource) {

        String[] templateParts = template.split("/");
        String[] resourceParts = resource.split("/");

        if (templateParts.length != resourceParts.length) {
            return false;
        }

        for (int i = 0; i < templateParts.length; i++) {
            String templatePart = templateParts[i];
            String resourcePart = resourceParts[i];


            if (!templatePart.matches(RESOURCE_VARIABLE_REGEX) && !templatePart.equals(resourcePart)) {
                return false;
            }
        }
        return true;
    }
}
