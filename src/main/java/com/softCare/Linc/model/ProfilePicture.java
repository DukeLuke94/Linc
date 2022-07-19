package com.softCare.Linc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Gerald Timmermans <g.h.r.timmermans@st.hanze.nl>
 * <p>
 * ZET HIER NEER WAT HET PROGRAMMA DOET!
 */

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ProfilePicture {

    @Id
    @Column(name = "profile_picture_id", nullable = false)
    @GeneratedValue
    private Long pictureId;

    private String fileName;
    private String fileType;
    private Long size;

    @Lob @Column(name = "data", nullable = false)
    private byte[] data;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public ProfilePicture(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
