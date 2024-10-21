package kafka.com.br.ApiTest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kafka.com.br.ApiTest.dto.ExceptionData;
import kafka.com.br.ApiTest.dto.FormData;
import kafka.com.br.ApiTest.exceptions.CaptchaIsNotCorrect;
import kafka.com.br.ApiTest.service.FormService;
import kafka.com.br.ApiTest.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form")
@EnableAsync
@Tag(name = "Form", description = "Endpoints for form")
public class FormController {

    @Autowired
    private FormService formService;

    @Autowired
    private SendEmailService emailService;

    @PostMapping
    @Operation(
            summary = "Verify captcha and send email",
            description = "Verify captcha and send email",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201", content =
                            @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(description = "BadRequestError", responseCode = "400", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionData.class)
                    )),
                    @ApiResponse(description = "UnauthorizedError", responseCode = "401", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionData.class)
                    )),
                    @ApiResponse(description = "InternalServerError", responseCode = "500", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionData.class)
                    ))
            }
    )
    public ResponseEntity<?> formPost(@Valid @RequestBody FormData data){
        var response = formService.verifyCaptcha(data);

        if(!response) throw new CaptchaIsNotCorrect("The captcha is incorrect!");

        emailService.sendingEmail(data.mail(), data.comment(), data.mail());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
