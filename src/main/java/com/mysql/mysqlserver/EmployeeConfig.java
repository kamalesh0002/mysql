package com.mysql.mysqlserver;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.identity.AzureCliCredential;
import com.azure.identity.AzureCliCredentialBuilder;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;

import com.azure.core.credential.TokenRequestContext;
import com.mysql.cj.jdbc.MysqlDataSource;

import org.springframework.beans.factory.annotation.Value;


@Configuration
public class EmployeeConfig {
    
    @Value("${MYSQL_SERVER_URI}")
    private String databaseUrl;

    @Value("${DATABASE_NAME}")
    private String dbname;
    
    @Bean
    public DataSource dataSource() {
       AzureCliCredential defaultCredential = new AzureCliCredentialBuilder().build();

     //   DefaultAzureCredential defaultCredential = new DefaultAzureCredentialBuilder().build();
        String token = defaultCredential.getToken(new TokenRequestContext().addScopes("https://ossrdbms-aad.database.windows.net/.default")).block().getToken();
        
        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setPassword(token);
        dataSource.setServerName(databaseUrl);
        dataSource.setDatabaseName(dbname); 
  
        return dataSource;
    }
}
