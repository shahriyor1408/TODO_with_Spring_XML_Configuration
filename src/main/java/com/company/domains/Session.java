package com.company.domains;

import com.company.enums.UserRole;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Session {
    public static SessionUser sessionUser;

    public static void setSessionUser(Users session) {
        sessionUser = new SessionUser(session);
    }

    @Data
    @NoArgsConstructor
    public static class SessionUser {
        private Long id;
        private String username;
        private String password;
        private UserRole role;

        public SessionUser(Users session) {
            this.id = session.getId();
            this.username = session.getUsername();
            this.password = session.getPassword();
            this.role = session.getRole();
        }
    }
}
