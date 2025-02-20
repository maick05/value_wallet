package br.speck.valuewallet.api.config;

import com.azure.security.keyvault.secrets.SecretClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@DependsOn("secretClient")
public class DataSourceConfig {

    @Autowired
    private SecretClient secretClient;

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        // Buscar valores do Key Vault
        String jdbcUrl = getSecretValue("VALUEWALLET-DATABASE-SQLSERVER-JDBCURL");
        String username = getSecretValue("APPS-SERVICE-USER-USERNAME");
        String password = getSecretValue("APPS-SERVICE-USER-PASSWORD");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    private String getSecretValue(String secretName) {
        try {
            return secretClient.getSecret(secretName).getValue();
        } catch (Exception e) {
            System.err.println("❌ Error whiling retrieving Key Vault secret '" + secretName + "': " + e.getMessage());
            return env.getProperty(secretName, "");
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("🚀 DATAHOUSRCE FOI CARREGADO PELO SPRING!");
    }
}

