package com.softCare.Linc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Project: UserVmEditPassword
 * @author Jan Willem vd Wal on 29-6-2022.
 * Attributes of user viewmodel for editing password
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserVmEditPassword {

    private String username;

    @NotEmpty(message = "This field has to be filled")
    private String currentPassword;

    @NotEmpty(message = "This field has to be filled")
    @Size(message = "Password has to be at least 6 characters", min = 6, max = 20)
    private String password;

    @NotEmpty(message = "This field has to be filled")
    @Size(message = "Password has to be at least 6 characters", min = 6, max = 20)
    private String passwordRepeat;
}
