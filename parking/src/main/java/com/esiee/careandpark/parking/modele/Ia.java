package com.esiee.careandpark.parking.modele;

import com.aspose.ocr.AsposeOCR;
import com.aspose.ocr.License;

public class Ia {
        public String main(String[] args) throws Exception { // main method for extracting text from image

        License.setLicense("Aspose.OCR.lic");

        // Create an instance of AsposeOcr class to apply OCR on an image
        AsposeOCR TextExtractFromImage = new AsposeOCR();

        // Read image using RecognizePage method for text extraction
        String ExtractedTextFromImage = TextExtractFromImage.RecognizePage("carplate.jpg");

        return ExtractedTextFromImage;
    }
}