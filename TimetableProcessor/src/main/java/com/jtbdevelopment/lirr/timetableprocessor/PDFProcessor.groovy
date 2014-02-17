package com.jtbdevelopment.lirr.timetableprocessor

import com.jtbdevelopment.lirr.timetableprocessor.converters.FinalConverter
import com.jtbdevelopment.lirr.timetableprocessor.converters.RoughConverter
import com.jtbdevelopment.lirr.timetableprocessor.data.ParsedSchedule
import com.jtbdevelopment.lirr.timetableprocessor.data.RoughParsedSchedule
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.ParseContext
import org.apache.tika.parser.pdf.PDFParser
import org.apache.tika.sax.BodyContentHandler

/**
 * Date: 2/15/14
 * Time: 2:17 PM
 */
class PDFProcessor {
    private RoughConverter roughConverter = new RoughConverter()
    private FinalConverter parsedConverter = new FinalConverter()

    ParsedSchedule parse(final InputStream input) {
        PDFParser parser = new PDFParser()
        BodyContentHandler bodyContentHandler = new BodyContentHandler();
        Metadata metadata = new Metadata()
        parser.parse(input, bodyContentHandler, metadata, new ParseContext())

        RoughParsedSchedule roughParsedSchedule = roughConverter.convert(bodyContentHandler.toString())
        roughParsedSchedule.title = metadata.get("dc:title")
        roughParsedSchedule.modified = metadata.get("Last-Modified")
        roughParsedSchedule.subject = metadata.get("subject")


        return parsedConverter.convert(roughParsedSchedule)
    }
}
