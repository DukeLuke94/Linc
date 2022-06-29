package com.softCare.Linc.model;

import com.softCare.Linc.passwordValidator.ValidPassword;
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
 *
 * @author Jan Willem vd Wal on 29-6-2022.
 * Beschrijving:
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserVM {

    private Long userId;
    @NotEmpty(message = "This field has to be filled")
    @Size(message = "This field can have a maximum of 30 characters", max = 30) @Column(unique = true)
    private String username;
    @NotEmpty(message = "This field has to be filled") @Email
    private String emailAddress;
    @NotEmpty(message = "This field has to be filled")
    @Size(message = "The phone number has to be between 10 and 12 digits", min = 10, max = 12)
    private String phoneNumber;

    private String currentPassword;
    @ValidPassword
    private String password;
    @NotEmpty(message = "This field has to be filled")
    private String passwordRepeat;

    private List<Task> assignedTasks;
}
