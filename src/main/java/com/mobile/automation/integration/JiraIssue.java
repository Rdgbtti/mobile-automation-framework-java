package com.mobile.automation.integration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @JiraIssue - Annotation para indicar issue JIRA linkedada ao teste (Framework)
 *
 * Uso:
 * @JiraIssue("PROJ-123")
 * public void testAlgo() { ... }
 *
 * Ou com múltiplas issues:
 * @JiraIssue(value = "PROJ-123", linkedIssues = {"PROJ-124", "PROJ-125"})
 * public void testAlgo() { ... }
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JiraIssue {

    /**
     * Issue principal do JIRA (obrigatório)
     */
    String value() default "";

    /**
     * Issues linkedadas (opcional)
     */
    String[] linkedIssues() default {};

    /**
     * Descrição do teste para o JIRA (opcional)
     */
    String description() default "";
}

