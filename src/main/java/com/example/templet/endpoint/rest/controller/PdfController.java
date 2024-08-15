package com.example.templet.endpoint.rest.controller;

import com.example.templet.service.event.PdfService;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.context.Context;

@Controller
@AllArgsConstructor
@CrossOrigin
public class PdfController {
  private final PdfService pdfService;

  @ResponseBody
  @GetMapping(value = "/pdf")
  public void generatePdf() throws DocumentException, IOException {
    Context context = new Context();
    context.setVariable("title", "Exemple de Titre");
    context.setVariable("content", "Ceci est un exemple de contenu pour le PDF.");

    byte[] pdfContents = pdfService.generatePdf("template", context);

    ServletRequestAttributes attr =
        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletResponse response = attr.getResponse();

    if (response != null) {
      response.setContentType("application/pdf");
      response.setHeader("Content-Disposition", "attachment; filename=exemple.pdf");
      response.getOutputStream().write(pdfContents);
    } else {
      throw new IllegalStateException("HttpServletResponse is not available");
    }
  }
}
