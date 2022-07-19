package com.softCare.Linc.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Project: UserVM
 * @author Jan Willem vd Wal on 29-6-2022.
 * This describes the attributes of the user view model
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserVmGeneral {

    @NotEmpty(message = "This field has to be filled")
    @Size(message = "This field can have a maximum of 30 characters", max = 30) @Column(unique = true)
    private String username;
    @Email @NotEmpty(message = "This field has to be filled")
    private String emailAddress;
    @NotEmpty(message = "This field has to be filled")
    @Size(message = "The phone number has to be between 10 and 12 digits", min = 10, max = 12)
    private String phoneNumber;

    @NotEmpty(message = "This field has to be filled")
    @Size(message = "Password has to be at least 6 characters", min = 6)
    private String password;
    @NotEmpty(message = "This field has to be filled")
    @Size(message = "Password has to be at least 6 characters", min = 6)
    private String passwordRepeat;
    public UserVmGeneral(String username) {
        this.username = username;
    }
}
