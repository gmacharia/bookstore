/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author kobe
 */
@ConfigurationProperties("com.bookstore")
public record BookConfig(
        String norecordfound,
        String successstatusmessage,
        String failedstatusmessage,
        int failurecode,
        int successcode) {

}
