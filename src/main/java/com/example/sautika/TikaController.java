package com.example.sautika;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.ArrayList;

@RestController
@CrossOrigin("http://localhost:3000/")
public class TikaController {

    @PostMapping("/tika/pdf")
    public ArrayList<String> pdf(@RequestParam("file") MultipartFile file) throws IOException, TikaException, SAXException {
        String fileName = file.getOriginalFilename();
        File tt = new File("D:\\sem 8\\wells fargo\\pdf_compare\\" + fileName);
        if (!tt.exists()) {
            file.transferTo(new File("D:\\sem 8\\wells fargo\\pdf_compare\\" + fileName));
        }
        //file.transferTo( new File("C:\\Users\\HP\\OneDrive\\Documents\\" + fileName));
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        //FileInputStream inputstream = new FileInputStream(fileName);
        FileInputStream inputstream = new FileInputStream("D:\\sem 8\\wells fargo\\pdf_compare\\" + fileName);
        ParseContext pcontext = new ParseContext();

        PDFParser pdfparser = new PDFParser();
        pdfparser.parse(inputstream, handler, metadata, pcontext);

        System.out.println("Contents of the PDF :" + handler.toString());

        System.out.println("Metadata of the PDF:");
        String[] metadataNames = metadata.names();

        ArrayList<String> response = new ArrayList<>();
        response.add(handler.toString());

        for (String name : metadataNames) {
            System.out.println(name + " : " + metadata.get(name));
            response.add(name + " : " + metadata.get(name));
        }

        return response;
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
        File tt = new File("C:\\Users\\HP\\OneDrive\\Documents\\"+fileName);
        if (!tt.exists()) {
            file.transferTo(new File("C:\\Users\\HP\\OneDrive\\Documents\\" + fileName));
        }
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        //FileInputStream inputstream = new FileInputStream(fileName);
        FileInputStream inputstream = new FileInputStream("C:\\Users\\HP\\OneDrive\\Documents\\"+fileName);
        ParseContext pcontext = new ParseContext();

        JpegParser JpegParser = new JpegParser();
        JpegParser.parse(inputstream, handler, metadata,pcontext);
        System.out.println("Contents of the document:" + handler.toString());
        System.out.println("Metadata of the document:");
        String[] metadataNames = metadata.names();

        for(String name : metadataNames) {
            System.out.println(name + ": " + metadata.get(name));
        }
        tt.delete();
        return handler.toString();
    }*/

    @PostMapping("/tika/allFiles")
    public String allFiles(@RequestParam("file") MultipartFile file) throws IOException, TikaException, SAXException {
        String fileName = file.getOriginalFilename();
        String filePath = "D:\\sem 8\\wells fargo\\pdf_compare\\" + fileName;
        File tt = new File(filePath);
        if (!tt.exists()) {
            file.transferTo(new File("D:\\sem 8\\wells fargo\\pdf_compare\\" + fileName));
        }
        //file.transferTo( new File("C:\\Users\\HP\\OneDrive\\Documents\\" + fileName));
        Process pp = Runtime.getRuntime().exec("curl -T " + filePath + " http://localhost:9998/tika");
        BufferedReader reader = new BufferedReader(new InputStreamReader(pp.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }
        String result = builder.toString();
        System.out.println(result+"res");
        /*BodyContentHandler handler = new BodyContentHandler();
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
        }*/
        return result;
    }
}
