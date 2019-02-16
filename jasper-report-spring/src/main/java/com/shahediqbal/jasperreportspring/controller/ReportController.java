package com.shahediqbal.jasperreportspring.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ReportController {

    private String sampleText = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    @RequestMapping(value = "/report/preview", method = RequestMethod.GET)
    public ResponseEntity<byte[]> previewReport() throws JRException, IOException {
        InputStream jasperStream = this.getClass().getResourceAsStream("/report/sample.jasper");

        Map<String, Object> params = new HashMap<>();
        params.put("parameter", sampleText);

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

        final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "report.pdf";

        headers.add("content-disposition", "inline;filename=" + filename);

        return new ResponseEntity<byte[]>(outStream.toByteArray(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/report/download", method = RequestMethod.GET)
    @ResponseBody
    public void downloadReport(HttpServletResponse response) throws JRException, IOException {
        InputStream jasperStream = this.getClass().getResourceAsStream("/report/sample.jasper");

        Map<String, Object> params = new HashMap<>();
        params.put("parameter", sampleText);

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", "attachment; filename=report.pdf");

        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    @ResponseBody
    public void report() throws JRException, IOException {
        InputStream jasperStream = this.getClass().getResourceAsStream("/report/sample.jasper");

        Map<String, Object> params = new HashMap<>();
        params.put("parameter", sampleText);

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

        File pdf = File.createTempFile("report.", ".pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
    }
}
