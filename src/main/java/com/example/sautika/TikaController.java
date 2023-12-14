package com.example.sautika;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class TikaController {

    @PostMapping("/tika/pdf")
    public String pdf(@RequestParam("file") MultipartFile file) throws IOException, TikaException, SAXException {
        String fileName = file.getOriginalFilename();
        file.transferTo( new File("C:\\Users\\HP\\OneDrive\\Documents\\" + fileName));
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        //FileInputStream inputstream = new FileInputStream(fileName);
        FileInputStream inputstream = new FileInputStream("C:\\Users\\HP\\OneDrive\\Documents\\"+fileName);
        ParseContext pcontext = new ParseContext();

        PDFParser pdfparser = new PDFParser();
        pdfparser.parse(inputstream, handler, metadata, pcontext);

        System.out.println("Contents of the PDF :" + handler.toString());

        System.out.println("Metadata of the PDF:");
        String[] metadataNames = metadata.names();

        for (String name : metadataNames) {
            System.out.println(name + " : " + metadata.get(name));
        }
        return handler.toString();
    }

    /*@GetMapping("/tika/myfile")
    public String myFile() throws IOException, TikaException, SAXException {
        FileInputStream inputstream = new FileInputStream("C:\\Users\\HP\\Downloads\\murmu.docx");
        Parser parser = new TXTParser();
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();

        parser.parse(inputstream, handler, metadata, context);
        return handler.toString();
    }*/

    /*@PostMapping("/tika/image")
    public String image(@RequestParam("file") MultipartFile file) throws IOException, TikaException, SAXException {
        String fileName = file.getOriginalFilename();
        file.transferTo( new File("C:\\Users\\HP\\OneDrive\\Documents\\" + fileName));
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        //FileInputStream inputstream = new FileInputStream(fileName);
        FileInputStream inputstream = new FileInputStream("C:\\Users\\HP\\OneDrive\\Documents\\"+fileName);
        ParseContext pcontext = new ParseContext();

        JpegParser  JpegParser = new JpegParser();
        JpegParser.parse(inputstream, handler, metadata,pcontext);
        System.out.println("Contents of the document:" + handler.toString());
        System.out.println("Metadata of the document:");
        String[] metadataNames = metadata.names();

        for(String name : metadataNames) {
            System.out.println(name + ": " + metadata.get(name));
        }

        return handler.toString();
    }*/
}
