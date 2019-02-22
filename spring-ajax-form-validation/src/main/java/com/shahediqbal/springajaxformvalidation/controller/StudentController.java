package com.shahediqbal.springajaxformvalidation.controller;

import com.shahediqbal.springajaxformvalidation.model.Student;
import com.shahediqbal.springajaxformvalidation.response.StudentResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StudentController {

    @PostMapping(value = "/saveStudent", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public StudentResponse saveEmployee(@ModelAttribute @Valid Student student,
                                        BindingResult result) {

        StudentResponse response = new StudentResponse();

        if (result.hasErrors()) {

            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(
                            Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
                    );

            response.setValidated(false);
            response.setErrorMessages(errors);
        } else {

            response.setValidated(true);
        }
        return response;
    }
}
