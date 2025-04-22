package com.regi.backend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/set-session")
    public String setSession(HttpSession session){
        session.setAttribute("username", "JohnDoe");
        return "session telah di set";
    }

    @GetMapping("/get-session")
    public String getSession(HttpSession session){
        String username = (String) session.getAttribute("username");
        return "Nama pengguna dari session: " + username;
    }

    @DeleteMapping("/del-session")
    public String delSession(HttpSession session){
        session.invalidate();
        return "Session berhasil dihapus";
    }
}
