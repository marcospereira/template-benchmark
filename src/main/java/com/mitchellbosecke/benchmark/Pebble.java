package com.mitchellbosecke.benchmark;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.error.PebbleException;
import io.pebbletemplates.pebble.extension.escaper.EscaperExtension;
import io.pebbletemplates.pebble.template.PebbleTemplate;

public class Pebble extends BaseBenchmark {

    private Map<String, Object> context;

    private PebbleTemplate template;

    @Setup
    public void setup() throws PebbleException {
        EscaperExtension escaperExtension = new EscaperExtension();
        escaperExtension.setAutoEscaping(false);

        PebbleEngine engine = new PebbleEngine.Builder().extension(escaperExtension).build();
        template = engine.getTemplate("templates/stocks.pebble.html");
        this.context = getContext();
    }

    @Benchmark
    public String benchmark() throws PebbleException, IOException {
        StringWriter writer = new StringWriter();
        template.evaluate(writer, context);
        return writer.toString();
    }

}
