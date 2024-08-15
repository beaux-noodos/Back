package com.example.templet.service.event;

import com.lowagie.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
@AllArgsConstructor
public class PdfService {

  @Autowired private TemplateEngine templateEngine;

  public byte[] generatePdf(String templateName, Context context)
      throws DocumentException, IOException {
    String htmlContent = templateEngine.process(templateName, context);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ITextRenderer renderer = new ITextRenderer();
    renderer.setDocumentFromString(htmlContent);
    renderer.layout();
    renderer.createPDF(outputStream);

    return outputStream.toByteArray();
  }
}
