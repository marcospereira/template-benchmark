package com.mitchellbosecke.benchmark;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.BeforeClass;
import org.junit.Test;

import io.pebbletemplates.pebble.error.PebbleException;
import freemarker.template.TemplateException;

/**
 *
 * @author Martin Kouba
 */
public class ExpectedOutputTest {

    @BeforeClass
    public static void beforeClass() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    public void testFreemarkerOutput() throws IOException, TemplateException {
        Freemarker freemarker = new Freemarker();
        freemarker.setup();
        assertOutput(freemarker.benchmark());
    }
    
    @Test
    public void testRockerOutput() throws IOException, TemplateException {
        Rocker rocker = new Rocker();
        rocker.setup();
        assertOutput(rocker.benchmark());
    }

    @Test
    public void testPebbleOutput() throws IOException, PebbleException {
        Pebble pebble = new Pebble();
        pebble.setup();
        assertOutput(pebble.benchmark());
    }

    @Test
    public void testVelocityOutput() throws IOException {
        Velocity velocity = new Velocity();
        velocity.setup();
        assertOutput(velocity.benchmark());
    }

    @Test
    public void testMustacheOutput() throws IOException {
        Mustache mustache = new Mustache();
        mustache.setup();
        assertOutput(mustache.benchmark());
    }

    @Test
    public void testThymeleafOutput() throws IOException, TemplateException {
        Thymeleaf thymeleaf = new Thymeleaf();
        thymeleaf.setup();
        assertOutput(thymeleaf.benchmark());
    }

    @Test
    public void testTrimouOutput() throws IOException {
        Trimou trimou = new Trimou();
        trimou.setup();
        assertOutput(trimou.benchmark());
    }

    private void assertOutput(String output) throws IOException {
        String sanitizedOutput = prettyPrinted(Jsoup.parse(output));
        assertEquals(readExpectedOutputResource(), sanitizedOutput);
    }

    private String readExpectedOutputResource() throws IOException {
        File file = Paths.get("src/test/resources/expected-output.html").toFile();
        return prettyPrinted(Jsoup.parse(file));
    }

    private String prettyPrinted(Document doc) {
        return doc.outputSettings(new Document.OutputSettings().prettyPrint(true)).toString();
    }
}
