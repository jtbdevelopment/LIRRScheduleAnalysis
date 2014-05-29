package com.jtbdevelopment.lirr.timetableprocessor

import com.jtbdevelopment.lirr.dataobjects.parsing.ParsedPDFSchedule
import com.jtbdevelopment.lirr.dataobjects.parsing.ProcessedPDFSchedule
import com.jtbdevelopment.lirr.timetableprocessor.converters.FinalConverter
import com.jtbdevelopment.lirr.timetableprocessor.converters.RoughConverter
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.ParseContext
import org.apache.tika.parser.pdf.PDFParser
import org.apache.tika.sax.BodyContentHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Date: 2/15/14
 * Time: 2:17 PM
 */
@Component
class PDFProcessor {
    @Autowired
    private RoughConverter roughConverter
    @Autowired
    private FinalConverter parsedConverter

    ProcessedPDFSchedule parse(final InputStream input, final String defaulTitle) {
        PDFParser parser = new PDFParser()
        BodyContentHandler bodyContentHandler = new BodyContentHandler();
        Metadata metadata = new Metadata()
        parser.parse(input, bodyContentHandler, metadata, new ParseContext())

        ParsedPDFSchedule roughParsedSchedule = roughConverter.convert(bodyContentHandler.toString())
        roughParsedSchedule.modified = metadata.get("Last-Modified")
        ProcessedPDFSchedule processedPDFSchedule = parsedConverter.convert(roughParsedSchedule)
        processedPDFSchedule.title =
                metadata.get("dc:title") ?
                        metadata.get("dc:title") :
                        (defaulTitle.replace(".pdf", "") + " " + roughParsedSchedule.subject)

        processedPDFSchedule
    }
}
