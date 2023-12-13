import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

@Service
public class DatabaseService {

    private final SecretsManagerClient secretsManagerClient;
    private final SsmClient ssmClient;

    @Value("${db.username}")
    private String dbUsername;

    @Value("${db.password}")
    private String dbPassword;

    @Value("${db.endpoint}")
    private String dbEndpoint;

    public DatabaseService(SecretsManagerClient secretsManagerClient, SsmClient ssmClient) {
        this.secretsManagerClient = secretsManagerClient;
        this.ssmClient = ssmClient;
    }

    public void yourDatabaseOperation() {
        // Use dbUsername, dbPassword, and dbEndpoint as needed
    }

    private String getSecretValue(String secretName) {
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse response = secretsManagerClient.getSecretValue(getSecretValueRequest);
        return response.secretString();
    }

    private String getParameter(String parameterName) {
        GetParameterRequest getParameterRequest = GetParameterRequest.builder()
                .name(parameterName)
                .build();

        GetParameterResponse response = ssmClient.getParameter(getParameterRequest);
        return response.parameter().value();
    }
}
