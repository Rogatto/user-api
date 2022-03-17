package com.app.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Password implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6986746375915710855L;
    private String password;
}
