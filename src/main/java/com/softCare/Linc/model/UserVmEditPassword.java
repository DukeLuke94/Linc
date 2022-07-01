package com.softCare.Linc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Project: UserVmEditPassword
 * @author Jan Willem vd Wal on 29-6-2022.
 * Attributes of user viewmodel for editing password
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserVmEditPassword {

    private String username;
    @NotBlank(message = "This field has to be filled")
    private String currentPassword;
    @NotBlank(message = "This field has to be filled")
    @Size(message = "Password has to be at least 6 characters", min = 6)
    private String password;
    @NotBlank(message = "This field has to be filled")
    @Size(message = "Password has to be at least 6 characters", min = 6)
    private String passwordRepeat;
}
