package br.com.devvinnas.crud_lab_user.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        Server devServer = new Server()
            .url("http://localhost:" + serverPort)
            .description("Servidor de Desenvolvimento");

        Contact contact = new Contact()
            .email("seu.email@exemplo.com")
            .name("Laboratório Clínico")
            .url("https://www.laboratorio.com.br");

        License license = new License()
            .name("Apache 2.0")
            .url("http://www.apache.org/licenses/LICENSE-2.0.html");

        Info info = new Info()
            .title("API de Gerenciamento de Usuários - Laboratório Clínico")
            .version("1.0.0")
            .contact(contact)
            .description("API para gerenciamento de usuários do laboratório clínico")
            .termsOfService("https://www.laboratorio.com.br/terms")
            .license(license);

        return new OpenAPI()
            .info(info)
            .servers(List.of(devServer));
    }
}