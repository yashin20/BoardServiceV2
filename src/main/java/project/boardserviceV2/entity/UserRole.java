package project.boardserviceV2.entity;

import jakarta.persistence.Enumerated;

/**
 * SUPER_ADMIN : 최고 관리자
 * ADMIN : 관리자
 * USER : 회원
 */
public enum UserRole {
    SUPER_ADMIN,
    ADMIN,
    USER
}
