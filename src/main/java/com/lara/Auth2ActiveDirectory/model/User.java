package com.lara.Auth2ActiveDirectory.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter

public class User {

    public String sub;
    public String version;
    public Rol roles;
    public String iss;
    public String oid;
    public String preferred_username;
    public String uti;
    public String nonce;
    public String tid;
    public String rh;
    public String name;

    public class Rol{
        public String name;
    }
}


