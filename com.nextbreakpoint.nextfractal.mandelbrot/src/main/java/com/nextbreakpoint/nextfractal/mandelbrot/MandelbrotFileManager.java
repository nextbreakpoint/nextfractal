package com.nextbreakpoint.nextfractal.mandelbrot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.FileManagerEntry;
import com.nextbreakpoint.nextfractal.core.FileManifest;
import com.nextbreakpoint.nextfractal.core.session.Session;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import static javax.xml.bind.JAXB.unmarshal;

public class MandelbrotFileManager extends FileManager {
    @Override
    protected Try<List<FileManagerEntry>, Exception> saveEntries(Session session) {
        return Try.of(() -> createEntries((MandelbrotSession) session));
    }

    @Override
    protected Try<Session, Exception> loadEntries(List<FileManagerEntry> entries) {
        return entries.stream().filter(entry -> entry.getName().equals("m-script")).findFirst()
            .map(entry -> Try.of(() -> loadMscript(new FileInputStream(new String(entry.getData()))))).orElseGet(() -> createSession(entries));
    }

    private Try<Session, Exception> createSession(List<FileManagerEntry> entries) {
        ObjectMapper mapper = new ObjectMapper();

        Try<String, Exception> script = entries.stream().filter(entry -> entry.getName().equals("script"))
                .findFirst().map(scriptEntry -> Try.success(new String(scriptEntry.getData())))
                .orElse(Try.failure(new Exception("Script entry is required")));

        Try<MandelbrotMetadata, Exception> metadata = entries.stream().filter(entry -> entry.getName().equals("metadata"))
                .findFirst().map(metadataEntry -> Try.of(() -> mapper.readValue(metadataEntry.getData(), MandelbrotMetadata.class)))
                .orElse(Try.failure(new Exception("Metadata entry is required")));

        return Try.of(() -> new MandelbrotSession(metadata.orThrow(), script.orThrow()));
    }

    private List<FileManagerEntry> createEntries(MandelbrotSession session) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        List<FileManagerEntry> entries = new LinkedList<>();

        FileManifest manifest = new FileManifest(MandelbrotFactory.PLUGIN_ID);

        entries.add(new FileManagerEntry("manifest", mapper.writeValueAsBytes(manifest)));
        entries.add(new FileManagerEntry("metadata", mapper.writeValueAsBytes(session.getMetadata())));
        entries.add(new FileManagerEntry("script", session.getScript().getBytes()));

        return entries;
    }

    private Session loadMscript(InputStream is) throws Exception {
        MandelbrotDataV11 data = loadFromStream(is);
        return new MandelbrotSession(data.getView(), data.getSource());
    }

    public MandelbrotDataV11 loadFromStream(InputStream stream) throws Exception {
        return Try.of(() -> unmarshal(stream, MandelbrotDataV11.class))
            .or(() -> unmarshal(stream, MandelbrotDataV10.class).toMandelbrotDataV11())
            .mapper(e -> new Exception("Cannot load data from stream")).orThrow();
    }

//    protected void updateEncodedData(TextArea textArea, MandelbrotSession session) {
//        if (Boolean.getBoolean("mandelbrot.encode.data")) {
//            try {
//                MandelbrotDataStore service = new MandelbrotDataStore();
//                StringWriter writer = new StringWriter();
//                service.saveToWriter(writer, data);
//                String plainData = writer.toString();
//                String encodedData = Base64.getEncoder().encodeToString(plainData.getBytes());
//                encodedData = URLEncoder.encode(encodedData, "UTF-8");
//                logger.info("Update encoded data");
//            } catch (Exception e) {
//                logger.log(Level.FINE, "Cannot encode data", e);
//            }
//        }
//    }
}
