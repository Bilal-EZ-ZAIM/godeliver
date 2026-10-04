package com.aura.godeliver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(

        @NotBlank(message = "{validation.firstName.required}")
        @Size(max = 100, message = "{validation.firstName.tooLong}")
        String firstName,

        @NotBlank(message = "{validation.lastName.required}")
        @Size(max = 100, message = "{validation.lastName.tooLong}")
        String lastName,

        @NotBlank(message = "{validation.email.required}")
        @Email(message = "{validation.email.invalid}")
        String email,

        @NotBlank(message = "{validation.phone.required}")
        @Size(max = 20, message = "{validation.phone.tooLong}")
        String phone,

        @NotBlank(message = "{validation.password.required}")
        @Size(min = 8, max = 20, message = "{validation.password.invalidLength}")
        String password

) {
}